package com.qx.rbf.adapter;

import java.util.HashMap;
import java.util.LinkedList;

import com.qx.rbf.activity.R;
import com.qx.rbf.utils.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PullAdapter extends BaseAdapter {

	private LinkedList<HashMap<String,Object>> linkedList;
	private LayoutInflater mInflater;
	
	public PullAdapter(LinkedList<HashMap<String,Object>> linkedList, Context context) {
		mInflater = LayoutInflater.from(context);
		this.linkedList = linkedList;
	}
	
	@Override
	public int getCount() {
		return linkedList.size();
	}

	@Override
	public Object getItem(int position) {
		return linkedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_main_list_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.goods_item_imageview);
			holder.tvName = (TextView) convertView.findViewById(R.id.goods_item_name);
			holder.tvOriginPrice = (TextView) convertView.findViewById(R.id.goods_item_origin_price);
			holder.tvQxPrice = (TextView) convertView.findViewById(R.id.goods_item_qx_price);
			holder.tvDistance = (TextView) convertView.findViewById(R.id.goods_item_distance);
			holder.tvArea = (TextView) convertView.findViewById(R.id.goods_item_area);
			holder.rbRate = (RatingBar) convertView.findViewById(R.id.goods_item_rate);
			holder.goodId = "";
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(linkedList.size()>0){
			final HashMap<String, Object> map = linkedList.get(position);
			
			Common.fb.display(holder.imageView, map.get("image").toString());
			holder.tvName.setText(map.get("name").toString());
			holder.tvOriginPrice.setText("原售价  "+map.get("origin_price").toString());
			holder.tvQxPrice.setText(" "+map.get("qx_price").toString()+" ");
			holder.tvDistance.setText(map.get("distance").toString());
			holder.tvArea.setText(map.get("area").toString());
			holder.rbRate.setRating((Float) map.get("rate"));
			holder.goodId = map.get("goodId").toString();  //给设置tag，以便获取地址
			holder.tvName.setTag(holder.goodId);
			
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;        //数据显示区域
		TextView tvName;
		TextView tvOriginPrice;
		TextView tvQxPrice;
		TextView tvDistance;
		TextView tvArea;
		RatingBar rbRate;
		String goodId;
	}
}
