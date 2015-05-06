package com.qx.rbf.activity.subMineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.GoodsActivity;
import com.qx.rbf.activity.MainActivity;
import com.qx.rbf.activity.MineActivity;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.ShopsActivity;
import com.qx.rbf.activity.subGoodsActivity.GoodsDetailsActivity;
import com.qx.rbf.adapter.PullAdapter;
import com.qx.rbf.provider.SearchSuggestionSampleProvider;
import com.qx.rbf.utils.Common;
import com.qx.rbf.utils.FilterDataSource;
import com.qx.rbf.utils.GoodsData;
import com.qx.rbf.view.ExpandTabView;
import com.qx.rbf.view.LeftFilterView;
import com.qx.rbf.view.MiddleFilterView;
import com.qx.rbf.view.RightFilterView;
import com.qx.rbf.view.pulltofresh.PullToRefreshListView;
import com.qx.rbf.view.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.qx.rbf.view.pulltofresh.task.PullTask;

public class MyLovedGoods extends ActionBarActivity implements OnClickListener, OnItemClickListener{
	//����Ʒ����
	private String goodsType;

	private ArrayList<View> mViewArray = new ArrayList<View>();

	private TextView search_global_navigate_up_title;
	private ImageView search_back;
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//����ˢ��
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private PullAdapter pullAdapter;
	public static LinkedList<HashMap<String,Object>> mDatas;
	private Handler handler = new MyHandler();  //�����첽����
	public ProgressDialog progressDialog;
	public GoodsData goodsData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_loved_goods_main);
		initActionBar();
		init();
		//�˴���������ȡ���ݴ���
		progressDialog = ProgressDialog.show(MyLovedGoods.this, "��ʾ","���ڻ�ȡ���ݣ���ȴ�...");
		progressDialog.setCanceledOnTouchOutside(true);
		
		//��ʼ��ɸѡ��
		initView();
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
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		search_back = (ImageView) findViewById(R.id.search_back);
		search_global_navigate_up_title.setText("�ҹ�ע����Ʒ");
		//��Ӽ�����
		search_back.setOnClickListener(this);
		search_global_navigate_up_title.setOnClickListener(this);
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
		
		goodsData = new GoodsData(this, handler);
	}
	
	private void initView() {
		//����ˢ��
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		//��ȡ����
		mDatas = new LinkedList<HashMap<String,Object>>();
		GoodsData.getMyLovedGoodsData();
		pullAdapter = new PullAdapter(mDatas, MyLovedGoods.this);
		mListView.setAdapter(pullAdapter);
		//����listview����
		mListView.setOnItemClickListener(this);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == search_back){
			//������ҳ��
			finish();
		}else if(v == search_global_navigate_up_title){
			//�ص�����
			mListView.setSelection(0);
		}
		else if(v == three_big_type_goods){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			Intent intent = new Intent();
			intent.setClass(MyLovedGoods.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//��������
			Intent intent = new Intent();
			intent.setClass(MyLovedGoods.this, ShopsActivity.class);
			startActivity(intent);
			finish();
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//��������
			
//			Intent intent = new Intent();
//			intent.setClass(MyLovedGoods.this, MineActivity.class);
//			startActivity(intent);
//			finish();
		}
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
		PullTask pullTask =	new PullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mDatas);
		pullTask.execute();
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {		
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
		Intent intent = new Intent(MyLovedGoods.this, GoodsDetailsActivity.class);
		//���������Ʒid
		
		startActivity(intent);
		//finish();
	}
}

