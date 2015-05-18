package com.qx.rbf.activity.subMineActivity;

import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.subGoodsActivity.EvaluateActivity;
import com.qx.rbf.activity.subShopsActivity.ShopsDetailsActivity;
import com.qx.rbf.adapter.MyAdapter;
import com.qx.rbf.widget.DelSlideListView;
import com.qx.rbf.widget.ListViewonSingleTapUpListenner;
import com.qx.rbf.widget.OnDeleteListioner;
import com.qx.rbf.widget.OnEvaluateListener;
import com.qx.rbf.widget.OnShopListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore.Action;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyOrdersActivity extends ActionBarActivity implements OnClickListener, ListViewonSingleTapUpListenner, OnDeleteListioner, OnShopListener, OnEvaluateListener{

	private ImageView back;
	private TextView search_global_navigate_up_title;//导航条名字
	
	LinkedList<HashMap<String, Object>> mlist = new LinkedList<HashMap<String,Object>>();
	MyAdapter mMyAdapter;
	DelSlideListView mDelSlideListView;
	int delID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_orders);
		
		initActionBar();
		initWidget();
		//加入application中
		MyApplication.getInstance().addActivity(this);
	}
	public void initWidget(){
		this.mDelSlideListView = (DelSlideListView) this.findViewById(R.id.listv);
		mlist = getData();
		mMyAdapter = new MyAdapter(this, mlist);
		mDelSlideListView.setAdapter(mMyAdapter);
		mDelSlideListView.setDeleteListioner(this);
		mDelSlideListView.setSingleTapUpListenner(this);
		mMyAdapter.setOnDeleteListioner(this);
		mMyAdapter.setOnShopListener(this);
		mMyAdapter.setOnEvaluateListener(this);
	}
	public LinkedList<HashMap<String, Object>> getData(){
		LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		for(int i=0;i<50;i++){
			map = new HashMap<String, Object>();
			map.put("goodNameTv", "红富士苹果"+i);
			map.put("originPriceTv", "商品原价：7元/斤");
			map.put("qxPriceTv", "抢鲜价格 ：3.5元/斤");
			map.put("buyNumberTv", "销售数量 ：3斤");
			map.put("orderCostsTv", "7元");
			map.put("fishTicketsAccountTv", "0.5元");
			map.put("orderRealCostsTv", "7.0元");
			map.put("orderTimeTv", "2014/11/20  17:43:57");
			map.put("goodId", "goodId:"+i);
			map.put("shopId", "shopId:"+i);
			list.add(map);
		}
		return list;
	}
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		back = (ImageView)findViewById(R.id.search_back);
		search_global_navigate_up_title = (TextView)findViewById(R.id.search_global_navigate_up_title);
		
		//赋值及监听
		back.setOnClickListener(this);
		search_global_navigate_up_title.setText("我的订单");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			finish();
		}
	}
	@Override
	public void onSingleTapUp() {
		
	}


	@Override
	public boolean isCandelete(int position) {
		return true;
	}

	@Override
	public void onBack() {
		
	}

	@Override
	public void onDelete(int ID) {
		delID = ID;
//		System.out.println("**********"+map.get("shopId").toString());
//		System.out.println("**********"+map.get("goodId").toString());
		mlist.remove(delID); //可利用ID获取shopId goodId等信息
		mDelSlideListView.deleteItem();
		mMyAdapter.notifyDataSetChanged();
	}

	@Override
	public void onEnterShop(int ID) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = mlist.get(ID);
//		System.out.println("**********"+map.get("shopId").toString());
//		System.out.println("**********"+map.get("goodId").toString());
		Intent intent = new Intent();
		intent.setClass(MyOrdersActivity.this, ShopsDetailsActivity.class);
		startActivity(intent);
	}
	@Override
	public void onEvaluate(int ID) {
		// TODO Auto-generated method stub
//		System.out.println("**********"+map.get("shopId").toString());
//		System.out.println("**********"+map.get("goodId").toString());
		Intent intent = new Intent();
		intent.setClass(MyOrdersActivity.this, EvaluateActivity.class);
		startActivity(intent);
	}

	
}
