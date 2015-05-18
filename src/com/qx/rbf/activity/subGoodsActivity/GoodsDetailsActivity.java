package com.qx.rbf.activity.subGoodsActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.qx.rbf.MyApplication;
import com.qx.rbf.activity.BNavigatorActivity;
import com.qx.rbf.activity.R;
import com.qx.rbf.activity.subShopsActivity.ShopsDetailsActivity;
import com.qx.rbf.adapter.GoodsDetailImagesAdapter;
import com.qx.rbf.utils.BadgeView;
import com.qx.rbf.utils.Common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailsActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener{
	//view替换
	private View detailView,basketView;
	private boolean isChange = false;
	//上部导航
	private TextView search_global_navigate_up_title;
	private ImageView search_back;
	//下部导航
	//private TextView good_basket; //购物篮
	private int good_basket_number = 0; //购物车物品数量
	private ImageView good_love;  //收藏商品
	private boolean isGoodLove = false;  //判断是否收藏
	private TextView good_immediate_buy;  //立刻抢购
	//private TextView good_immediate_add_to_basket;  //加入购物车
	
	//其余对象
	private TextView good_detail_name; //商品名称
	//private TextView good_detail_from_area; //产地
	private TextView good_detail_left_number; //剩余数量
	private TextView good_detail_origin_price;//原价
	private TextView good_detail_qx_price;  //抢鲜价
	private TextView good_detail_discount; //折扣价
	//private TextView good_detail_compare_price; //比比价格   先忽略
	private Gallery good_detail_images_listview; //商品图片
	private Gallery good_detail_images_listview1	; //放大商品图片
	private TextView good_detail_qx_time; //抢先时间
	private TextView good_detail_belong_shop; //商品所属超市
	private TextView good_detail_location; //地址
	private LinearLayout good_detail_call_belong_shop_layout; //电话
	private TextView good_detail_call_belong_shop; //电话
	private TextView     good_detail_call_phone_belong_shop;  //手机
	private TextView good_detail_rate; //商品评价
	private GoodsDetailImagesAdapter adapter; 
	private BadgeView badge; 
	//用于显示大图的对象
	public static String bitmapUrl= null;
	public static String shopInfo = null;
	private PopupWindow popupWindow;  
    private View parent; 
	private TextView goods_detail_phone;
	private TextView goods_detail_mobile_phone;
	private TextView goods_detail_phone_cancel;
	//点击购买
	private EditText goodsNumber;//商品数量
	private TextView goodsPrice; //商品价钱
	private TextView ensureBuy; //确认购买按钮
	private boolean isFirstBuy = false; //是否第一次点击购买
	private AlertDialog dialog; //确认购买弹窗
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		detailView = inflater.inflate(R.layout.goods_details_main, null);
		setContentView1();
		initActionBar();
		init();
		initView();
		MyApplication.getInstance().addActivity(this);
	}
	private void setContentView1(){
		setContentView(detailView);
		//初始化
		good_detail_name = (TextView) findViewById(R.id.good_detail_name);
		//good_detail_from_area = (TextView) findViewById(R.id.good_detail_from_area);
		good_detail_left_number = (TextView) findViewById(R.id.good_detail_left_number);
		good_detail_origin_price = (TextView) findViewById(R.id.good_detail_origin_price);
		good_detail_qx_price = (TextView) findViewById(R.id.good_detail_qx_price);
		good_detail_discount = (TextView) findViewById(R.id.good_detail_discount);
		//good_detail_compare_price = (TextView) findViewById(R.id.good_detail_compare_price);
		good_detail_images_listview = (Gallery) findViewById(R.id.good_detail_images_listview);
		good_detail_qx_time = (TextView) findViewById(R.id.good_detail_qx_time);
		good_detail_belong_shop = (TextView) findViewById(R.id.good_detail_belong_shop);
		good_detail_location = (TextView) findViewById(R.id.good_detail_location);
		good_detail_call_belong_shop_layout = (LinearLayout) findViewById(R.id.good_detail_call_belong_shop_layout);
		good_detail_call_phone_belong_shop = (TextView)findViewById(R.id.good_detail_call_phone_belong_shop);
		good_detail_call_belong_shop = (TextView)findViewById(R.id.good_detail_call_belong_shop);
		good_detail_rate = (TextView) findViewById(R.id.good_detail_rate);
		adapter = new GoodsDetailImagesAdapter(this, getData());
		good_detail_images_listview.setAdapter(adapter);
		//居左排列
		good_detail_images_listview.setSelection(good_detail_images_listview.getCount()/2);
		adapter.setSelectItem(good_detail_images_listview.getCount()/2);
		//设置监听
		good_detail_images_listview.setOnItemClickListener(this);
		good_detail_belong_shop.setOnClickListener(this);
		good_detail_location.setOnClickListener(this);
		good_detail_rate.setOnClickListener(this);
		good_detail_call_belong_shop_layout.setOnClickListener(this);
		
	}
	
	//测试使用
	private List<HashMap<String, Object>> getData(){
		List<HashMap<String, Object>> data = new LinkedList<HashMap<String,Object>>();
		
		HashMap<String, Object> map;
		for(int i=0;i<6;i++){
			map = new HashMap<String, Object>();
			map.put("uri", Common.imageUrl);
			map.put("info", "商品描述：本商品原产自山东烟台，红富士苹果特点鲜明，价格便宜，且适宜存放，老少皆宜");
			data.add(map);
		}
		return data;
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
		search_global_navigate_up_title.setText("商品详情");
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
          
        parent = this.findViewById(R.id.main);
        
        goods_detail_phone_cancel = (TextView) contentView.findViewById(R.id.goods_detail_phone_cancel);
        goods_detail_phone = (TextView) contentView.findViewById(R.id.goods_detail_phone);
        goods_detail_mobile_phone = (TextView) contentView.findViewById(R.id.goods_detail_mobile_phone);
        
        goods_detail_phone_cancel.setOnClickListener(this);
        goods_detail_phone.setOnClickListener(this);
        goods_detail_mobile_phone.setOnClickListener(this);
	}
	
	//初始化view对象
	public void initView(){
		//good_basket = (TextView) findViewById(R.id.good_basket);
		good_love = (ImageView) findViewById(R.id.good_love);
		good_immediate_buy = (TextView) findViewById(R.id.good_immediate_buy);
		//good_immediate_add_to_basket = (TextView) findViewById(R.id.good_immediate_add_to_basket);
		//添加监听器
		//good_basket.setOnClickListener(this);
		good_love.setOnClickListener(this);
		good_immediate_buy.setOnClickListener(this);
		//good_immediate_add_to_basket.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == search_back){
			finish();
//		}else if(v == good_basket){
//			//购物车页面
//			
//			
		}else if(v == good_love){
			if(!isGoodLove){
				good_love.setImageResource(R.drawable.good_love);
				isGoodLove = true;
				//提交收藏代码到服务器
				
			}else{
				good_love.setImageResource(R.drawable.good_love_default);
				isGoodLove = false;
			}
			//添加到收藏
			
		}else if(v == good_immediate_buy){
			//立刻购买
			buyGoods();
//		}else if(v == good_immediate_add_to_basket){
//			//加入购物车，上传消息代码
//			
//			//数字改变
//			if(badge==null||!badge.isShown()){
//	            badge = new BadgeView(GoodsDetailsActivity.this,good_basket);//三个参数，1、this,2、当前控件（tab）3、在第几个控件上实现数字提醒
//	            good_basket_number ++;
//	            badge.setText(""+good_basket_number);
//	            badge.show();
//	        }else{
//	        	good_basket_number++;
//	            badge.setText(""+good_basket_number);
//	       } 
			//
			
		}else if(v == good_detail_belong_shop){
			//所属商店
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, ShopsDetailsActivity.class);
			startActivity(intent);
			
			
		}else if(v == good_detail_location){
			//定位
//			System.out.println("***************开始导航");
//			System.out.println("***************lan:"+Common.lan);
//			System.out.println("***************lng:"+Common.lng);
//			System.out.println("***************addr: "+Common.addr);
			BaiduNaviManager.getInstance().launchNavigator(this,   
		            Common.lan, Common.lng,Common.addr,   
		            39.90882, 116.39750,"北京天安门",  
		            NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式  
		            true,                                            //真实导航  
		            BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略  
		            new OnStartNavigationListener() {                //跳转监听  
		 
		                @Override  
		                public void onJumpToNavigator(Bundle configParams) {  
		                    Intent intent = new Intent(GoodsDetailsActivity.this, BNavigatorActivity.class);  
		                    intent.putExtras(configParams);  
		                    startActivity(intent);  
		                }  
		 
		                @Override  
		                public void onJumpToDownloader() {  
		                }  
		            });
			
		}else if(v == good_detail_call_belong_shop_layout){
			/**设置PopupWindow弹出后的位置*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
			//电话或手机
			final String callNumber = good_detail_call_belong_shop.getText().toString().trim();
			final String phoneNumber = good_detail_call_phone_belong_shop.getText().toString().trim();
			/**设置PopupWindow弹出后的位置*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
	        goods_detail_mobile_phone.setText(Html.fromHtml("<u>"+phoneNumber+"</u>"));
	        goods_detail_phone.setText(Html.fromHtml("<u>"+callNumber+"</u>"));
	        /**设置PopupWindow弹出后的位置*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
		}else if(v == good_detail_rate){
			//商品评价
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, GoodsEvaluateActivity.class);
			startActivity(intent);
			
		}else if(v == goods_detail_phone_cancel){
			 if (popupWindow.isShowing()) {  
	                popupWindow.dismiss();//关闭  
	            } 
		}else if(v == goods_detail_mobile_phone){
			//拨打移动手机
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good_detail_call_phone_belong_shop.getText().toString().trim()));
			startActivity(intent);
		}else if( v == goods_detail_phone){
			//拨打座机
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good_detail_call_belong_shop.getText().toString().trim()));
			startActivity(intent);
		}else if(v == ensureBuy){
			//确定购买
			double price = Double.parseDouble(good_detail_qx_price.getText().toString().trim());
			double number = 0;
            if(!"".equals(goodsNumber.getText().toString().trim())){
            	 number = Double.parseDouble(goodsNumber.getText().toString().trim());
            }
            DecimalFormat format = new DecimalFormat(".##");
            String totalPrice = format.format(price * number);
            if(".0".equals(totalPrice)){
            	Toast.makeText(GoodsDetailsActivity.this, "购买数量不能为0", 3000).show();
            }else{
            	//开始购买，发送订单
            	Toast.makeText(GoodsDetailsActivity.this, totalPrice+" 元", 3000).show();
            	if(dialog.isShowing()){
            		dialog.dismiss();
            	}
            	//购买完毕，跳转到评价页面，实际调试时，需放到回调函数中
            	//在跳转时，需加入商品、商家id
            	Intent intent = new Intent();
            	intent.setClass(GoodsDetailsActivity.this, EvaluateActivity.class);
            	startActivity(intent);
            }
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		adapter.setSelectItem(position);
		Intent intent = new Intent(GoodsDetailsActivity.this,ImageShower.class);
		ImageView iv = (ImageView) view;
		bitmapUrl = (String) iv.getTag(R.id.first_tag);
		shopInfo = (String) iv.getTag(R.id.second_tag);
		
		startActivity(intent);
		
        //Toast.makeText(GoodsDetailsActivity.this, "点击了第"+position+"张图片", Toast.LENGTH_LONG).show();
        
	}
	
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
	
	private final class ItemClickListener implements OnItemClickListener{  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            if (popupWindow.isShowing()) {  
                popupWindow.dismiss();//关闭  
            }  
        }  
    }  
    
	private void buyGoods(){
		AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDetailsActivity.this);
		View  view = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.ensure_buy, null);
		if(!isFirstBuy){
			ensureBuy = (TextView) view.findViewById(R.id.ensureBuy);
			goodsNumber = (EditText) view.findViewById(R.id.goodsNumber);
			goodsPrice = (TextView) view.findViewById(R.id.goodsPrice);
			
			ensureBuy.setOnClickListener(this);
			goodsNumber.addTextChangedListener(new TextWatcher() {
				
				@Override
	            public void onTextChanged(CharSequence s, int start, int before,
	                    int count) {
	                if (s.toString().contains(".")) {
	                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
	                        s = s.toString().subSequence(0,
	                                s.toString().indexOf(".") + 2);
	                        goodsNumber.setText(s);
	                        goodsNumber.setSelection(s.length());
	                    }
	                }
	                if (s.toString().trim().substring(0).equals(".")) {
	                    s = "0" + s;
	                    goodsNumber.setText(s);
	                    goodsNumber.setSelection(2);
	                }
	 
	                if (s.toString().startsWith("0")
	                        && s.toString().trim().length() > 1) {
	                    if (!s.toString().substring(1, 2).equals(".")) {
	                    	goodsNumber.setText(s.subSequence(0, 1));
	                        goodsNumber.setSelection(1);
	                        return;
	                    }
	                }
	                //根据数量动态计算价钱
	                double price = Double.parseDouble(good_detail_qx_price.getText().toString().trim());
	                double number = 0;
	                if(!"".equals(goodsNumber.getText().toString().trim())){
	                	 number = Double.parseDouble(goodsNumber.getText().toString().trim());
	                }
	               
	                DecimalFormat format = new DecimalFormat(".##");
	                String totalPrice = format.format(price * number);
	                goodsPrice.setText(totalPrice + " 元");
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence s, int start, int count,
	                    int after) {
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable s) {
	                // TODO Auto-generated method stub
	                 
	            }
	 
	        });
			
		}
		
		builder.setView(view);
		dialog = builder.show();
	}
}
