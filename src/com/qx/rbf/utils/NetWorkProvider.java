package com.qx.rbf.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkProvider {

	/** 
	* �����Ƿ���� 
	* 
	* @param activity 
	* @return 
	*/ 
	public static boolean isNetworkAvailable(Context context) { 
		ConnectivityManager connectivity = (ConnectivityManager) context 
											.getSystemService(Context.CONNECTIVITY_SERVICE); 
		if (connectivity == null) { 
		
		} else { 
			NetworkInfo[] info = connectivity.getAllNetworkInfo(); 
			if (info != null) { 
				for (int i = 0; i < info.length; i++) { 
					if (info[i].getState() == NetworkInfo.State.CONNECTED) { 
						return true; 
					} 
				} 
			} 
		} 
		return false; 
	} 
}	