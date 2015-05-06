package com.qx.rbf.listener;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.qx.rbf.utils.Common;

public class MyLocationListener implements BDLocationListener {
	
	private Context context; 
	
	public MyLocationListener(Context context){
		this.context = context;
	}
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
	            return ;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		} 
		;
		Toast toast = Toast.makeText(context, sb, 8000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		System.out.println(sb);
		Common.lan = location.getLatitude();
		Common.lng = location.getLongitude();
		Common.addr = location.getAddrStr();
		Common.locationClient.stop();
	}
}