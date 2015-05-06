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
	private TextView saveInformation; //���������Ϣ
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	
	//���������
	private ImageView setting_photo; //ͷ��
	private EditText setting_nickname; //�ǳ�
	private EditText setting_phone; //�ֻ���
	private EditText setting_name; //����
	private EditText setting_gender; //�Ա�
	private EditText setting_birthday; //����
	private TextView setting_modify_password; //�޸�����
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		initActionBar();
		init();
		//�˴���������ȡ���ݴ���
		
		MyApplication.getInstance().addActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//������ƷΪ���״̬
		three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.minePressed);
		super.onResume();
	}
	
	/**
	 * initActionBar ����:
	 * ��ʼ��������
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.setting_navigate_up_back);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		search_back = (ImageView) findViewById(R.id.search_back);
		saveInformation = (TextView) findViewById(R.id.saveInformation);
		search_global_navigate_up_title.setText("������Ϣ");
		//��Ӽ�����
		search_back.setOnClickListener(this);
		//search_global_navigate_up_title.setOnClickListener(this);
		saveInformation.setOnClickListener(this);
	}
	public void init(){
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//������ƷΪ���״̬
		three_big_type_mine.setPressed(true);
		//���ü���
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		//��ʼ�����
		setting_photo = (ImageView) findViewById(R.id.setting_photo); //ͷ��
		setting_nickname = (EditText) findViewById(R.id.setting_nickname); //�ǳ�
		setting_phone = (EditText) findViewById(R.id.setting_phone); //�ֻ���
		setting_name = (EditText) findViewById(R.id.setting_name); //����
		setting_gender = (EditText) findViewById(R.id.setting_gender); //�Ա�
		setting_birthday = (EditText) findViewById(R.id.setting_birthday); //����
		setting_modify_password = (TextView) findViewById(R.id.setting_modify_password); //�޸�����
		//����ֵ�������
		
		//����
		setting_photo.setOnClickListener(this);
		
		setting_modify_password.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == search_back){
			//��������
			finish();
			
		}else if(v == saveInformation){
			//���������Ϣ
			
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
			
			//��������
			Intent intent = new Intent();
			intent.setClass(SettingActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//��������
//			Intent intent = new Intent();
//			intent.setClass(MyLovedShops.this, MineActivity.class);
//			startActivity(intent);
//			finish();
		}else if(v == setting_modify_password){
			//�޸�����
			Intent intent = new Intent(SettingActivity.this, SettingRegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "findPassword");
			startActivity(intent);
			
		}else if(v == setting_photo){
			//����ͼƬ
			changePhoto();
		}
	}
	
	//���ؼ�
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			//���¼����Ϸ��ذ�ť
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
			//�����������ַ�ʽ
			//android.os.Process.killProcess(android.os.Process.myPid()); 
		}
		
		private void changePhoto(){
			new AlertDialog.Builder(SettingActivity.this)
							.setTitle("�޸�ͷ��")
							.setIcon(R.drawable.ic_prompt)
							.setMessage("ѡ���ȡ��Ƭ��ʽ")
							.setPositiveButton("���", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss(); 
			                        /** 
			                         * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬 
			                         * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ��� 
			                         */ 
			                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
			                           
			                        /** 
			                         * ������仰����������ʽд��һ����Ч��������� 
			                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
			                         * intent.setType(""image/*");������������ 
			                         * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����" 
			                         * 
			                         */
			                      //�������ָ������������պ����Ƭ�洢��·�� 
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
							.setNegativeButton("����", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss(); 
			                        Intent intent = new Intent( 
			                                MediaStore.ACTION_IMAGE_CAPTURE); 
			                        //�������ָ������������պ����Ƭ�洢��·�� 
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
				//Toast.makeText(this, "ȡ��", 3000).show();
				return ;
			}
	        switch (requestCode) { 
	        // �����ֱ�Ӵ�����ȡ 
	        case 1: 
	            startPhotoZoom(data.getData()); 
	            break; 
	        // ����ǵ����������ʱ 
	        case 2: 
	            File temp = new File(Environment.getExternalStorageDirectory()+File.separator+"qx"+File.separator
	                    + "temp.jpg"); 
	            startPhotoZoom(Uri.fromFile(temp)); 
	            break; 
	        // ȡ�òü����ͼƬ 
	        case 3: 
	        	//Toast.makeText(this, "requestCode: "+requestCode, 3000).show();
	            /** 
	             * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� 
	             * �ڼ���֮��������ֲ����⣬Ҫ���²ü������� 
	                
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
	     * �ü�ͼƬ����ʵ�� 
	     * @param uri 
	     */ 
	    public void startPhotoZoom(Uri uri) { 
	        /* 
	         * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ 
	         * yourself_sdk_path/docs/reference/android/content/Intent.html 
	            
	         */ 
	        Intent intent = new Intent("com.android.camera.action.CROP"); 
	        intent.setDataAndType(uri, "image/*"); 
	        //�������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü� 
	        intent.putExtra("crop", "true"); 
	        Uri imageUri = Uri 
                    .fromFile(new File(Environment 
                            .getExternalStorageDirectory()+File.separator+"qx", 
                            "temp.jpg"));
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//ͼ�����  
            intent.putExtra("outputFormat",  
                    Bitmap.CompressFormat.JPEG.toString()); 
	        // aspectX aspectY �ǿ�ߵı��� 
	        intent.putExtra("aspectX", 1); 
	        intent.putExtra("aspectY", 1); 
//	        // outputX outputY �ǲü�ͼƬ��� 
	        intent.putExtra("outputX", 150);    
	        intent.putExtra("outputY", 150);  
	        intent.putExtra("scale", true);
	        intent.putExtra("noFaceDetection", true); 
	        intent.putExtra("return-data", false); 
	        startActivityForResult(intent, 3); 
	    } 
	       
	    /** 
	     * ����ü�֮���ͼƬ���� 
	     * @param picdata 
	     */ 
	    private void setPicToView() { 
	     
	        	Bitmap bm = BitmapFactory.decodeFile(Environment 
                        .getExternalStorageDirectory()+File.separator+"qx/temp.jpg");
	            Drawable drawable = new BitmapDrawable(bm); 
	            setting_photo.setImageDrawable(drawable);
	       
	    }
		
		
}