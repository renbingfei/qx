package com.qx.rbf.activity.subMineActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.R;
import com.qx.rbf.adapter.FishTicketAdapter;

import android.drm.DrmStore.Action;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyFishTicketsActivity extends ActionBarActivity implements OnClickListener{

	private ImageView back;
	private TextView search_global_navigate_up_title;//导航条名字
	
	private ListView fishTicketLv; //鲜券
	private FishTicketAdapter adapter; //适配器
	private List<HashMap<String,Object>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_fish_tickets);
		
		initActionBar();
		init();
		//加入application中
		MyApplication.getInstance().addActivity(this);
	}
	
	public void init(){
		fishTicketLv = (ListView)findViewById(R.id.fishTicketLv);
		data = getData();
		adapter = new FishTicketAdapter(MyFishTicketsActivity.this, data, R.layout.my_fish_tickets_item, new String[]{}, new int[]{});
		fishTicketLv.setAdapter(adapter);
	}
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		back = (ImageView)findViewById(R.id.search_back);
		search_global_navigate_up_title = (TextView)findViewById(R.id.search_global_navigate_up_title);
		
		//赋值及监听
		back.setOnClickListener(this);
		search_global_navigate_up_title.setText("我的鲜券");
	}
	
	public List<HashMap<String,Object>> getData(){
		List<HashMap<String,Object>> list = new LinkedList<HashMap<String,Object>>();
		HashMap<String, Object> map;
		for(int i=0;i<6;i++){
			map = new HashMap<String, Object>();
			if(i<5){
				map.put("available", true);
			}else{
				map.put("available", false);
			}
			map.put("ticketValue", new Random().nextInt(10)+1);
			map.put("availableTime","2015.05.07");
			map.put("unAvailableTime", "2015.05.11");
			list.add(map);
		}
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			finish();
		}
	}
	
	
}
