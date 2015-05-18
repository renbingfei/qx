package com.qx.rbf.activity.subGoodsActivity;

import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.R;
import com.qx.rbf.adapter.GoodsEvaluateAdapter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GoodsEvaluateActivity extends ActionBarActivity implements OnClickListener{
	
	private ImageView back;
	private TextView search_global_navigate_up_title;//����������
	private ListView goodsEvaluateLv; //�����б�
	private GoodsEvaluateAdapter adapter; //
	private LinkedList<HashMap<String, Object>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_evaluate);
		
		initActionBar();
		initWidget();
		init();
		//����application��
		MyApplication.getInstance().addActivity(this);
	}
	public void init(){
		data = new LinkedList<HashMap<String,Object>>();
		data = getData();
		adapter = new GoodsEvaluateAdapter(this, data, R.layout.goods_evaluate_item, 
				new String[]{"nickName","goodsItemRate","evaluateTime","evaluateInfo"}, 
				new int[]{R.id.nickNameTv,R.id.goodsItemRate,R.id.evaluateTimeTv,R.id.evaluateInfoTv});
		goodsEvaluateLv.setAdapter(adapter);
		
	}
	
	public void initWidget(){
		goodsEvaluateLv = (ListView)findViewById(R.id.goodsEvaluateLv);
		
	}
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		back = (ImageView)findViewById(R.id.search_back);
		search_global_navigate_up_title = (TextView)findViewById(R.id.search_global_navigate_up_title);
		
		//��ֵ������
		back.setOnClickListener(this);
		search_global_navigate_up_title.setText("��������");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			finish();
		}
	}
	
	private LinkedList<HashMap<String, Object>> getData(){
		LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		HashMap<String, Object> map;
		for(int i=0;i<4;i++){
			map = new HashMap<String, Object>();
			map.put("nickName","��ͷ�ְ�"+i);
			map.put("goodsItemRate",3.5f );
			map.put("evaluateTime", "2015-03-25");
			map.put("evaluateInfo", "�ϰ��˺ܺã����ߵ�ʱ�򻹶����һ��ƻ����ˮ�������ʣ��´λ�����");
			list.add(map);
		}
		return list;
	}
}
