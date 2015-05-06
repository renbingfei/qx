package com.qx.rbf.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.qx.rbf.MyApplication;
import com.qx.rbf.listener.MyLocationListener;
import com.qx.rbf.utils.*;
import com.qx.rbf.activity.subGoodsActivity.GoodsDetailsActivity;
import com.qx.rbf.adapter.MyGridViewAdapter;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener {

	private TextView search_global_navigate_up_title;
	private ImageView searchImageView;
	private GridView gridView ;
	private List<HashMap<String,Object>> list ;
	private MyGridViewAdapter adapter ;
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;

	private boolean mIsEngineInitSuccess = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		init();
		MyApplication.getInstance().addActivity(this);
		System.out.println("**"+Runtime.getRuntime().maxMemory());
		System.out.println("**"+Runtime.getRuntime().freeMemory());
		System.out.println("**"+Runtime.getRuntime().totalMemory());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//������ƷΪ���״̬
		three_big_type_goods.setImageBitmap(MyApplication.goodsPressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
		
		super.onResume();
	}

	/**
	 * initActionBar ����:
	 * ��ʼ��������
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.search_global_navigate_up);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		searchImageView = (ImageView) findViewById(R.id.searchImageView);
		//��Ӽ�����
		searchImageView.setOnClickListener(this);
	}
	
	public void init(){
		gridView = (GridView) findViewById(R.id.goods_type);
		list = new ArrayList<HashMap<String,Object>>();
		adapter = new MyGridViewAdapter(this, getData(), R.layout.activity_goods_types, new String[]{"goods","goodsText"}, new int[]{R.id.goodsImageView,R.id.goodsTextView});
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//������ƷΪ���״̬
		three_big_type_goods.setPressed(true);
		//���ü���
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		//��ʼ��fb �� fhttp
		Common.fb = FinalBitmap.create(this);
		Common.fb.configBitmapLoadThreadSize(4);
		Common.fb.configDiskCachePath(this.getBaseContext().getFilesDir().toString());//���û���Ŀ¼��   
		Common.fb.configDiskCacheSize(1024 * 1024 * 10);//���û����С 
		Common.fb.configLoadingImage(R.drawable.fruit_type);
		Common.fb.configLoadfailImage(R.drawable.fruit_type);
		
		Common.fhttp = new FinalHttp();
		Common.fhttp.configTimeout(5000);//��ʱʱ��  ����ʹ��,ʵ��ʹ��ʱ��Ҫ����
		Common.fhttp.configCharset("utf-8");
		//��ʼ���ٶȶ�λclient
		Common.locationClient = new LocationClient(this);
		//���ö�λ����
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);//���صĶ�λ��������ֻ���ͷ�ķ���
		option.setOpenGps(true);
		option.setScanSpan(Common.UPDATE_TIME); 
		option.setProdName("����");
		Common.locationClient.setLocOption(option);
		Common.myListener = new MyLocationListener(this);
		//ע���������
		Common.registerLocation();
		if(NetWorkProvider.isNetworkAvailable(this)){
			Common.locationClient.start(); //��ʼ��λ
			
		}else{
			Toast.makeText(this, "��λʧ�ܣ��������", Toast.LENGTH_SHORT).show();
		}
		//��ʼ����������  
        BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
                      mNaviEngineInitListener, new LBSAuthManagerListener() {
                          @Override
                          public void onAuthResult(int status, String msg) {
                              String str = null;
                              if (0 == status) {
                                  str = "keyУ��ɹ�!";
                              } else {
                                  str = "keyУ��ʧ��, " + msg;
                              }
                              Toast.makeText(MainActivity.this, str,
                                      Toast.LENGTH_LONG).show();
                          }
        }); 
	}
	
	public List<HashMap<String,Object>> getData(){
		HashMap<String,Object> map;
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.fruit_type);
		map.put("goodsText", "ˮ��");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.vegetable_type);
		map.put("goodsText", "�߲�");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.cookie_type);
		map.put("goodsText", "���");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.fish_type);
		map.put("goodsText", "����");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.cooked_type);
		map.put("goodsText", "��ʳ");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.all_type);
		map.put("goodsText", "ȫ��");
		list.add(map);
	
		return list;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == searchImageView){
			//��������
			onSearchRequested();
			
		}else if(v == three_big_type_goods){
			//������ƷΪ���״̬
			three_big_type_goods.setImageBitmap(MyApplication.goodsPressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
			
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//��������
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//��������
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MineActivity.class);
			startActivity(intent);
			finish();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		//[0-6]��ʾ ˮ��  �߲�  ���  ����  ��ʳ  ȫ��  
		switch(position){
			case 0:
				intent.putExtra("goodsType", "fruit");
				break;
			case 1:
				intent.putExtra("goodsType", "vegetable");
				break;
			case 2:
				intent.putExtra("goodsType", "cookie");
				break;
			case 3:
				intent.putExtra("goodsType", "fish");
				break;
			case 4:
				intent.putExtra("goodsType", "cooked");
				break;
			case 5:
				intent.putExtra("goodsType", "all");
				break;
		}
		intent.setClass(MainActivity.this, GoodsActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onSearchRequested(){
		Bundle bundle=new Bundle();
		bundle.putString("data", "");
		
		//�򿪸��������򣨵�һ������Ĭ����ӵ��������ֵ��
		//bundleΪ���ݵ�����
		startSearch("", false, bundle, false);
		//����ط�һ��Ҫ������ ���ֻ��super.onSearchRequested����
		//����onSearchRequested��������Ĭ��ֵ���޷���ӵ���������
		//bundleҲ�޷����ݳ�ȥ
		return true;
	}
	//���ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//���¼����Ϸ��ذ�ť
		if(keyCode == KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_prompt)
				.setTitle(R.string.prompt)
				.setMessage(R.string.quit_desc)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						MyApplication.getInstance().exit();
					}
				}).show();
			
			return true;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//System.exit(0);
		//�����������ַ�ʽ
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
	
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {  
        public void engineInitSuccess() {  
            //������ʼ�����첽�ģ���ҪһС��ʱ�䣬�������־��ʶ�������Ƿ��ʼ���ɹ���Ϊtrueʱ����ܷ��𵼺�  
            mIsEngineInitSuccess = true;  
        }  
 
        public void engineInitStart() {  
        }  
 
        public void engineInitFail() {  
        }  
    };  
    private String getSdcardDir() {  
        if (Environment.getExternalStorageState().equalsIgnoreCase(  
                Environment.MEDIA_MOUNTED)) {  
            return Environment.getExternalStorageDirectory().toString();  
        }  
        return null;  
    }       
}
