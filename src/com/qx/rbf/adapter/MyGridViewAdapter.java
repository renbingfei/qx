package com.qx.rbf.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qx.rbf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyGridViewAdapter extends SimpleAdapter{

	private Context context;
	public MyGridViewAdapter(Context context,
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goods_types, null);
			view = convertView;
		}else{
			view = convertView;
		}
		//设置item高度
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                300);
        convertView.setLayoutParams(param);
        
		ImageView imageView = (ImageView) view.findViewById(R.id.goodsImageView);
		TextView textView = (TextView) view.findViewById(R.id.goodsTextView);
	
		imageView.setTag(map.get("goods"));
		imageView.setBackgroundResource((Integer) map.get("goods"));
		textView.setText(map.get("goodsText").toString());
		
		//设置监听器
		
		return view;
	}

	
}
