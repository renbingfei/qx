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

	private View noLoginView,loginView; //��¼ǰ�͵�½���view
	private boolean isFirstNo = false; 
	private boolean isFirstlogin = false; 
	private ImageView three_big_type_goods;
	private ImageView three_big_type_shop;
	private ImageView three_big_type_mine;
	//��¼�˺����
	private EditText accountEt;
	private EditText passwordEt;
	private TextView login_btn; //��¼��ť
	private TextView forget_password_btn;//��������
	private TextView register; //ע�ᰴť
	private ProgressDialog progressDialog;
	//�ڶ���view��һЩ����
	private TextView login_account_text; //��ʾ �û��ֻ���
	private TextView myOrders; //�ҵĶ���
	private TextView good_basket_number; //���ﳵ��Ʒ����
	private TextView myGoodsLoved; //���ղص���Ʒ
	private TextView myShopsLoved;  //�ҹ�ע���̵�
	private TextView myFishTickets; //�ҵ���ȯ
	private TextView fish_ticket_number; //��ȯ����
	private TextView mySystemNotice; //ϵͳ֪ͨ
	private TextView system_notice_number; //֪ͨ
	private TextView logout;  //�˳���¼
	private ImageView setting; //����
	private ImageView call; //����绰
	private TextView login_title; //��¼����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		//��ʼ��view
		noLoginView = inflater.inflate(R.layout.mine_main,null);
		loginView = inflater.inflate(R.layout.mine_main_login, null);
		if(!Common.isLogin){   //û�е�¼
			setContentView1();
		}else{   //�Ѿ���¼����������������ļ�
			//
			Toast.makeText(this, "�Ѿ���¼", 3000).show();
			setContentView2();
		}
		MyApplication.getInstance().addActivity(this);
		
	}
	
	//��ʼ��ȫ�ֱ���
	public void initGlobal(){

		if(!isFirstlogin && !isFirstNo){
			initActionBar1();
			initTop();
		}
	}
	//δ��¼view
	public void setContentView1(){
		setContentView(noLoginView);
		//��ʼ������Ͷ���
		initGlobal();
		initBottom();
		if(!isFirstNo){
			
			init1();
			isFirstNo = true;
		}
		login_title.setText("��¼");
		setting.setVisibility(View.INVISIBLE);
		call.setVisibility(View.INVISIBLE);
	}
	//��¼view
	public void setContentView2(){
		//
		setContentView(loginView);
		initGlobal();
		initBottom();
		//��ʼ�����
		if(!isFirstlogin){
			//
			
			init2();
			
			isFirstlogin = true;
		}
		login_title.setText("�ҵ�");
		setting.setVisibility(View.VISIBLE);
		call.setVisibility(View.VISIBLE);
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
	public void initActionBar1(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.mine_title);
		//��ʼ��
		
	}
	
	public void init1(){
		//
		accountEt = (EditText)findViewById(R.id.account);
		passwordEt = (EditText)findViewById(R.id.password);
		login_btn = (TextView)findViewById(R.id.login_btn);
		forget_password_btn = (TextView)findViewById(R.id.forget_password_btn);
		register = (TextView)findViewById(R.id.register);
		//��Ӽ�����
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
		myFishTickets = (TextView) findViewById(R.id.myFishTickets); //�ҵ���ȯ
		fish_ticket_number = (TextView) findViewById(R.id.fish_ticket_number); //��ȯ����
		mySystemNotice = (TextView) findViewById(R.id.mySystemNotice); //ϵͳ֪ͨ
		system_notice_number = (TextView) findViewById(R.id.system_notice_number); //֪ͨ
		logout = (TextView) findViewById(R.id.logout);  //�˳���¼
		
		//����
		login_account_text.setText("13718485367");
		good_basket_number.setText("3");
		fish_ticket_number.setText("4");
		system_notice_number.setText("2");
		
		//���ü�����
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
		//������ƷΪ���״̬
		three_big_type_mine.setPressed(true);
		//���ü���
		three_big_type_goods.setOnClickListener(this);
		three_big_type_shop.setOnClickListener(this);
		three_big_type_mine.setOnClickListener(this);
	}
	public void initTop(){
		login_title = (TextView)findViewById(R.id.login_title);
		setting = (ImageView) findViewById(R.id.setting);
		call = (ImageView) findViewById(R.id.call);
		
		//���ü���
		setting.setOnClickListener(this);
		call.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == call){
			//����ͷ��绰
			new AlertDialog.Builder(MineActivity.this)
							.setTitle("�ͷ�")
							.setIcon(R.drawable.ic_prompt)
							.setMessage("�����⣿�ҿͷ���")
							.setPositiveButton("����", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
									startActivity(intent);
								}
							})
							.setNegativeButton("ȡ��", null)
							.show();
							
		}else if(v == setting){
		//����
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
			
			//��������
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, ShopsActivity.class);
			startActivity(intent);
			finish();
			
		}else if(v == three_big_type_mine){
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopUnpressed);
			three_big_type_mine.setImageBitmap(MyApplication.minePressed);
			//��������
			
		}else if(v == login_btn){
			//��¼
			String account = accountEt.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			if("".equals(account) || "".equals(password)){
				Toast.makeText(this, "�˺Ż����벻��Ϊ��", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "�ֻ��Ų��Ϸ�", 3000).show();
			}else{
				//�ύ��¼����
				progressDialog = ProgressDialog.show(this, "��ʾ","���ڵ�½����ȴ�...");
				//progressDialog.setCanceledOnTouchOutside(true);
				
			}
			//���õ�¼״̬
			Common.isLogin = true;
			setContentView2();
			
		}else if(v == forget_password_btn){
			//��������
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "findPassword");
			startActivity(intent);
			finish();
		}else if(v == register){
			//ע��
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			intent.putExtra("findPasswordOrRegister", "register");
			startActivity(intent);
			finish();
		}else if(v == myOrders){
			//�����ҵĹ��ﳵ
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyOrdersActivity.class);
			startActivity(intent);
		}else if(v == myGoodsLoved){
			//�����ҹ�ע����Ʒ
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyLovedGoods.class);
			startActivity(intent);
		}else if(v == myShopsLoved){
			//�����ҹ�ע���̵�
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyLovedShops.class);
			startActivity(intent);
		}else if(v == logout){
			//�˳���¼
			Common.isLogin = false;
			setContentView1();
		}else if(v == myFishTickets){
			Intent intent = new Intent();
			intent.setClass(MineActivity.this, MyFishTicketsActivity.class);
			startActivity(intent);
		}
	}
	//���ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//���¼����Ϸ��ذ�ť
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
		//�����������ַ�ʽ
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
}
