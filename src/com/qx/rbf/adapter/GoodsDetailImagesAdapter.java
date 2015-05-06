package com.qx.rbf.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qx.rbf.utils.Common;
import com.qx.rbf.activity.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class GoodsDetailImagesAdapter extends SimpleAdapter{
	private int selectItem = -1;
	private Context context;
	private LayoutParams mParams;
	@SuppressLint("NewApi")
	public GoodsDetailImagesAdapter(Context context,
			List<? extends Map<String, ?>> data) {
		super(context, data, 0, null, null);
		// TODO Auto-generated constructor stub
		this.context = context;
		mParams = new LayoutParams(250,android.view.ViewGroup.LayoutParams.FILL_PARENT);
	}

	 public void setSelectItem(int selectItem) {
		  if (this.selectItem != selectItem) {               
			  this.selectItem = selectItem;
			  //notifyDataSetChanged();        
		  }
	}
	public int getSelectItem(){
		return this.selectItem;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map = (HashMap<String, Object>) getItem(position);
		String uri = (String) map.get("uri");
		String infoText = (String)map.get("info");
		
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(mParams);
//		if(selectItem==position){
//		     Animation animation = AnimationUtils.loadAnimation(context, R.anim.hyperspace_out);
//		     imageView.setLayoutParams(new Gallery.LayoutParams(600,600));
//		     imageView.startAnimation(animation);
//		     Common.fb.display(imageView, uri);
//		  }else{
		imageView.setTag(R.id.first_tag,uri);
		imageView.setTag(R.id.second_tag,infoText);
	    Common.fb.display(imageView, uri);
		  //}
		
		
		return imageView;  
	}
	

}
