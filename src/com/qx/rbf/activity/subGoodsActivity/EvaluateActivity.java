package com.qx.rbf.activity.subGoodsActivity;

import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class EvaluateActivity extends ActionBarActivity implements OnClickListener, OnRatingBarChangeListener{

	private ImageView back;
	private TextView search_global_navigate_up_title;//����������
	private RatingBar evaluate_rate; //���
	private EditText evaluate_info; //������Ϣ
	private TextView sumit_evaluate; //�ύ
	private float rating; //���۵÷�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluate_rate);
		
		initActionBar();
		initWidget();
		//����application��
		MyApplication.getInstance().addActivity(this);
	}
	
	public void initWidget(){
		evaluate_rate = (RatingBar)findViewById(R.id.evaluate_rate);
		evaluate_info = (EditText)findViewById(R.id.evaluate_info);
		sumit_evaluate = (TextView)findViewById(R.id.sumit_evaluate);
		//���ü���
		evaluate_rate.setOnRatingBarChangeListener(this);
		sumit_evaluate.setOnClickListener(this);
	}
	
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		back = (ImageView)findViewById(R.id.search_back);
		search_global_navigate_up_title = (TextView)findViewById(R.id.search_global_navigate_up_title);
		
		//��ֵ������
		back.setOnClickListener(this);
		search_global_navigate_up_title.setText("������");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			//����
			finish();
			
		}else if(v == sumit_evaluate){
			//�ύ����
			
		}
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		// TODO Auto-generated method stub
		rating = rating;
		Toast.makeText(EvaluateActivity.this, ""+rating, Toast.LENGTH_SHORT).show();
	}
}
