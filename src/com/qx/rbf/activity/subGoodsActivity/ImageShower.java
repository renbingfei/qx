package com.qx.rbf.activity.subGoodsActivity;

import com.qx.rbf.activity.R;
import com.qx.rbf.utils.Common;
import com.qx.rbf.utils.ImageLoadingDialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @package：
 * @author：Rbf
 * @description：The class is for...
 */
public class ImageShower extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		ImageView iv = (ImageView)findViewById(R.id.imageShower);
		final TextView tv = (TextView)findViewById(R.id.imageShowerText);
		
		Common.fb.display(iv, GoodsDetailsActivity.bitmapUrl);
		
		
		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 两秒后关闭后dialog
		new Handler().postDelayed(new Runnable() {
		@Override
			public void run() {
					dialog.dismiss();
					tv.setText(GoodsDetailsActivity.shopInfo);
				}
			}, 1000 * 2);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}

}
