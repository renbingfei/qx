package com.qx.rbf.handler;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    /** 使用Properties来保存设备的信息和错误堆栈信息*/    
    private Properties mDeviceCrashInfo = new Properties();  
    private static final String VERSION_NAME = "versionName";    
    private static final String VERSION_CODE = "versionCode";    
    private static final String STACK_TRACE = "STACK_TRACE";   
    /** 是否开启日志输出,在Debug状态下开启,   
     * 在Release状态下关闭以提示程序性能   
     * */    
     public static final boolean DEBUG = true; 
    /** 错误报告文件的扩展名 */    
    private static final String CRASH_REPORTER_EXTENSION = ".cr"; 
    
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
    	
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
         if (!handleException(ex) && mDefaultHandler != null) {
         mDefaultHandler.uncaughtException(thread, ex);
         } else {
         android.os.Process.killProcess(android.os.Process.myPid());
         System.exit(10);
         }
        System.out.println("uncaughtException");
        System.out.println("崩溃了");
        
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     * 
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
    	if (ex == null) {    
            Log.w(TAG, "handleException --- ex==null");    
            return true;    
        }    
        final String msg = ex.getLocalizedMessage();    
        if(msg == null) {   
            return false;   
        }   
        //使用Toast来显示异常信息    
        new Thread() {    
            @Override    
            public void run() {    
                Looper.prepare();    
                Toast toast = Toast.makeText(mContext, "程序出错，即将退出:\r\n" + msg,   
                        Toast.LENGTH_LONG);   
                toast.setGravity(Gravity.CENTER, 0, 0);   
                toast.show();   
//              MsgPrompt.showMsg(mContext, "程序出错啦", msg+"\n点确认退出");   
                Looper.loop();    
            }    
        }.start();    
        //收集设备信息    
        collectCrashDeviceInfo(mContext);    
        //保存错误报告文件    
        saveCrashInfoToFile(ex);    
        //发送错误报告到服务器    
        //sendCrashReportsToServer(mContext);    
        return true;    
    }    
    /**   
     * 保存错误信息到文件中   
     * @param ex   
     * @return   
     */    
     private String saveCrashInfoToFile(Throwable ex) {    
         Writer info = new StringWriter();    
         PrintWriter printWriter = new PrintWriter(info);    
         ex.printStackTrace(printWriter);    
         Throwable cause = ex.getCause();    
         while (cause != null) {    
             cause.printStackTrace(printWriter);    
             cause = cause.getCause();    
         }    
         String result = info.toString();    
         printWriter.close();    
         mDeviceCrashInfo.put("EXEPTION", ex.getLocalizedMessage());   
         mDeviceCrashInfo.put(STACK_TRACE, result);    
         try {    
             //long timestamp = System.currentTimeMillis();    
             Time t = new Time("GMT+8");    
             t.setToNow(); // 取得系统时间   
             int date = t.year * 10000 + t.month * 100 + t.monthDay;   
             int time = t.hour * 10000 + t.minute * 100 + t.second;   
             String fileName = "crash-" + date + "-" + time + CRASH_REPORTER_EXTENSION;    
             FileOutputStream trace = mContext.openFileOutput(fileName,    
                     Context.MODE_PRIVATE);    
             mDeviceCrashInfo.store(trace, "");    
             trace.flush();    
             trace.close();    
             return fileName;    
         } catch (Exception e) {    
             Log.e(TAG, "an error occured while writing report file...", e);    
         }    
         return null;    
     }    
   
     /**   
     * 收集程序崩溃的设备信息   
     *   
     * @param ctx   
     */    
     public void collectCrashDeviceInfo(Context ctx) {    
         try {    
             PackageManager pm = ctx.getPackageManager();    
             PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),    
                     PackageManager.GET_ACTIVITIES);    
             if (pi != null) {    
                 mDeviceCrashInfo.put(VERSION_NAME,    
                         pi.versionName == null ? "not set" : pi.versionName);    
                 mDeviceCrashInfo.put(VERSION_CODE, ""+pi.versionCode);    
             }    
         } catch (NameNotFoundException e) {    
             Log.e(TAG, "Error while collect package info", e);    
         }    
         //使用反射来收集设备信息.在Build类中包含各种设备信息,    
         //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息    
         //具体信息请参考后面的截图    
         Field[] fields = Build.class.getDeclaredFields();    
         for (Field field : fields) {    
             try {    
                 field.setAccessible(true);    
                 mDeviceCrashInfo.put(field.getName(), ""+field.get(null));    
                 if (DEBUG) {    
                     Log.d(TAG, field.getName() + " : " + field.get(null));    
                 }    
             } catch (Exception e) {    
                 Log.e(TAG, "Error while collect crash info", e);    
             }    
         }    
     }       
}