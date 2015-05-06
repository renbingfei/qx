package com.qx.rbf.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.subShopsActivity.ShopsDetailsActivity;
import com.qx.rbf.adapter.ShopsPullAdapter;
import com.qx.rbf.provider.SearchSuggestionSampleProvider;
import com.qx.rbf.utils.*;
import com.qx.rbf.view.ExpandTabView;
import com.qx.rbf.view.LeftFilterView;
import com.qx.rbf.view.MiddleFilterView;
import com.qx.rbf.view.RightFilterView;
import com.qx.rbf.view.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.qx.rbf.view.pulltofresh.PullToRefreshListView;
import com.qx.rbf.view.pulltofresh.task.ShopsPullTask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
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

public class ShopsActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener{
	//����Ʒ����
	private ExpandTabView expandTabView;
	private String query;

	private ArrayList<View> mViewArray = new ArrayList<View>();

	private LeftFilterView viewLeft;
	private MiddleFilterView viewMiddle;
	private RightFilterView viewRight;
	private TextView search_global_navigate_up_title;
	private ImageView searchImageView;
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//����ˢ��
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private ShopsPullAdapter pullAdapter;
	public static LinkedList<HashMap<String,Object>> mDatas;
	private Handler handler = new MyHandler();  //�����첽����
	public ProgressDialog progressDialog;
	public GoodsData shopsData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops_main);
		getIntents();
		initActionBar();
		init();
		//�˴���������ȡ���ݴ���
		//�˴���������ȡ���ݴ���
		progressDialog = ProgressDialog.show(ShopsActivity.this, "��ʾ","���ڻ�ȡ���ݣ���ȴ�...");
		progressDialog.setCanceledOnTouchOutside(true);
		
		//��ʼ��ɸѡ��
		initView();
		initVaule();
		initListener();
		MyApplication.getInstance().addActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//������ƷΪ���״̬
		//������ƷΪ���״̬
		three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopPressed);
		three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
		super.onResume();
	}
	
	public void getIntents(){
		Intent intent = getIntent();
		if(intent!=null){
		
		}
//		query=intent.getStringExtra(SearchManager.QUERY);
//		//����������¼
//		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
//				SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
//		suggestions.saveRecentQuery(query, null);
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
		search_global_navigate_up_title.setText("����");
		//��Ӽ�����
		searchImageView.setOnClickListener(this);
		search_global_navigate_up_title.setOnClickListener(this);
	}
	public void init(){
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//������ƷΪ���״̬
		three_big_type_shop.setPressed(true);
		//���ü���
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
		//
		shopsData = new GoodsData(this, handler);
	}
	
	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);

		viewLeft = new LeftFilterView(this, FilterDataSource.createShopTypeFilterItems());

		viewMiddle = new MiddleFilterView(this,FilterDataSource.getGroupData(""),FilterDataSource.getItemData(""));

		viewRight = new RightFilterView(this, FilterDataSource.createShopSortFilterItems());
		//����ˢ��
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		//��ȡ����
		mDatas = new LinkedList<HashMap<String,Object>>();
		GoodsData.getShopsData();
		pullAdapter = new ShopsPullAdapter(mDatas, ShopsActivity.this);
		mListView.setAdapter(pullAdapter);
		//listview����
		mListView.setOnItemClickListener(this);
	}

	private void initVaule() {

		mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);

		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("����");
		mTextArray.add("λ��");
		mTextArray.add("����");

		expandTabView.setValue(mTextArray, mViewArray);

		expandTabView.setTitle("����", 0);
		expandTabView.setTitle("λ��", 1);
		expandTabView.setTitle("����", 2);

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

		Toast.makeText(ShopsActivity.this, showText, Toast.LENGTH_SHORT).show();

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
		if(v == searchImageView){
			//��������
			onSearchRequested();
			
		}else if(v == search_global_navigate_up_title){
			//�ص�����
			mListView.setSelection(0);
		}else if(v == three_big_type_goods){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			//
			Intent intent = new Intent();
			intent.setClass(ShopsActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopPressed);
			three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
			
			//��������
			
		}else if(v == three_big_type_mine){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//��������
			Intent intent = new Intent();
			intent.setClass(ShopsActivity.this, MineActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
		ShopsPullTask pullTask =	new ShopsPullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mDatas);
		pullTask.execute();
		}
	};
	//��������
	public void doSearchQuery(){
		final Intent searchIntent = getIntent();
		//�����������ֵ
		String query=searchIntent.getStringExtra(SearchManager.QUERY);
		//����������¼
		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
				SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
			//��ȡ���ݵ�����
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
		//�����������ֵ
		query=intent.getStringExtra(SearchManager.QUERY);
		//tvquery.setText(query);
		
		//����������¼
		SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
				SearchSuggestionSampleProvider.AUTHORITYForShop, SearchSuggestionSampleProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			//��ȡ���ݵ�����
			Bundle bundled=intent.getBundleExtra(SearchManager.APP_DATA);
		}
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
			//�����������ַ�ʽ
			//android.os.Process.killProcess(android.os.Process.myPid()); 
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
			TextView tvName = (TextView) view.findViewById(R.id.shops_item_name);
			//Toast.makeText(this,tvName.getTag().toString(), 3000).show();
			Intent intent = new Intent(ShopsActivity.this, ShopsDetailsActivity.class);
			//��������̼�id
			
			startActivity(intent);
		}
}
