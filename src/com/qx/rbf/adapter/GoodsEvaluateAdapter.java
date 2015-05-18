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
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class GoodsEvaluateAdapter extends SimpleAdapter{

	private Context context;
	
	public GoodsEvaluateAdapter(Context context,
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
			convertView = LayoutInflater.from(context).inflate(R.layout.goods_evaluate_item, null);
			view = convertView;
		}else{
			view = convertView;
		}
        
		TextView nickNameTv = (TextView) view.findViewById(R.id.nickNameTv);
		RatingBar goodsItemRate = (RatingBar)view.findViewById(R.id.goodsItemRate);
		TextView evaluateTimeTv = (TextView) view.findViewById(R.id.evaluateTimeTv);
		TextView evaluateInfoTv = (TextView) view.findViewById(R.id.evaluateInfoTv);
		nickNameTv.setText(map.get("nickName").toString());
		goodsItemRate.setRating((Float) map.get("goodsItemRate"));
		evaluateTimeTv.setText(map.get("evaluateTime").toString());
		evaluateInfoTv.setText(map.get("evaluateInfo").toString());
		//…Ë÷√º‡Ã˝∆˜
		
		return view;
	}


}
