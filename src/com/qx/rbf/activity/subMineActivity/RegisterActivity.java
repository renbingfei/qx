package com.qx.rbf.activity.subMineActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.MainActivity;
import com.qx.rbf.activity.MineActivity;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.ShopsActivity;
import com.qx.rbf.utils.Common;
import com.qx.rbf.utils.Instance;
import com.qx.rbf.utils.MD5Util;
import com.qx.rbf.utils.Validate;
import com.tencent.bugly.crashreport.CrashReport;

public class RegisterActivity extends ActionBarActivity implements OnClickListener {
	String account ;
	private boolean findPasswordOrRegister;  //���һ����뻹��ע��
	private View contentView1,contentView2;
	private boolean isNextView = false;
	private int changeViewCount = 0;
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//
	private ImageView three_big_type_goods1;
	private ImageView three_big_type_shop1;
	private ImageView three_big_type_mine1;
	//��¼�˺����
	private EditText accountEt;
	private TextView nextStep; //��һ����ť
	private ImageView search_back;//���ذ�ť
	//�ڶ���view��
	private EditText identify_code; //��֤��
	private TextView get_identify_code;//�򿪾ͷ���
	private EditText passwordEt; //����
	private EditText ensure_passwordEt;//ȷ������
	private TextView finish_and_login; //���ע�Ტ��½
	
	private ProgressDialog progressDialog;//������
	private TimeCount timeCount;                //��ʱ������֤���ط�
	private Long millisInFuture;                //��ʱ����ʱ��
	private Long countDownInterval;             //��ʱ�����ʱ��
	private Handler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		contentView1 = inflater.inflate(R.layout.register_main, null);
		contentView2 = inflater.inflate(R.layout.register_main_next_step, null);
		setContentView1();
		initActionBar();
		init();
		MyApplication.getInstance().addActivity(this);
	}
	
	private void setContentView1(){
		setContentView(contentView1);
		if(changeViewCount==0){
			three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
			three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
			three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
			//������ƷΪ���״̬
			three_big_type_mine.setPressed(true);
			//���ü���
			three_big_type_goods.setOnClickListener(this);
			three_big_type_shop.setOnClickListener(this);
			three_big_type_mine.setOnClickListener(this);
		}
	}
	private void setContentView2(){
		setContentView(contentView2);
		//���ÿؼ�
		if(changeViewCount==0){
			identify_code = (EditText) findViewById(R.id.identify_code);
			get_identify_code = (TextView) findViewById(R.id.get_identify_code);
			passwordEt = (EditText) findViewById(R.id.password);
			ensure_passwordEt = (EditText) findViewById(R.id.ensure_password);
			finish_and_login = (TextView) findViewById(R.id.finish_and_login);
			//ע�������
			get_identify_code.setOnClickListener(this);
			finish_and_login.setOnClickListener(this);

			three_big_type_goods1 = (ImageView) findViewById(R.id.three_big_type_goods);
			three_big_type_shop1 = (ImageView) findViewById(R.id.three_big_type_shop);
			three_big_type_mine1 = (ImageView) findViewById(R.id.three_big_type_mine);
			//������ƷΪ���״̬
			three_big_type_mine1.setPressed(true);
			//���ü���
			three_big_type_goods1.setOnClickListener(this);
			three_big_type_shop1.setOnClickListener(this);
			three_big_type_mine1.setOnClickListener(this);
		}
		isNextView = true;
		changeViewCount++;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//������ƷΪ���״̬
		three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.minePressed);
		
		
		super.onResume();
	}

	/**
	 * initActionBar ����:
	 * ��ʼ��������
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back);
		TextView title = (TextView)findViewById(R.id.search_global_navigate_up_title);
		Intent intent = getIntent();
		String str = intent.getStringExtra("findPasswordOrRegister");
		if("register".equals(str)){
			findPasswordOrRegister = true;	
		}else if("findPassword".equals(str)){
			findPasswordOrRegister = false;
		}
		//Toast.makeText(this, str, 4000).show();
		
		if(findPasswordOrRegister){
			title.setText("ע��");
		}else{
			title.setText("�һ�����");
		}
		search_back = (ImageView)findViewById(R.id.search_back);
		search_back.setOnClickListener(this);
	}
	
	public void init(){
		//
		accountEt = (EditText)findViewById(R.id.account);
		nextStep = (TextView)findViewById(R.id.nextStep);
		//���Ӽ�����
		nextStep.setOnClickListener(this);
		
		////��ʼ������
		millisInFuture = (long) 60*1000;
		countDownInterval = (long) 1*1000;
		timeCount = new TimeCount(millisInFuture, countDownInterval);
		//
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == three_big_type_goods || v == three_big_type_goods1){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			//
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop || v == three_big_type_shop1){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//��������
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//��������
			
		}else if(v == nextStep){
			//��¼
			account = accountEt.getText().toString().trim();
			if("".equals(account)){
				Toast.makeText(this, "�ֻ��Ų���Ϊ��", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "�ֻ��Ų��Ϸ�", 3000).show();
			}else{
				//������һ�����滻view
				setContentView2();
				//������֤��
				timeCount.start();
				get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//������ɫ
				getIdentifyCode(account);
				
			}
		}else if(v == search_back){
			if(isNextView){
				setContentView(contentView1);
				isNextView = false;
				changeViewCount++;
			}else{
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, MineActivity.class);
				startActivity(intent);
				finish();
			}
		}else if(v == get_identify_code){
			//��ȡ��֤��
			timeCount.start();
			get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//������ɫ
			getIdentifyCode(account);
			
		}else if(v == finish_and_login){
			//��ɲ���½
			//account = accountEt.getText().toString().trim();
			String identifyCode = identify_code.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			String confirmPassword = ensure_passwordEt.getText().toString().trim();
			if("".equals(password) || "".equals(confirmPassword)||"".equals(identifyCode)){
				Toast.makeText(this, "����������", 3000).show();
			}else if(!password.equals(confirmPassword)){
				Toast.makeText(this, "�����������벻һ��", 3000).show();
			}else{
				//ע�Ტ��½���룬��¼�ɹ��󣬼ǵ���ת����¼����
				if(findPasswordOrRegister){
					//ע��
					finishAndLogin(account,identifyCode,password,true);
				}else{
					//�һ�����
					finishAndLogin(account,identifyCode,password,false);
				}
			}
		}
	}
	//���ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//���¼����Ϸ��ذ�ť
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isNextView){
				setContentView(contentView1);
				isNextView = false;
				changeViewCount++;
			}else{
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, MineActivity.class);
				startActivity(intent);
				finish();
			}
			return false;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//System.exit(0);
		//�����������ַ�ʽ
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
	
	//��ʱ����������֤��������
	class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			get_identify_code.setClickable(false);
			get_identify_code.setText("���·���("+(millisUntilFinished/1000) +")" );
					
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			get_identify_code.setText("��ȡ��֤��");
			get_identify_code.setClickable(true);
			get_identify_code.setBackgroundResource(R.drawable.ic_register);
		}
				
	}
	
	class MyHandler extends Handler{
		private boolean isFirst = true;
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case 0:
					Toast.makeText(RegisterActivity.this, "��֤��:666666", 2000).show();
					break;
				case 1:
					Bundle bundle = msg.getData();
					Toast.makeText(RegisterActivity.this, "ʧ��ԭ��: "+bundle.getString("reason"), 2000).show();
					break;
				default:
					break;
			}
			
		}
		
	}
	
	private void getIdentifyCode(String account){
		AjaxParams params = new AjaxParams();
		JSONObject data = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject body = new JSONObject();
		body.put("account",account);
		data.put("body",body);
		String finalData = Instance.EncryptGzipJsonData(data);
		params.put("data", finalData);
		System.out.println("finalData: "+finalData);
		//http.addHeader("Content-Encoding", "gzip");
		//http.addHeader("Accept-Encoding", "gzip");
		Common.fhttp.post(Common.baseUrl+"common/getVerifyCode", params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
//				System.out.println("****");
//				Toast.makeText(RegisterActivity.this, "****"+errorNo+":"+strMsg, 3000).show();
				super.onFailure(t, errorNo, strMsg);
			}
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}
	
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				System.out.println(t);
				Message msg = handler.obtainMessage();
				//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
				//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
				JSONObject data = Instance.DencryptUnGzipJsonData(t);
				//System.out.println(data.toJSONString());
				JSONObject header = data.getJSONObject("header");
				if("success".equals(header.getString("result"))){
					msg.what = 0;
				}else{
					msg.what = 1;
					Bundle bundle = new Bundle();
					bundle.putString("reason", header.getString("reason"));
					msg.setData(bundle);
				}
				//Toast.makeText(RegisterActivity.this, data.toJSONString()+"**", 50000).show();
				handler.sendMessage(msg);
			}
		});
	}
	
	private void finishAndLogin(String account,String identifyCode,String password,boolean isRegister){

		AjaxParams params = new AjaxParams();
		JSONObject data = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject body = new JSONObject();
		body.put("account",account);
		body.put("userType", "user");
		
		body.put("password", MD5Util.MD5(password));
		body.put("identify_code", identifyCode);
		String url = "";
		if(isRegister){
			url = Common.baseUrl + "user/register";
		}else{
			url = Common.baseUrl + "user/login";
		}
		data.put("body",body);
		String finalData = Instance.EncryptGzipJsonData(data);
		params.put("data", finalData);
		System.out.println("finalData: "+finalData);
		Common.fhttp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
//				System.out.println("****");
//				Toast.makeText(RegisterActivity.this, "****"+errorNo+":"+strMsg, 3000).show();
				super.onFailure(t, errorNo, strMsg);
			}
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}
	
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				String decodeT = "";
				try {
					decodeT = URLDecoder.decode(t, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(decodeT);
				
				//Toast.makeText(RegisterActivity.this, t, 3000).show();
				Message msg = handler.obtainMessage();
				//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
				//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
				JSONObject data = Instance.DencryptUnGzipJsonData(decodeT);
				System.out.println(data.toJSONString());
				JSONObject header = data.getJSONObject("header");
				if("success".equals(header.getString("result"))){
					msg.what = 0;
				}else{
					msg.what = 1;
					Bundle bundle = new Bundle();
					bundle.putString("reason", header.getString("reason"));
					msg.setData(bundle);
				}
				//Toast.makeText(RegisterActivity.this, data.toJSONString()+"**", 50000).show();
				handler.sendMessage(msg);
			}
		});
	}
}