package com.qx.rbf.activity.subMineActivity;

import java.io.UnsupportedEncodingException;

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
import com.qx.rbf.utils.Validate;
import com.tencent.bugly.crashreport.CrashReport;

public class SettingRegisterActivity extends ActionBarActivity implements OnClickListener {
	String account ;
	private boolean findPasswordOrRegister;  //是找回密码还是注册
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
	//登录账号相关
	private EditText accountEt;
	private TextView nextStep; //下一步按钮
	private ImageView search_back;//返回按钮
	//第二个view中
	private EditText identify_code; //验证码
	private TextView get_identify_code;//打开就发送
	private EditText passwordEt; //密码
	private EditText ensure_passwordEt;//确认密码
	private TextView finish_and_login; //完成注册并登陆
	
	private ProgressDialog progressDialog;//进度条
	private TimeCount timeCount;                //计时器，验证码重发
	private Long millisInFuture;                //计时器总时长
	private Long countDownInterval;             //计时器间隔时间
	private Handler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		contentView1 = inflater.inflate(R.layout.register_main, null);
		contentView2 = inflater.inflate(R.layout.setting_register_main_next_step, null);
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
			//设置商品为点击状态
			three_big_type_mine.setPressed(true);
			//设置监听
			three_big_type_goods.setOnClickListener(this);
			three_big_type_shop.setOnClickListener(this);
			three_big_type_mine.setOnClickListener(this);
		}
	}
	private void setContentView2(){
		setContentView(contentView2);
		//设置控件
		if(changeViewCount==0){
			identify_code = (EditText) findViewById(R.id.identify_code);
			get_identify_code = (TextView) findViewById(R.id.get_identify_code);
			passwordEt = (EditText) findViewById(R.id.password);
			ensure_passwordEt = (EditText) findViewById(R.id.ensure_password);
			finish_and_login = (TextView) findViewById(R.id.finish_and_login);
			//注册监听器
			get_identify_code.setOnClickListener(this);
			finish_and_login.setOnClickListener(this);

			three_big_type_goods1 = (ImageView) findViewById(R.id.three_big_type_goods);
			three_big_type_shop1 = (ImageView) findViewById(R.id.three_big_type_shop);
			three_big_type_mine1 = (ImageView) findViewById(R.id.three_big_type_mine);
			//设置商品为点击状态
			three_big_type_mine1.setPressed(true);
			//设置监听
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
		//设置商品为点击状态
		three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
		three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
		three_big_type_mine.setImageBitmap(MyApplication.minePressed);
		
		
		super.onResume();
	}

	/**
	 * initActionBar 功能:
	 * 初始化导航条
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
			title.setText("注册");
		}else{
			title.setText("找回密码");
		}
		search_back = (ImageView)findViewById(R.id.search_back);
		search_back.setOnClickListener(this);
	}
	
	public void init(){
		//
		accountEt = (EditText)findViewById(R.id.account);
		nextStep = (TextView)findViewById(R.id.nextStep);
		//添加监听器
		nextStep.setOnClickListener(this);
		
		////初始化操作
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
			intent.setClass(SettingRegisterActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop || v == three_big_type_shop1){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			Intent intent = new Intent();
			intent.setClass(SettingRegisterActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//其他代码
			
		}else if(v == nextStep){
			//登录
			account = accountEt.getText().toString().trim();
			if("".equals(account)){
				Toast.makeText(this, "手机号不能为空", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "手机号不合法", 3000).show();
			}else{
				//进入下一步，替换view
				setContentView2();
				//发送验证码
				timeCount.start();
				get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//界面变灰色
				getIdentifyCode(account);
				
			}
		}else if(v == search_back){
			if(isNextView){
				setContentView(contentView1);
				isNextView = false;
				changeViewCount++;
			}else{
				Intent intent = new Intent();
				intent.setClass(SettingRegisterActivity.this, MineActivity.class);
				startActivity(intent);
				finish();
			}
		}else if(v == get_identify_code){
			//获取验证码
			timeCount.start();
			get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//界面变灰色
			getIdentifyCode(account);
			
		}else if(v == finish_and_login){
			//完成并登陆
			//account = accountEt.getText().toString().trim();
			String identifyCode = identify_code.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			String confirmPassword = ensure_passwordEt.getText().toString().trim();
			if("".equals(password) || "".equals(confirmPassword)||"".equals(identifyCode)){
				Toast.makeText(this, "请输入完整", 3000).show();
			}else if(!password.equals(confirmPassword)){
				Toast.makeText(this, "两次密码输入不一样", 3000).show();
			}else{
				//注册并登陆代码，登录成功后，记得跳转到登录界面
				if(findPasswordOrRegister){
					//注册
					
				}else{
					//找回密码
					
					//返回个人信息设置页面
					finish();
				}
			}
		}
	}
	//返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isNextView){
				setContentView(contentView1);
				isNextView = false;
				changeViewCount++;
			}else{
				Intent intent = new Intent();
				intent.setClass(SettingRegisterActivity.this, MineActivity.class);
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
		//或者下面这种方式
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
	
	//计时器，用于验证码间隔发送
	class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			get_identify_code.setClickable(false);
			get_identify_code.setText("重新发送("+(millisUntilFinished/1000) +")" );
					
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			get_identify_code.setText("获取验证码");
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
					
					break;
				case 1:
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
		Common.fhttp.post(Common.baseUrl+"user/getIdentifyCode", params, new AjaxCallBack<String>() {
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
				System.out.println("****12");
				System.out.println(t);
				//Toast.makeText(RegisterActivity.this, t, 3000).show();
				Message msg = handler.obtainMessage();
				msg.what = 0;
				//应该放在onSuccess中，此处为测试使用
				//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
				JSONObject data = Instance.DencryptUnGzipJsonData(t);
				System.out.println(data.toJSONString());
				//Toast.makeText(RegisterActivity.this, data.toJSONString()+"**", 50000).show();
				handler.sendMessage(msg);
			}
		});
	}
}
