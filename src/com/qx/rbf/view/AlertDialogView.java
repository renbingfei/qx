package com.qx.rbf.view;

import com.qx.rbf.activity.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlertDialogView {
	private static Context context;
	
	public AlertDialogView(Context context){
		AlertDialogView.context = context;
	}
	public static View getAlertDialogView(){
		LinearLayout layout = new LinearLayout(context);  
		TextView tv = new TextView(context);  
		tv.setText(R.string.prompt);  
		LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
		layout.addView(tv, pm);  
		layout.setGravity(Gravity.CENTER); 
		return layout;
	}
}
