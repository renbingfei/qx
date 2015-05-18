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
	//view�滻
	private View detailView,basketView;
	private boolean isChange = false;
	//�ϲ�����
	private TextView search_global_navigate_up_title;
	private ImageView search_back;
	//�²�����
	//private TextView good_basket; //������
	private int good_basket_number = 0; //���ﳵ��Ʒ����
	private ImageView good_love;  //�ղ���Ʒ
	private boolean isGoodLove = false;  //�ж��Ƿ��ղ�
	private TextView good_immediate_buy;  //��������
	//private TextView good_immediate_add_to_basket;  //���빺�ﳵ
	
	//�������
	private TextView good_detail_name; //��Ʒ����
	//private TextView good_detail_from_area; //����
	private TextView good_detail_left_number; //ʣ������
	private TextView good_detail_origin_price;//ԭ��
	private TextView good_detail_qx_price;  //���ʼ�
	private TextView good_detail_discount; //�ۿۼ�
	//private TextView good_detail_compare_price; //�ȱȼ۸�   �Ⱥ���
	private Gallery good_detail_images_listview; //��ƷͼƬ
	private Gallery good_detail_images_listview1	; //�Ŵ���ƷͼƬ
	private TextView good_detail_qx_time; //����ʱ��
	private TextView good_detail_belong_shop; //��Ʒ��������
	private TextView good_detail_location; //��ַ
	private LinearLayout good_detail_call_belong_shop_layout; //�绰
	private TextView good_detail_call_belong_shop; //�绰
	private TextView     good_detail_call_phone_belong_shop;  //�ֻ�
	private TextView good_detail_rate; //��Ʒ����
	private GoodsDetailImagesAdapter adapter; 
	private BadgeView badge; 
	//������ʾ��ͼ�Ķ���
	public static String bitmapUrl= null;
	public static String shopInfo = null;
	private PopupWindow popupWindow;  
    private View parent; 
	private TextView goods_detail_phone;
	private TextView goods_detail_mobile_phone;
	private TextView goods_detail_phone_cancel;
	//�������
	private EditText goodsNumber;//��Ʒ����
	private TextView goodsPrice; //��Ʒ��Ǯ
	private TextView ensureBuy; //ȷ�Ϲ���ť
	private boolean isFirstBuy = false; //�Ƿ��һ�ε������
	private AlertDialog dialog; //ȷ�Ϲ��򵯴�
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
		//��ʼ��
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
		//��������
		good_detail_images_listview.setSelection(good_detail_images_listview.getCount()/2);
		adapter.setSelectItem(good_detail_images_listview.getCount()/2);
		//���ü���
		good_detail_images_listview.setOnItemClickListener(this);
		good_detail_belong_shop.setOnClickListener(this);
		good_detail_location.setOnClickListener(this);
		good_detail_rate.setOnClickListener(this);
		good_detail_call_belong_shop_layout.setOnClickListener(this);
		
	}
	
	//����ʹ��
	private List<HashMap<String, Object>> getData(){
		List<HashMap<String, Object>> data = new LinkedList<HashMap<String,Object>>();
		
		HashMap<String, Object> map;
		for(int i=0;i<6;i++){
			map = new HashMap<String, Object>();
			map.put("uri", Common.imageUrl);
			map.put("info", "��Ʒ����������Ʒԭ����ɽ����̨���츻ʿƻ���ص��������۸���ˣ������˴�ţ����ٽ���");
			data.add(map);
		}
		return data;
	}
	/**
	 * initActionBar ����:
	 * ��ʼ��������
	 */
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_navigate_up_back_register_next_step);
		search_global_navigate_up_title = (TextView) findViewById(R.id.search_global_navigate_up_title);
		search_back = (ImageView) findViewById(R.id.search_back);
		search_global_navigate_up_title.setText("��Ʒ����");
		//��Ӽ�����
		search_back.setOnClickListener(this);
	}
	
	public void init(){
		/**PopupWindow�Ľ���*/  
        View contentView = getLayoutInflater()  
                .inflate(R.layout.popupwindow, null);  
        /**��ʼ��PopupWindow*/  
        popupWindow = new PopupWindow(contentView,  
                ViewGroup.LayoutParams.MATCH_PARENT,  
                ViewGroup.LayoutParams.WRAP_CONTENT);  
        popupWindow.setFocusable(true);// ȡ�ý���  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
        /**����PopupWindow�������˳�ʱ��Ķ���Ч��*/  
        popupWindow.setAnimationStyle(R.style.animation);  
          
        parent = this.findViewById(R.id.main);
        
        goods_detail_phone_cancel = (TextView) contentView.findViewById(R.id.goods_detail_phone_cancel);
        goods_detail_phone = (TextView) contentView.findViewById(R.id.goods_detail_phone);
        goods_detail_mobile_phone = (TextView) contentView.findViewById(R.id.goods_detail_mobile_phone);
        
        goods_detail_phone_cancel.setOnClickListener(this);
        goods_detail_phone.setOnClickListener(this);
        goods_detail_mobile_phone.setOnClickListener(this);
	}
	
	//��ʼ��view����
	public void initView(){
		//good_basket = (TextView) findViewById(R.id.good_basket);
		good_love = (ImageView) findViewById(R.id.good_love);
		good_immediate_buy = (TextView) findViewById(R.id.good_immediate_buy);
		//good_immediate_add_to_basket = (TextView) findViewById(R.id.good_immediate_add_to_basket);
		//��Ӽ�����
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
//			//���ﳵҳ��
//			
//			
		}else if(v == good_love){
			if(!isGoodLove){
				good_love.setImageResource(R.drawable.good_love);
				isGoodLove = true;
				//�ύ�ղش��뵽������
				
			}else{
				good_love.setImageResource(R.drawable.good_love_default);
				isGoodLove = false;
			}
			//��ӵ��ղ�
			
		}else if(v == good_immediate_buy){
			//���̹���
			buyGoods();
//		}else if(v == good_immediate_add_to_basket){
//			//���빺�ﳵ���ϴ���Ϣ����
//			
//			//���ָı�
//			if(badge==null||!badge.isShown()){
//	            badge = new BadgeView(GoodsDetailsActivity.this,good_basket);//����������1��this,2����ǰ�ؼ���tab��3���ڵڼ����ؼ���ʵ����������
//	            good_basket_number ++;
//	            badge.setText(""+good_basket_number);
//	            badge.show();
//	        }else{
//	        	good_basket_number++;
//	            badge.setText(""+good_basket_number);
//	       } 
			//
			
		}else if(v == good_detail_belong_shop){
			//�����̵�
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, ShopsDetailsActivity.class);
			startActivity(intent);
			
			
		}else if(v == good_detail_location){
			//��λ
//			System.out.println("***************��ʼ����");
//			System.out.println("***************lan:"+Common.lan);
//			System.out.println("***************lng:"+Common.lng);
//			System.out.println("***************addr: "+Common.addr);
			BaiduNaviManager.getInstance().launchNavigator(this,   
		            Common.lan, Common.lng,Common.addr,   
		            39.90882, 116.39750,"�����찲��",  
		            NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //��·��ʽ  
		            true,                                            //��ʵ����  
		            BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //�����߲���  
		            new OnStartNavigationListener() {                //��ת����  
		 
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
			/**����PopupWindow�������λ��*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
			//�绰���ֻ�
			final String callNumber = good_detail_call_belong_shop.getText().toString().trim();
			final String phoneNumber = good_detail_call_phone_belong_shop.getText().toString().trim();
			/**����PopupWindow�������λ��*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
	        goods_detail_mobile_phone.setText(Html.fromHtml("<u>"+phoneNumber+"</u>"));
	        goods_detail_phone.setText(Html.fromHtml("<u>"+callNumber+"</u>"));
	        /**����PopupWindow�������λ��*/  
	        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);  
		}else if(v == good_detail_rate){
			//��Ʒ����
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, GoodsEvaluateActivity.class);
			startActivity(intent);
			
		}else if(v == goods_detail_phone_cancel){
			 if (popupWindow.isShowing()) {  
	                popupWindow.dismiss();//�ر�  
	            } 
		}else if(v == goods_detail_mobile_phone){
			//�����ƶ��ֻ�
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good_detail_call_phone_belong_shop.getText().toString().trim()));
			startActivity(intent);
		}else if( v == goods_detail_phone){
			//��������
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good_detail_call_belong_shop.getText().toString().trim()));
			startActivity(intent);
		}else if(v == ensureBuy){
			//ȷ������
			double price = Double.parseDouble(good_detail_qx_price.getText().toString().trim());
			double number = 0;
            if(!"".equals(goodsNumber.getText().toString().trim())){
            	 number = Double.parseDouble(goodsNumber.getText().toString().trim());
            }
            DecimalFormat format = new DecimalFormat(".##");
            String totalPrice = format.format(price * number);
            if(".0".equals(totalPrice)){
            	Toast.makeText(GoodsDetailsActivity.this, "������������Ϊ0", 3000).show();
            }else{
            	//��ʼ���򣬷��Ͷ���
            	Toast.makeText(GoodsDetailsActivity.this, totalPrice+" Ԫ", 3000).show();
            	if(dialog.isShowing()){
            		dialog.dismiss();
            	}
            	//������ϣ���ת������ҳ�棬ʵ�ʵ���ʱ����ŵ��ص�������
            	//����תʱ���������Ʒ���̼�id
            	Intent intent = new Intent();
            	intent.setClass(GoodsDetailsActivity.this, EvaluateActivity.class);
            	startActivity(intent);
            }
		}
	}
	//���ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//���¼����Ϸ��ذ�ť
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
		//�����������ַ�ʽ
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
		
        //Toast.makeText(GoodsDetailsActivity.this, "����˵�"+position+"��ͼƬ", Toast.LENGTH_LONG).show();
        
	}
	
	/**  
	* ��Bitmapת����ָ����С  
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
	* Drawable ת Bitmap  
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
                popupWindow.dismiss();//�ر�  
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
	                //����������̬�����Ǯ
	                double price = Double.parseDouble(good_detail_qx_price.getText().toString().trim());
	                double number = 0;
	                if(!"".equals(goodsNumber.getText().toString().trim())){
	                	 number = Double.parseDouble(goodsNumber.getText().toString().trim());
	                }
	               
	                DecimalFormat format = new DecimalFormat(".##");
	                String totalPrice = format.format(price * number);
	                goodsPrice.setText(totalPrice + " Ԫ");
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
