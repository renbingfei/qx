package com.qx.rbf.activity.subMineActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.MainActivity;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.ShopsActivity;
import com.qx.rbf.activity.subShopsActivity.ShopsDetailsActivity;
import com.qx.rbf.adapter.ShopsPullAdapter;
import com.qx.rbf.utils.GoodsData;
import com.qx.rbf.view.pulltofresh.PullToRefreshListView;
import com.qx.rbf.view.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.qx.rbf.view.pulltofresh.task.ShopsPullTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class SettingActivity extends ActionBarActivity implements OnClickListener{


	private ArrayList<View> mViewArray = new ArrayList<View>();

	private TextView search_global_navigate_up_title;
	private ImageView search_back;
	private TextView saveInformation; //保存个人信息
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	
	//设置相关项
	private ImageView setting_photo; //头像
	private EditText setting_nickname; //昵称
	private EditText setting_phone; //手机号
	private EditText setting_name; //名字
	private EditText setting_gender; //性别
	private EditText setting_birthday; //生日
	private TextView setting_modify_password; //修改密码
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		initActionBar();
		init();
		//此处添加网络获取数据代码
		
		MyApplication.getInstance().addActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//设置商品为点击状态
		three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.minePressed);
		super.onResume();
	}
	
	/**
	 * initActionBar 功能:
	 * 初始化导航条
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.setting_navigate_up_back);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		search_back = (ImageView) findViewById(R.id.search_back);
		saveInformation = (TextView) findViewById(R.id.saveInformation);
		search_global_navigate_up_title.setText("基本信息");
		//添加监听器
		search_back.setOnClickListener(this);
		//search_global_navigate_up_title.setOnClickListener(this);
		saveInformation.setOnClickListener(this);
	}
	public void init(){
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//设置商品为点击状态
		three_big_type_mine.setPressed(true);
		//设置监听
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		//初始化组件
		setting_photo = (ImageView) findViewById(R.id.setting_photo); //头像
		setting_nickname = (EditText) findViewById(R.id.setting_nickname); //昵称
		setting_phone = (EditText) findViewById(R.id.setting_phone); //手机号
		setting_name = (EditText) findViewById(R.id.setting_name); //名字
		setting_gender = (EditText) findViewById(R.id.setting_gender); //性别
		setting_birthday = (EditText) findViewById(R.id.setting_birthday); //生日
		setting_modify_password = (TextView) findViewById(R.id.setting_modify_password); //修改密码
		//设置值，待添加
		
		//监听
		setting_photo.setOnClickListener(this);
		
		setting_modify_password.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == search_back){
			//搜索功能
			finish();
			
		}else if(v == saveInformation){
			//保存个人信息
			
		}else if(v == three_big_type_goods){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			//
			Intent intent = new Intent();
			intent.setClass(SettingActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			Intent intent = new Intent();
			intent.setClass(SettingActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//其他代码
//			Intent intent = new Intent();
//			intent.setClass(MyLovedShops.this, MineActivity.class);
//			startActivity(intent);
//			finish();
		}else if(v == setting_modify_password){
			//修改密码
			Intent intent = new Intent(SettingActivity.this, SettingRegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "findPassword");
			startActivity(intent);
			
		}else if(v == setting_photo){
			//更换图片
			changePhoto();
		}
	}
	
	//返回键
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			//按下键盘上返回按钮
			if(keyCode == KeyEvent.KEYCODE_BACK){
	 
//				new AlertDialog.Builder(this)
//					.setIcon(R.drawable.ic_prompt)
//					.setTitle(R.string.prompt)
//					.setMessage(R.string.quit_desc)
//					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					})
//					.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int whichButton) {
//							MyApplication.getInstance().exit();
//						}
//					}).show();
//				
				finish();
				return true;
			}else{		
				return super.onKeyDown(keyCode, event);
			}
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
			//或者下面这种方式
			//android.os.Process.killProcess(android.os.Process.myPid()); 
		}
		
		private void changePhoto(){
			new AlertDialog.Builder(SettingActivity.this)
							.setTitle("修改头像")
							.setIcon(R.drawable.ic_prompt)
							.setMessage("选择获取照片方式")
							.setPositiveButton("相册", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss(); 
			                        /** 
			                         * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码， 
			                         * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下 
			                         */ 
			                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
			                           
			                        /** 
			                         * 下面这句话，与其它方式写是一样的效果，如果： 
			                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
			                         * intent.setType(""image/*");设置数据类型 
			                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 
			                         * 
			                         */
			                      //下面这句指定调用相机拍照后的照片存储的路径 
			                        File dir = new File(Environment 
	                                        .getExternalStorageDirectory()+File.separator+"qx");
			                        if(!dir.exists()){
			                        	dir.mkdir();
			                        }
			                        intent.setDataAndType( 
			                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
			                                "image/*"); 
			                        startActivityForResult(intent, 1); 
								}
							})
							.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss(); 
			                        Intent intent = new Intent( 
			                                MediaStore.ACTION_IMAGE_CAPTURE); 
			                        //下面这句指定调用相机拍照后的照片存储的路径 
			                        File dir = new File(Environment 
	                                        .getExternalStorageDirectory()+File.separator+"qx");
			                        if(!dir.exists()){
			                        	dir.mkdir();
			                        }
			                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri 
			                                .fromFile(new File(Environment 
			                                        .getExternalStorageDirectory()+File.separator+"qx"+File.separator
			                	                    + "temp.jpg"))); 
			                        startActivityForResult(intent, 2); 
								}
							})
							.show();
							
		}
		@Override 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
			//Toast.makeText(this, "resultCode: "+resultCode, 3000).show();
			if(resultCode == RESULT_CANCELED && requestCode!=3){
				//Toast.makeText(this, "取消", 3000).show();
				return ;
			}
	        switch (requestCode) { 
	        // 如果是直接从相册获取 
	        case 1: 
	            startPhotoZoom(data.getData()); 
	            break; 
	        // 如果是调用相机拍照时 
	        case 2: 
	            File temp = new File(Environment.getExternalStorageDirectory()+File.separator+"qx"+File.separator
	                    + "temp.jpg"); 
	            startPhotoZoom(Uri.fromFile(temp)); 
	            break; 
	        // 取得裁剪后的图片 
	        case 3: 
	        	//Toast.makeText(this, "requestCode: "+requestCode, 3000).show();
	            /** 
	             * 非空判断大家一定要验证，如果不验证的话， 
	             * 在剪裁之后如果发现不满意，要重新裁剪，丢弃 
	                
	             */ 
	            if(data != null){ 
	                setPicToView(); 
	            } 
	            break; 
	        default: 
	            break; 
	   
	        } 
	        super.onActivityResult(requestCode, resultCode, data); 
	    } 
	       
	    /** 
	     * 裁剪图片方法实现 
	     * @param uri 
	     */ 
	    public void startPhotoZoom(Uri uri) { 
	        /* 
	         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页 
	         * yourself_sdk_path/docs/reference/android/content/Intent.html 
	            
	         */ 
	        Intent intent = new Intent("com.android.camera.action.CROP"); 
	        intent.setDataAndType(uri, "image/*"); 
	        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪 
	        intent.putExtra("crop", "true"); 
	        Uri imageUri = Uri 
                    .fromFile(new File(Environment 
                            .getExternalStorageDirectory()+File.separator+"qx", 
                            "temp.jpg"));
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出  
            intent.putExtra("outputFormat",  
                    Bitmap.CompressFormat.JPEG.toString()); 
	        // aspectX aspectY 是宽高的比例 
	        intent.putExtra("aspectX", 1); 
	        intent.putExtra("aspectY", 1); 
//	        // outputX outputY 是裁剪图片宽高 
	        intent.putExtra("outputX", 150);    
	        intent.putExtra("outputY", 150);  
	        intent.putExtra("scale", true);
	        intent.putExtra("noFaceDetection", true); 
	        intent.putExtra("return-data", false); 
	        startActivityForResult(intent, 3); 
	    } 
	       
	    /** 
	     * 保存裁剪之后的图片数据 
	     * @param picdata 
	     */ 
	    private void setPicToView() { 
	     
	        	Bitmap bm = BitmapFactory.decodeFile(Environment 
                        .getExternalStorageDirectory()+File.separator+"qx/temp.jpg");
	            Drawable drawable = new BitmapDrawable(bm); 
	            setting_photo.setImageDrawable(drawable);
	       
	    }
		
		
}