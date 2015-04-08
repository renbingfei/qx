package com.qx.rbf.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import com.qx.rbf.adapter.MyGridViewAdapter;
import com.qx.rbf.adapter.PullAdapter;
import com.qx.rbf.utils.*;
import com.qx.rbf.view.ExpandTabView;
import com.qx.rbf.view.LeftFilterView;
import com.qx.rbf.view.MiddleFilterView;
import com.qx.rbf.view.RightFilterView;
import com.qx.rbf.view.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.qx.rbf.view.pulltofresh.PullToRefreshListView;
import com.qx.rbf.view.pulltofresh.task.PullTask;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsActivity extends ActionBarActivity implements OnClickListener{
	private ExpandTabView expandTabView;

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
	private LinkedList<String> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private PullAdapter pullAdapter;
	private String[] mStrings = { "��ʼ����01","��ʼ����02","��ʼ����03","��ʼ����04","��ʼ����05" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_main);
		initActionBar();
		init();
		//��ʼ��ɸѡ��
		initView();
		initVaule();
		initListener();

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
		search_global_navigate_up_title.setText("��Ʒ");
		//��Ӽ�����
		searchImageView.setOnClickListener(this);
	}
	public void init(){
		three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
		three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
		three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
		//������ƷΪ���״̬
		three_big_type_goods.setPressed(true);
		//���ü���
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
	}
	
	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);

		viewLeft = new LeftFilterView(this, FilterDataSource.createTypeFilterItems());

		viewMiddle = new MiddleFilterView(this,FilterDataSource.getGroupData(),FilterDataSource.getItemData());

		viewRight = new RightFilterView(this, FilterDataSource.createSortFilterItems());
		//����ˢ��
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		pullAdapter = new PullAdapter(mListItems, GoodsActivity.this);
		mListView.setAdapter(pullAdapter);
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
		if(v == searchImageView){
			//��������
		}else if(v == three_big_type_goods){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
		}else if(v == three_big_type_shop){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//��������
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
			//��������
			
		}
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		public void onRefresh() {
		PullTask pullTask =	new PullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mListItems);
		pullTask.execute();
		}
	};
}
