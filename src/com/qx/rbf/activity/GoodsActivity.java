package com.qx.rbf.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.subGoodsActivity.GoodsDetailsActivity;
import com.qx.rbf.adapter.PullAdapter;
import com.qx.rbf.provider.SearchSuggestionSampleProvider;
import com.qx.rbf.utils.*;
import com.qx.rbf.view.ExpandTabView;
import com.qx.rbf.view.LeftFilterView;
import com.qx.rbf.view.MiddleFilterView;
import com.qx.rbf.view.RightFilterView;
import com.qx.rbf.view.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.qx.rbf.view.pulltofresh.PullToRefreshListView;
import com.qx.rbf.view.pulltofresh.task.PullTask;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener{
	//该商品类型
	private String goodsType;
	private ExpandTabView expandTabView;
	private String query;

	private ArrayList<View> mViewArray = new ArrayList<View>();

	private LeftFilterView viewLeft;
	private MiddleFilterView viewMiddle;
	private RightFilterView viewRight;
	private TextView search_global_navigate_up_title;
	private ImageView searchImageView;
	private ImageView search_back;
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//下拉刷新
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private PullAdapter pullAdapter;
	public static LinkedList<HashMap<String,Object>> mDatas;
	private Handler handler = new MyHandler();  //接受异步数据
	public ProgressDialog progressDialog;
	public GoodsData goodsData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_main);
		getIntents();
		initActionBar();
		init();
		//此处添加网络获取数据代码
		progressDialog = ProgressDialog.show(GoodsActivity.this, "提示","正在获取数据，请等待...");
		progressDialog.setCanceledOnTouchOutside(true);
		
		//初始化筛选框
		initView();
		initVaule();
		initListener();
		MyApplication.getInstance().addActivity(this);
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
	
	public void getIntents(){
		Intent intent = getIntent();
		if(intent!=null){
			goodsType = intent.getStringExtra("goodsType");
			if(goodsType == null){
				goodsType = "all";
			}
		}
		query=intent.getStringExtra(SearchManager.QUERY);
		//保存搜索记录
		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
				SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
		suggestions.saveRecentQuery(query, null);
	}
	/**
	 * initActionBar 功能:
	 * 初始化导航条
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.search_global_navigate_up_back);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		search_back = (ImageView) findViewById(R.id.search_back);
		searchImageView = (ImageView) findViewById(R.id.searchImageView);
		search_global_navigate_up_title.setText(Common.goodsNameToType.get(goodsType));
		//添加监听器
		searchImageView.setOnClickListener(this);
		search_back.setOnClickListener(this);
		search_global_navigate_up_title.setOnClickListener(this);
	}
	public void init(){
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//设置商品为点击状态
		three_big_type_goods.setPressed(true);
		//设置监听
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		
		goodsData = new GoodsData(this, handler);
	}
	
	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);

		viewLeft = new LeftFilterView(this, FilterDataSource.createTypeFilterItems(goodsType));

		viewMiddle = new MiddleFilterView(this,FilterDataSource.getGroupData(goodsType),FilterDataSource.getItemData(goodsType));

		viewRight = new RightFilterView(this, FilterDataSource.createSortFilterItems(goodsType));
		//下拉刷新
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		//获取数据
		mDatas = new LinkedList<HashMap<String,Object>>();
		GoodsData.getData();
		pullAdapter = new PullAdapter(mDatas, GoodsActivity.this);
		mListView.setAdapter(pullAdapter);
		//设置listview监听
		mListView.setOnItemClickListener(this);
	}

	private void initVaule() {

		mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);

		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("分类");
		mTextArray.add("位置");
		mTextArray.add("排序");

		expandTabView.setValue(mTextArray, mViewArray);

		expandTabView.setTitle("分类", 0);
		expandTabView.setTitle("位置", 1);
		expandTabView.setTitle("排序", 2);

	}

	private void initListener() {

		viewLeft.setOnSelectListener(new LeftFilterView.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});

		viewMiddle.setOnSelectListener(new MiddleFilterView.OnItemSelectListener() {

			@Override
			public void getValue(String showText) {
				onRefresh(viewMiddle, showText);

			}
		});

		viewRight.setOnSelectListener(new RightFilterView.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});

	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}

		Toast.makeText(GoodsActivity.this, showText, Toast.LENGTH_SHORT).show();

	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == search_back){
			//返回商品主页面
			Intent intent = new Intent();
			intent.setClass(GoodsActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}else if(v == search_global_navigate_up_title){
			//回到顶部
			mListView.setSelection(0);
		}
		else if(v == searchImageView){
			//搜索功能
			onSearchRequested();
			
		}else if(v == three_big_type_goods){
			three_big_type_goods.setImageBitmap(MyApplication.goodsPressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			Intent intent = new Intent();
			intent.setClass(GoodsActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
		}else if(v == three_big_type_mine){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//其他代码
			
			Intent intent = new Intent();
			intent.setClass(GoodsActivity.this, MineActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
		PullTask pullTask =	new PullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mDatas);
		pullTask.execute();
		}
	};
	//搜索功能
	public void doSearchQuery(){
		final Intent searchIntent = getIntent();
		//获得搜索框里值
		String query=searchIntent.getStringExtra(SearchManager.QUERY);
		//保存搜索记录
		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
				SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
			//获取传递的数据
			Bundle bundled=searchIntent.getBundleExtra(SearchManager.APP_DATA);
		}
	}
	
	@Override
	public boolean onSearchRequested(){
		
		startSearch("", false, null, false);
		return true;
	}
	
	@Override
	public void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		//获得搜索框里值
		query=intent.getStringExtra(SearchManager.QUERY);
		//tvquery.setText(query);
		
		//保存搜索记录
		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
				SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			//获取传递的数据
			Bundle bundled=intent.getBundleExtra(SearchManager.APP_DATA);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {	
			//返回商品主页面
			Intent intent = new Intent();
			intent.setClass(GoodsActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class MyHandler extends Handler{
		private boolean isFirst = true;
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case 0:
					if(progressDialog.isShowing())
					progressDialog.cancel();
					//mDatas = (LinkedList<HashMap<String, Object>>) msg.getData().get("goodsData");
					pullAdapter.notifyDataSetChanged();
					break;
				case 1:
					break;
			}
			
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView tvName = (TextView) view.findViewById(R.id.goods_item_name);
		//Toast.makeText(this,tvName.getTag().toString(), 3000).show();
		Intent intent = new Intent(GoodsActivity.this, GoodsDetailsActivity.class);
		//传输相关商品id
		
		startActivity(intent);
		//finish();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
}
