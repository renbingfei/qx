package com.qx.rbf.activity;


import com.qx.rbf.MyApplication;
import com.qx.rbf.utils.*;
import com.qx.rbf.activity.subMineActivity.MyFishTicketsActivity;
import com.qx.rbf.activity.subMineActivity.MyLovedGoods;
import com.qx.rbf.activity.subMineActivity.MyLovedShops;
import com.qx.rbf.activity.subMineActivity.MyOrdersActivity;
import com.qx.rbf.activity.subMineActivity.RegisterActivity;
import com.qx.rbf.activity.subMineActivity.SettingActivity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity extends ActionBarActivity implements OnClickListener {

	private View noLoginView,loginView; //登录前和登陆后的view
	private boolean isFirstNo = false; 
	private boolean isFirstlogin = false; 
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//登录账号相关
	private EditText accountEt;
	private EditText passwordEt;
	private TextView login_btn; //登录按钮
	private TextView forget_password_btn;//忘记密码
	private TextView register; //注册按钮
	private ProgressDialog progressDialog;
	//第二个view的一些变量
	private TextView login_account_text; //显示 用户手机号
	private TextView myOrders; //我的订单
	private TextView good_basket_number; //购物车物品数量
	private TextView myGoodsLoved; //我收藏的商品
	private TextView myShopsLoved;  //我关注的商店
	private TextView myFishTickets; //我的鲜券
	private TextView fish_ticket_number; //鲜券数量
	private TextView mySystemNotice; //系统通知
	private TextView system_notice_number; //通知
	private TextView logout;  //退出登录
	private ImageView setting; //设置
	private ImageView call; //拨打电话
	private TextView login_title; //登录标题
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		//初始化view
		noLoginView = inflater.inflate(R.layout.mine_main,null);
		loginView = inflater.inflate(R.layout.mine_main_login, null);
		if(!Common.isLogin){   //没有登录
			setContentView1();
		}else{   //已经登录，需调用其他布局文件
			//
			Toast.makeText(this, "已经登录", 3000).show();
			setContentView2();
		}
		MyApplication.getInstance().addActivity(this);
		
	}
	
	//初始化全局变量
	public void initGlobal(){

		if(!isFirstlogin && !isFirstNo){
			initActionBar1();
			initTop();
		}
	}
	//未登录view
	public void setContentView1(){
		setContentView(noLoginView);
		//初始化组件和对象
		initGlobal();
		initBottom();
		if(!isFirstNo){
			
			init1();
			isFirstNo = true;
		}
		login_title.setText("登录");
		setting.setVisibility(View.INVISIBLE);
		call.setVisibility(View.INVISIBLE);
	}
	//登录view
	public void setContentView2(){
		//
		setContentView(loginView);
		initGlobal();
		initBottom();
		//初始化组件
		if(!isFirstlogin){
			//
			
			init2();
			
			isFirstlogin = true;
		}
		login_title.setText("我的");
		setting.setVisibility(View.VISIBLE);
		call.setVisibility(View.VISIBLE);
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
	public void initActionBar1(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.mine_title);
		//初始化
		
	}
	
	public void init1(){
		//
		accountEt = (EditText)findViewById(R.id.account);
		passwordEt = (EditText)findViewById(R.id.password);
		login_btn = (TextView)findViewById(R.id.login_btn);
		forget_password_btn = (TextView)findViewById(R.id.forget_password_btn);
		register = (TextView)findViewById(R.id.register);
		//添加监听器
		login_btn.setOnClickListener(this);
		forget_password_btn.setOnClickListener(this);
		register.setOnClickListener(this);

	}
	
	public void init2(){
		login_account_text = (TextView) findViewById(R.id.login_account_text);
		myOrders = (TextView) findViewById(R.id.myOrders);
		good_basket_number = (TextView) findViewById(R.id.good_basket_number);
		myGoodsLoved = (TextView) findViewById(R.id.myGoodsLoved);
		myShopsLoved = (TextView) findViewById(R.id.myShopsLoved);
		myFishTickets = (TextView) findViewById(R.id.myFishTickets); //我的鲜券
		fish_ticket_number = (TextView) findViewById(R.id.fish_ticket_number); //鲜券数量
		mySystemNotice = (TextView) findViewById(R.id.mySystemNotice); //系统通知
		system_notice_number = (TextView) findViewById(R.id.system_notice_number); //通知
		logout = (TextView) findViewById(R.id.logout);  //退出登录
		
		//设置
		login_account_text.setText("13718485367");
		good_basket_number.setText("3");
		fish_ticket_number.setText("4");
		system_notice_number.setText("2");
		
		//设置监听器
		myOrders.setOnClickListener(this);
		myGoodsLoved.setOnClickListener(this);
		myShopsLoved.setOnClickListener(this);
		myFishTickets.setOnClickListener(this);
		logout.setOnClickListener(this);
	}
	public void initBottom(){
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
	public void initTop(){
		login_title = (TextView)findViewById(R.id.login_title);
		setting = (ImageView) findViewById(R.id.setting);
		call = (ImageView) findViewById(R.id.call);
		
		//设置监听
		setting.setOnClickListener(this);
		call.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == call){
			//拨打客服电话
			new AlertDialog.Builder(MineActivity.this)
							.setTitle("客服")
							.setIcon(R.drawable.ic_prompt)
							.setMessage("有问题？找客服！")
							.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
									startActivity(intent);
								}
							})
							.setNegativeButton("取消", null)
							.show();
							
		}else if(v == setting){
		//设置
			Intent intent = new Intent(MineActivity.this, SettingActivity.class);
			startActivity(intent);
			
		}else if(v == three_big_type_goods){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			//
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_shop){
//			three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//			three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_pressed);
//			three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
			
			//其他代码
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//其他代码
			
		}else if(v == login_btn){
			//登录
			String account = accountEt.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			if("".equals(account) || "".equals(password)){
				Toast.makeText(this, "账号或密码不能为空", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "手机号不合法", 3000).show();
			}else{
				//提交登录代码
				progressDialog = ProgressDialog.show(this, "提示","正在登陆，请等待...");
				//progressDialog.setCanceledOnTouchOutside(true);
				
			}
			//设置登录状态
			Common.isLogin = true;
			setContentView2();
			
		}else if(v == forget_password_btn){
			//忘记密码
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "findPassword");
			startActivity(intent);
			finish();
		}else if(v == register){
			//注册
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "register");
			startActivity(intent);
			finish();
		}else if(v == myOrders){
			//进入我的购物车
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyOrdersActivity.class);
			startActivity(intent);
		}else if(v == myGoodsLoved){
			//进入我关注的商品
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyLovedGoods.class);
			startActivity(intent);
		}else if(v == myShopsLoved){
			//进入我关注的商店
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyLovedShops.class);
			startActivity(intent);
		}else if(v == logout){
			//退出登录
			Common.isLogin = false;
			setContentView1();
		}else if(v == myFishTickets){
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyFishTicketsActivity.class);
			startActivity(intent);
		}
	}
	//返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_prompt)
				.setTitle(R.string.prompt)
				.setMessage(R.string.quit_desc)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						MyApplication.getInstance().exit();
					}
				}).show();
			
			return true;
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
}
