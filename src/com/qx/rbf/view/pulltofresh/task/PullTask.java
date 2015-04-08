package com.qx.rbf.view.pulltofresh.task;

import java.util.LinkedList;


import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class PullTask extends AsyncTask<Void, Void, String>{

	private com.qx.rbf.view.pulltofresh.PullToRefreshListView pullToRefreshListView;  //实现下拉刷新与上拉加载的ListView
	private int pullState;               //记录判断，上拉与下拉动作
	private BaseAdapter baseAdapter;     //ListView适配器，用于提醒ListView数据已经更新
	private LinkedList<String> linkedList;
	
	public PullTask(com.qx.rbf.view.pulltofresh.PullToRefreshListView pullToRefreshListView, int pullState,
			BaseAdapter baseAdapter, LinkedList<String> linkedList) {
		this.pullToRefreshListView = pullToRefreshListView;
		this.pullState = pullState;
		this.baseAdapter = baseAdapter;
		this.linkedList = linkedList;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return "StringTest";
	}

	@Override
	protected void onPostExecute(String result) {
		if(pullState == 1) {//name="pullDownFromTop" value="0x1" 下拉
			linkedList.addFirst("顶部数据");
		}
		if(pullState == 2) {//name="pullUpFromBottom" value="0x2" 上拉
			linkedList.addLast("底部数据");
		}
		baseAdapter.notifyDataSetChanged();
		pullToRefreshListView.onRefreshComplete();
		super.onPostExecute(result);
	}
}
