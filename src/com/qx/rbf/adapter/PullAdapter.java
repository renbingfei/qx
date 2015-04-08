package com.qx.rbf.adapter;

import java.util.LinkedList;

import com.qx.rbf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PullAdapter extends BaseAdapter {

	private LinkedList<String> linkedList;
	private LayoutInflater mInflater;
	
	public PullAdapter(LinkedList<String> linkedList, Context context) {
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
			convertView = mInflater.inflate(R.layout.layout_main_listitem, null);
			holder.textView = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(linkedList.size()>0){
			final String dataStr = linkedList.get(position);
			holder.textView.setText(dataStr);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView textView;        //������ʾ����
	}
}
