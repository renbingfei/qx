package com.qx.rbf.view.pulltofresh.task;

import java.util.LinkedList;


import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class PullTask extends AsyncTask<Void, Void, String>{

	private com.qx.rbf.view.pulltofresh.PullToRefreshListView pullToRefreshListView;  //ʵ������ˢ�����������ص�ListView
	private int pullState;               //��¼�жϣ���������������
	private BaseAdapter baseAdapter;     //ListView����������������ListView�����Ѿ�����
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
		if(pullState == 1) {//name="pullDownFromTop" value="0x1" ����
			linkedList.addFirst("��������");
		}
		if(pullState == 2) {//name="pullUpFromBottom" value="0x2" ����
			linkedList.addLast("�ײ�����");
		}
		baseAdapter.notifyDataSetChanged();
		pullToRefreshListView.onRefreshComplete();
		super.onPostExecute(result);
	}
}
