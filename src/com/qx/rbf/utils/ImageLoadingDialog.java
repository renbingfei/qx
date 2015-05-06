package com.qx.rbf.utils;

import com.qx.rbf.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * @package£∫
 * @author£∫Rbf
 * @description£∫The class is for...
 */
public class ImageLoadingDialog extends Dialog {

	public ImageLoadingDialog(Context context) {
		super(context, R.style.ImageloadingDialogStyle);
		//setOwnerActivity((Activity) context);// …Ë÷√dialog»´∆¡œ‘ æ
	}

	private ImageLoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_imageloading);
	}

}
