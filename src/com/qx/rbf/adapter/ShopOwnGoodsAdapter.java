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
import android.widget.TextView;

public class ShopOwnGoodsAdapter extends BaseAdapter{
	private LinkedList<HashMap<String,Object>> datalist;
	private Context context;
	private LayoutInflater mInflater;
	public ShopOwnGoodsAdapter(LinkedList<HashMap<String,Object>> datalist,Context context){
		this.datalist = datalist;
		this.context = context;
		
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.shop_own_goods_list_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.goods_item_imageview);
			holder.tvName = (TextView) convertView.findViewById(R.id.goods_item_name);
			holder.tvOriginPrice = (TextView) convertView.findViewById(R.id.goods_item_origin_price);
			holder.tvQxPrice = (TextView) convertView.findViewById(R.id.goods_item_qx_price);
			holder.good_detail_left_number = (TextView)convertView.findViewById(R.id.good_detail_left_number);
			holder.qx_time = (TextView)convertView.findViewById(R.id.qx_time);
			holder.goodId = "";
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(datalist.size()>0){
			
			final HashMap<String, Object> map = datalist.get(position);
			
			Common.fb.display(holder.imageView, map.get("image").toString());
			holder.tvName.setText(map.get("name").toString());
			holder.tvOriginPrice.setText("原售价  "+map.get("origin_price").toString());
			holder.tvQxPrice.setText(" "+map.get("qx_price").toString()+" ");
			holder.good_detail_left_number.setText("库存"+map.get("good_detail_left_number").toString());
			holder.qx_time.setText(map.get("qx_time").toString());
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
		TextView good_detail_left_number;
		TextView qx_time;
		String goodId;
	}
}
