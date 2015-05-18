package com.qx.rbf.adapter;

import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.activity.R;
import com.qx.rbf.widget.OnDeleteListioner;
import com.qx.rbf.widget.OnEvaluateListener;
import com.qx.rbf.widget.OnShopListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private LinkedList<HashMap<String,Object>> mlist = null;
	private OnDeleteListioner mOnDeleteListioner;
	private OnShopListener mOnShopListener;
	private OnEvaluateListener mOnEvaluateListener;
	
	private boolean delete = false;

	// private Button curDel_btn = null;
	// private UpdateDate mUpdateDate = null;

	public MyAdapter(Context mContext, LinkedList<HashMap<String, Object>> mlist) {
		this.mContext = mContext;
		this.mlist = mlist;

	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setOnDeleteListioner(OnDeleteListioner mOnDeleteListioner) {
		this.mOnDeleteListioner = mOnDeleteListioner;
	}
	
	public void setOnShopListener(OnShopListener mOnShopListener){
		this.mOnShopListener = mOnShopListener;
	}
	public void setOnEvaluateListener(OnEvaluateListener mEvaluateListener){
		this.mOnEvaluateListener = mEvaluateListener;
	}
	public int getCount() {

		return mlist.size();
	}

	public Object getItem(int pos) {
		return mlist.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(final int pos, View convertView, ViewGroup p) {
		
		HashMap<String, Object> map = mlist.get(pos);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.my_orders_item, null);
			viewHolder = new ViewHolder();
			viewHolder.goodNameTv = (TextView) convertView
					.findViewById(R.id.goodNameTv);
			viewHolder.originPriceTv = (TextView) convertView
					.findViewById(R.id.originPriceTv);
			viewHolder.qxPriceTv = (TextView) convertView
					.findViewById(R.id.qxPriceTv);
			viewHolder.buyNumberTv = (TextView) convertView
					.findViewById(R.id.buyNumberTv);
			viewHolder.orderCostsTv = (TextView) convertView
					.findViewById(R.id.orderCostsTv);
			viewHolder.fishTicketsAccountTv = (TextView) convertView
					.findViewById(R.id.fishTicketsAccountTv);
			viewHolder.orderRealCostsTv = (TextView) convertView
					.findViewById(R.id.orderRealCostsTv);
			viewHolder.orderTimeTv = (TextView) convertView
					.findViewById(R.id.orderTimeTv);
			
			//Òþ²ØµÄ¿Ø¼þ
			viewHolder.delete_action = (TextView) convertView
					.findViewById(R.id.delete_action);
			viewHolder.enter_action = (TextView) convertView
					.findViewById(R.id.enter_action);
			viewHolder.evaluate_action = (TextView) convertView
					.findViewById(R.id.evaluate_action);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mOnDeleteListioner != null)
					mOnDeleteListioner.onDelete(pos);

			}
		};
		
		final OnClickListener mShopOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnShopListener !=null){
					mOnShopListener.onEnterShop(pos);
				}
			}
		};
		
		final OnClickListener mEvaluateOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnEvaluateListener !=null){
					mOnEvaluateListener.onEvaluate(pos);
				}
			}
		};
		
		viewHolder.enter_action.setOnClickListener(mShopOnClickListener);
		viewHolder.delete_action.setOnClickListener(mOnClickListener);
		viewHolder.evaluate_action.setOnClickListener(mEvaluateOnClickListener);
		//¸³Öµ
		viewHolder.goodNameTv.setText(map.get("goodNameTv").toString());
		viewHolder.originPriceTv.setText(map.get("originPriceTv").toString());
		viewHolder.qxPriceTv.setText(map.get("qxPriceTv").toString());
		viewHolder.buyNumberTv.setText(map.get("buyNumberTv").toString());
		viewHolder.orderCostsTv.setText(map.get("orderCostsTv").toString());
		viewHolder.fishTicketsAccountTv.setText(map.get("fishTicketsAccountTv").toString());
		viewHolder.orderRealCostsTv.setText(map.get("orderRealCostsTv").toString());
		viewHolder.orderTimeTv.setText(map.get("orderTimeTv").toString());
		
		return convertView;
	}

	public static class ViewHolder {
		public TextView goodNameTv;
		public TextView originPriceTv;
		public TextView qxPriceTv;
		public TextView buyNumberTv;
		public TextView orderCostsTv;
		public TextView fishTicketsAccountTv;
		public TextView orderRealCostsTv;
		public TextView orderTimeTv;
		
		public TextView enter_action;
		public TextView delete_action;
		public TextView evaluate_action;
	}

}
