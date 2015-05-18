package com.qx.rbf.activity.subShopsActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.BNavigatorActivity;
import com.qx.rbf.activity.MainActivity;
import com.qx.rbf.activity.MineActivity;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.subGoodsActivity.GoodsDetailsActivity;
import com.qx.rbf.activity.subGoodsActivity.GoodsEvaluateActivity;
import com.qx.rbf.adapter.ShopOwnGoodsAdapter;
import com.qx.rbf.adapter.ShopsDetailImagesAdapter;
import com.qx.rbf.utils.Common;
import com.qx.rbf.utils.GoodsData;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShopsDetailsActivity extends ActionBarActivity implements OnClickListener{
	//view替换
		private View detailView;
		private ImageView three_big_type_goods;
		private ImageView three_big_type_shop;
		private ImageView three_big_type_mine;
		//上部导航
		private TextView search_global_navigate_up_title;
		private ImageView search_back;
		//下部导航
		private ImageView shop_love;  //收藏商品
		private boolean isShopLove = false;  //判断是否收藏

		//其余对象
		private TextView shop_detail_name; //商品名称
		private RatingBar shop_detail_rate; //评分
		
		private TextView shop_detail_order_number; //成交订单数量
		private ImageView shop_detail_images_listview; //商品图片
		
		private TextView shop_detail_location; //地址
		private TextView shop_detail_call_belong_shop; //电话
		private TextView shop_detail_call_phone_belong_shop; //手机
		private LinearLayout shop_detail_call_belong_shop_layout; //打电话布局
		private ListView shop_own_goods_listView;
		
		//private ShopsDetailImagesAdapter adapter; 
		private ShopOwnGoodsAdapter shopOwnGoodsAdapter;
		//用于显示大图的对象
		public static String bitmapUrl= null;

		private Handler handler = new MyHandler();
		private GoodsData goodsData = new GoodsData(this,handler);
		public static LinkedList<HashMap<String,Object>> mDatas;
		public ProgressDialog progressDialog;
		
		private PopupWindow popupWindow;  
	    private View parent; 
		private TextView goods_detail_phone;
		private TextView goods_detail_mobile_phone;
		private TextView goods_detail_phone_cancel;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			LayoutInflater inflater = LayoutInflater.from(this);
			detailView = inflater.inflate(R.layout.shops_details_main, null);
			setContentView1();
			initActionBar();
			init();
			initView();
			initBottom();
			
			//此处添加网络获取数据代码
			progressDialog = ProgressDialog.show(ShopsDetailsActivity.this, "提示","正在获取数据，请等待...");
			progressDialog.setCanceledOnTouchOutside(true);
			MyApplication.getInstance().addActivity(this);
		}
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			//设置商品为点击状态
			three_big_type_goods.setImageBitmap(MyApplication.goodsUnpressed);
			three_big_type_shop.setImageBitmap(MyApplication.shopPressed);
			three_big_type_mine.setImageBitmap(MyApplication.mineUnpressed);
			super.onResume();
		}
		public void initBottom(){
			three_big_type_goods = (ImageView) findViewById(R.id.three_big_type_goods);
			three_big_type_shop = (ImageView) findViewById(R.id.three_big_type_shop);
			three_big_type_mine = (ImageView) findViewById(R.id.three_big_type_mine);
			//设置商品为点击状态
			three_big_type_shop.setPressed(true);
			//设置监听
			three_big_type_goods.setOnClickListener(this);
			three_big_type_shop.setOnClickListener(this);
			three_big_type_mine.setOnClickListener(this);
		}
		
		private void setContentView1(){
			setContentView(detailView);
			//初始化
			shop_detail_name = (TextView) findViewById(R.id.shop_detail_name);
			shop_detail_rate = (RatingBar) findViewById(R.id.shop_detail_rate);
			shop_detail_order_number = (TextView) findViewById(R.id.shop_detail_order_number);
			
			shop_detail_images_listview = (ImageView) findViewById(R.id.shop_detail_images_listview);
			
			shop_detail_location = (TextView) findViewById(R.id.shop_detail_location);
			shop_detail_call_phone_belong_shop = (TextView) findViewById(R.id.shop_detail_call_phone_belong_shop);
			shop_detail_call_belong_shop = (TextView) findViewById(R.id.shop_detail_call_belong_shop);
			shop_detail_call_belong_shop_layout = (LinearLayout) findViewById(R.id.shop_detail_call_belong_shop_layout);
			//adapter = new ShopsDetailImagesAdapter(this, getData());
			//shop_detail_images_listview.setAdapter(adapter);
			
			shop_own_goods_listView = (ListView) findViewById(R.id.shop_own_goods_listView); //商店所拥有的商品
			//设置商品图片
			getData();
			//居左排列
			//shop_detail_images_listview.setSelection(shop_detail_images_listview.getCount()/2);
			//adapter.setSelectItem(shop_detail_images_listview.getCount()/2);
			//设置监听
			//shop_detail_images_listview.setOnItemClickListener(this);
			shop_detail_location.setOnClickListener(this);
			shop_detail_call_belong_shop_layout.setOnClickListener(this);
			
			//对商店商品设置adapter
			mDatas = new LinkedList<HashMap<String,Object>>();
			GoodsData.getShopOwnGoodsData();
			shopOwnGoodsAdapter = new ShopOwnGoodsAdapter(mDatas, ShopsDetailsActivity.this);
			shop_own_goods_listView.setAdapter(shopOwnGoodsAdapter);
			shop_own_goods_listView.setOnItemClickListener(new MyItemListener());
			shop_detail_order_number.setOnClickListener(this);
		}
		
		//测试使用
		private void getData(){
			//设置商店图片
			Common.fb.display(shop_detail_images_listview, Common.imageUrl);
			
		}
		/**
		 * initActionBar 功能:
		 * 初始化导航条
		 */
		public void initActionBar(){
			ActionBar actionBar = this.getSupportActionBar();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setCustomView(R.layout.global_navigate_up_back_register_next_step);
			search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
			search_back = (ImageView) findViewById(R.id.search_back);
			search_global_navigate_up_title.setText("店铺详情");
			//添加监听器
			search_back.setOnClickListener(this);
		}
		
		public void init(){
			/**PopupWindow的界面*/  
	        View contentView = getLayoutInflater()  
	                .inflate(R.layout.popupwindow, null);  
	        /**初始化PopupWindow*/  
	        popupWindow = new PopupWindow(contentView,  
	                ViewGroup.LayoutParams.MATCH_PARENT,  
	                ViewGroup.LayoutParams.WRAP_CONTENT);  
	        popupWindow.setFocusable(true);// 取得焦点  
	        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
	        /**设置PopupWindow弹出和退出时候的动画效果*/  
	        popupWindow.setAnimationStyle(R.style.animation);  
	          
	        parent = this.findViewById(R.id.shop_main);
	        
	        goods_detail_phone_cancel = (TextView) contentView.findViewById(R.id.goods_detail_phone_cancel);
	        goods_detail_phone = (TextView) contentView.findViewById(R.id.goods_detail_phone);
	        goods_detail_mobile_phone = (TextView) contentView.findViewById(R.id.goods_detail_mobile_phone);
	        
	        goods_detail_phone_cancel.setOnClickListener(this);
	        goods_detail_phone.setOnClickListener(this);
	        goods_detail_mobile_phone.setOnClickListener(this);
		}
		
		//初始化view对象
		public void initView(){
			shop_love = (ImageView) findViewById(R.id.shop_love);
			
			//添加监听器
			shop_love.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == search_back){
				finish();
			}else if(v == three_big_type_goods){
//				three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_pressed);
//				three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//				three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_unpressed);
				//
				Intent intent = new Intent();
				intent.setClass(ShopsDetailsActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}else if(v == three_big_type_mine){
//				three_big_type_goods.setImageResource(R.drawable.three_big_type_goods_unpressed);
//				three_big_type_shop.setImageResource(R.drawable.three_big_type_shop_unpressed);
//				three_big_type_mine.setImageResource(R.drawable.three_big_type_mine_pressed);
				//
				Intent intent = new Intent();
				intent.setClass(ShopsDetailsActivity.this, MineActivity.class);
				startActivity(intent);
				finish();
			}else if(v == shop_love){
				if(!isShopLove){
					shop_love.setImageResource(R.drawable.good_love);
					isShopLove = true;
					//提交收藏代码到服务器
					
				}else{
					shop_love.setImageResource(R.drawable.good_love_default);
					isShopLove = false;
				}
				//添加到收藏
				
			}else if(v == shop_detail_location){
				//定位
//				System.out.println("***************开始导航");
//				System.out.println("***************lan:"+Common.lan);
//				System.out.println("***************lng:"+Common.lng);
//				System.out.println("***************addr: "+Common.addr);
				BaiduNaviManager.getInstance().launchNavigator(this,   
			            Common.lan, Common.lng,Common.addr,   
			            39.90882, 116.39750,"北京天安门",  
			            NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式  
			            true,                                            //真实导航  
			            BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略  
			            new OnStartNavigationListener() {                //跳转监听  
			 
			                @Override  
			                public void onJumpToNavigator(Bundle configParams) {  
			                    Intent intent = new Intent(ShopsDetailsActivity.this, BNavigatorActivity.class);  
			                    intent.putExtras(configParams);  
			                    startActivity(intent);  
			                }  
			 
			                @Override  
			                public void onJumpToDownloader() {  
			                }  
			            });
				
			}else if(v == shop_detail_call_belong_shop_layout){
				/**设置PopupWindow弹出后的位置*/  
		        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
				//电话或手机
				final String callNumber = shop_detail_call_belong_shop.getText().toString().trim();
				final String phoneNumber = shop_detail_call_phone_belong_shop.getText().toString().trim();
				/**设置PopupWindow弹出后的位置*/  
		        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
		        goods_detail_mobile_phone.setText(Html.fromHtml("<u>"+phoneNumber+"</u>"));
		        goods_detail_phone.setText(Html.fromHtml("<u>"+callNumber+"</u>"));
		        /**设置PopupWindow弹出后的位置*/  
		        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0); 
			}else if(v == goods_detail_phone_cancel){
				 if (popupWindow.isShowing()) {  
		                popupWindow.dismiss();//关闭  
		            } 
			}else if(v == goods_detail_mobile_phone){
				//拨打移动手机
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+shop_detail_call_phone_belong_shop.getText().toString().trim()));
				startActivity(intent);
			}else if( v == goods_detail_phone){
				//拨打座机
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+shop_detail_call_belong_shop.getText().toString().trim()));
				startActivity(intent);
			}else if(v == shop_detail_order_number){
				//商品评价
				Intent intent = new Intent();
				intent.setClass(ShopsDetailsActivity.this, GoodsEvaluateActivity.class);
				startActivity(intent);
			}
		}
		//返回键
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			//按下键盘上返回按钮
			if(keyCode == KeyEvent.KEYCODE_BACK){
				finish();
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
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			// TODO Auto-generated method stub
//			//adapter.setSelectItem(position);
//			Intent intent = new Intent(ShopsDetailsActivity.this,ShopsImageShower.class);
//			ImageView iv = (ImageView) view;
//			bitmapUrl = (String) iv.getTag();
//			startActivity(intent);
//			
//	        //Toast.makeText(GoodsDetailsActivity.this, "点击了第"+position+"张图片", Toast.LENGTH_LONG).show();
//	        
//		}
		
		/**  
		* 将Bitmap转换成指定大小  
		* @param bitmap  
		* @param width  
		* @param height  
		* @return  
		*/  
		public static Bitmap createBitmapBySize(Bitmap bitmap,int width,int height)   
		{   
			return Bitmap.createScaledBitmap(bitmap, width, height, true);   
		}   
		/**  
		* Drawable 转 Bitmap  
		*   
		* @param drawable  
		* @return  
		*/  
		public static Bitmap drawableToBitmapByBD(Drawable drawable) {   
			BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;   
			return bitmapDrawable.getBitmap();   
		}  
		
		class MyItemListener implements OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				TextView tvName = (TextView) view.findViewById(R.id.goods_item_name);
				//Toast.makeText(ShopsDetailsActivity.this,tvName.getTag().toString(), 3000).show();
				Intent intent = new Intent(ShopsDetailsActivity.this, GoodsDetailsActivity.class);
				//传输相关商品id
				
				startActivity(intent);
			}
			
		}
		
		class MyHandler extends Handler{
			private boolean isFirst = true;
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
					case 0:
						if(progressDialog.isShowing())
							progressDialog.cancel();
						//mDatas = (LinkedList<HashMap<String, Object>>) msg.getData().get("goodsData");
//						System.out.println("***********************************");
//						for(int i = 0;i<mDatas.size();i++){
//							HashMap<String, Object> map = mDatas.get(i);
//							for(Entry<String, Object> entry:map.entrySet()){
//								System.out.println(entry.getKey());
//								System.out.println(entry.getValue());
//							}
//						}
						shopOwnGoodsAdapter.notifyDataSetChanged();
						break;
					case 1:
						break;
				}
				
			}
		}

}
