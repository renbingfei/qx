package com.qx.rbf.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.exception.AfinalException;

import com.qx.rbf.utils.*;
import com.qx.rbf.adapter.MyGridViewAdapter;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//设置商品为点击状态
		three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
		three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
		three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
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
		Common.fb.configLoadingImage(R.drawable.ic_launcher);
		Common.fb.configLoadfailImage(R.drawable.ic_launcher);
		
		Common.fhttp = new FinalHttp();
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
		}else if(v == three_big_type_goods){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
		}else if(v == three_big_type_shop){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//其他代码
			
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//[0-6]表示 水果  蔬菜  糕点  生鲜  熟食  全部  
		switch(position){
			case 0:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GoodsActivity.class);
				startActivity(intent);
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
		}
	}
}
