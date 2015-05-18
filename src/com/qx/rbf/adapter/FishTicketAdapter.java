package com.qx.rbf.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qx.rbf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FishTicketAdapter extends SimpleAdapter{

	private Context context;
	public FishTicketAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map = (HashMap<String,Object>)getItem(position);
		View view = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.my_fish_tickets_item, null);
			view = convertView;
		}else{
			view = convertView;
		}
		
		//检查是否过期
		if((Boolean) map.get("available")){
			view.setBackgroundResource(R.drawable.able_ticket);
		}else{
			view.setBackgroundResource(R.drawable.disable_ticket);
		}
		TextView ticketValueTv = (TextView)view.findViewById(R.id.ticketValueTv);
		TextView availableTimeTv = (TextView)view.findViewById(R.id.availableTimeTv);
		TextView unAvailableTimeTv = (TextView)view.findViewById(R.id.unAvailableTimeTv);
		//赋值
		ticketValueTv.setText(map.get("ticketValue").toString());
		availableTimeTv.setText(map.get("availableTime").toString());
		unAvailableTimeTv.setText(map.get("unAvailableTime").toString());
		return view;
	}
	
	
}
