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
    /** ʹ��Properties�������豸����Ϣ�ʹ����ջ��Ϣ*/    
    private Properties mDeviceCrashInfo = new Properties();  
    private static final String VERSION_NAME = "versionName";    
    private static final String VERSION_CODE = "versionCode";    
    private static final String STACK_TRACE = "STACK_TRACE";   
    /** �Ƿ�����־���,��Debug״̬�¿���,   
     * ��Release״̬�¹ر�����ʾ��������   
     * */    
     public static final boolean DEBUG = true; 
    /** ���󱨸��ļ�����չ�� */    
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
        System.out.println("������");
        
    }

    /**
     * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. �����߿��Ը����Լ���������Զ����쳣�����߼�
     * 
     * @param ex
     * @return true:��������˸��쳣��Ϣ;���򷵻�false
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
        //ʹ��Toast����ʾ�쳣��Ϣ    
        new Thread() {    
            @Override    
            public void run() {    
                Looper.prepare();    
                Toast toast = Toast.makeText(mContext, "������������˳�:\r\n" + msg,   
                        Toast.LENGTH_LONG);   
                toast.setGravity(Gravity.CENTER, 0, 0);   
                toast.show();   
//              MsgPrompt.showMsg(mContext, "���������", msg+"\n��ȷ���˳�");   
                Looper.loop();    
            }    
        }.start();    
        //�ռ��豸��Ϣ    
        collectCrashDeviceInfo(mContext);    
        //������󱨸��ļ�    
        saveCrashInfoToFile(ex);    
        //���ʹ��󱨸浽������    
        //sendCrashReportsToServer(mContext);    
        return true;    
    }    
    /**   
     * ���������Ϣ���ļ���   
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
             t.setToNow(); // ȡ��ϵͳʱ��   
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
     * �ռ�����������豸��Ϣ   
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
         //ʹ�÷������ռ��豸��Ϣ.��Build���а��������豸��Ϣ,    
         //����: ϵͳ�汾��,�豸������ �Ȱ������Գ����������Ϣ    
         //������Ϣ��ο�����Ľ�ͼ    
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