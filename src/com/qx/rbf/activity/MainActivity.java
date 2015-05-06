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
		//设置商品为点击状态
		three_big_type_goods.setImageBitmap(MyApplication.goodsPressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
		
		super.onResume();
	}

	/**
	 * initActionBar 功能:
	 * 初始化导航条
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.search_global_navigate_up);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		searchImageView = (ImageView) findViewById(R.id.searchImageView);
		//添加监听器
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
		//设置商品为点击状态
		three_big_type_goods.setPressed(true);
		//设置监听
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		//初始化fb 和 fhttp
		Common.fb = FinalBitmap.create(this);
		Common.fb.configBitmapLoadThreadSize(4);
		Common.fb.configDiskCachePath(this.getBaseContext().getFilesDir().toString());//设置缓存目录；   
		Common.fb.configDiskCacheSize(1024 * 1024 * 10);//设置缓存大小 
		Common.fb.configLoadingImage(R.drawable.fruit_type);
		Common.fb.configLoadfailImage(R.drawable.fruit_type);
		
		Common.fhttp = new FinalHttp();
		Common.fhttp.configTimeout(5000);//超时时间  测试使用,实际使用时需要调高
		Common.fhttp.configCharset("utf-8");
		//初始化百度定位client
		Common.locationClient = new LocationClient(this);
		//设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		option.setOpenGps(true);
		option.setScanSpan(Common.UPDATE_TIME); 
		option.setProdName("抢鲜");
		Common.locationClient.setLocOption(option);
		Common.myListener = new MyLocationListener(this);
		//注册监听函数
		Common.registerLocation();
		if(NetWorkProvider.isNetworkAvailable(this)){
			Common.locationClient.start(); //开始定位
			
		}else{
			Toast.makeText(this, "定位失败，请打开网络", Toast.LENGTH_SHORT).show();
		}
		//初始化导航引擎  
        BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
                      mNaviEngineInitListener, new LBSAuthManagerListener() {
                          @Override
                          public void onAuthResult(int status, String msg) {
                              String str = null;
                              if (0 == status) {
                                  str = "key校验成功!";
                              } else {
                                  str = "key校验失败, " + msg;
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
		map.put("goodsText", "水果");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.vegetable_type);
		map.put("goodsText", "蔬菜");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.cookie_type);
		map.put("goodsText", "糕点");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.fish_type);
		map.put("goodsText", "生鲜");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.cooked_type);
		map.put("goodsText", "熟食");
		list.add(map);
		/******************************************/
		map = new  HashMap<String, Object>();
		map.put("goods", R.drawable.all_type);
		map.put("goodsText", "全部");
		list.add(map);
	
		return list;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == searchImageView){
			//搜索功能
			onSearchRequested();
			
		}else if(v == three_big_type_goods){
			//设置商品为点击状态
			three_big_type_goods.setImageBitmap(MyApplication.goodsPressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
			
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//其他代码
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
		//[0-6]表示 水果  蔬菜  糕点  生鲜  熟食  全部  
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
		
		//打开浮动搜索框（第一个参数默认添加到搜索框的值）
		//bundle为传递的数据
		startSearch("", false, bundle, false);
		//这个地方一定要返回真 如果只是super.onSearchRequested方法
		//不但onSearchRequested（搜索框默认值）无法添加到搜索框中
		//bundle也无法传递出去
		return true;
	}
	//返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//按下键盘上返回按钮
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
		//或者下面这种方式
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
	
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {  
        public void engineInitSuccess() {  
            //导航初始化是异步的，需要一小段时间，以这个标志来识别引擎是否初始化成功，为true时候才能发起导航  
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
