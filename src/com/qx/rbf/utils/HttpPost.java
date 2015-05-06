package com.qx.rbf.utils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.content.Context;
import android.os.Handler;

public class HttpPost {
	
	private Context context;
	private Handler handler; //用于传输数据到调用者
	
	public HttpPost(Context context,Handler handler){
		this.context = context;
		this.handler = handler;
	}
	
	public void post(String url,AjaxParams params){
		FinalHttp http = new FinalHttp();

		http.post(Common.baseUrl+url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
			}
			
		});
	}
}
