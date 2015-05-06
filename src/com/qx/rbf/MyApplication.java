package com.qx.rbf;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.tencent.bugly.crashreport.CrashReport;

import com.qx.rbf.activity.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class MyApplication extends Application{

	private List<Activity> activityList=new LinkedList<Activity>();

	private static MyApplication instance;
	
	
	 
	 @Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Context appContext =  this.getApplicationContext();         

        String appId = "900002930";   //上Bugly(bugly.qq.com)注册产品获取的AppId
        
        boolean isDebug = true ;  //true代表App处于调试阶段，false代表App发布阶段
        
        CrashReport. initCrashReport(appContext , appId ,isDebug);  //初始化SDK  
        CrashReport. setUserId( "rbf");  //设置用户的唯一标识
        initBitmap();
	}

	public static MyApplication getInstance(){
		 if(null == instance){
			 instance = new MyApplication();
		 }
		return instance;
	 }

	 public void addActivity(Activity activity){
		 this.activityList.add(activity);
	 }
	 
	 public void exit(){
		 for(Activity activity:activityList){
			 if(activity!=null)
			 activity.finish();
		 }
		 
		 System.exit(0);
	 }
	 
	 public static Bitmap goodsPressed = null;
	 public static Bitmap goodsUnpressed = null;
	 public static Bitmap minePressed = null;
	 public static Bitmap mineUnpressed = null;
	 public static Bitmap shopPressed = null;
	 public static Bitmap shopUnpressed = null;
	 
	 /** 
	  * 以最省内存的方式读取本地资源的图片 
	  * @param context 
	  * @param resId 
	  * @return 
	  */  
	 public static Bitmap readBitMap(Context context, int resId){  
	     BitmapFactory.Options opt = new BitmapFactory.Options();  
	     opt.inPreferredConfig = Bitmap.Config.RGB_565;   
	     opt.inPurgeable = true;  
	     opt.inInputShareable = true;  
	        //获取资源图片  
	     InputStream is = context.getResources().openRawResource(resId);  
	         return BitmapFactory.decodeStream(is,null,opt);  
	 }
	 
	 //初始化三个bitmap
	 private void initBitmap(){
		 if(goodsPressed==null){
			 goodsPressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_goods_pressed);
		 }
		 if(goodsUnpressed==null){
			 goodsUnpressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_goods_unpressed);
		 }
		 if(minePressed==null){
			 minePressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_mine_pressed);
		 }
		 if(mineUnpressed==null){
			 mineUnpressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_mine_unpressed);
		 }
		 if(shopPressed==null){
			 shopPressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_shop_pressed);
		 }
		 if(shopUnpressed==null){
			 shopUnpressed = readBitMap(getApplicationContext(),R.drawable.three_big_type_shop_unpressed);
		 }
		 
		 
	 }
}
