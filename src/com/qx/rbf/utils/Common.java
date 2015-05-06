package com.qx.rbf.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

public class Common {
	public static boolean isLogin = false;  //是否登录
	public static LocationClient locationClient = null;  //百度定位
	public static final int UPDATE_TIME = 5000;   //定位刷新时间
	public static int LOCATION_COUTNS = 0;        //定位次数
	public static BDLocationListener myListener = null;
	public static double lan = 0.0d;
	public static double lng = 0.0d; //经纬度
	public static String addr = "";//地理位置
    
	public static FinalBitmap fb = null;
	public static FinalHttp fhttp = null;
	public static String baseUrl = "http://10.108.167.26:8080/";
	public static String imageUrl = baseUrl + "ic_launcher.png";
	public static String[] beijingArea = new String[] {"全部","东城","西城","海淀","朝阳","丰台","通州","昌平","大兴"};
	public static int[] beijingArea2Sub = new int[]{1,17,8,11,8,7,8,10,10};  //每个地区有多少子区域
	
	//北京各区县下属商业区
	public static String[] beijingAreaSub = new String[]{
		//全部
		"全部",
		//东城
		"安定门街道","建国门街道","朝阳门街道","东直门街道","东华门街道","和平里街道","北新桥街道","交道口街道","景山街道","东四街道","天坛街道","东花市街道","前门街道","龙潭街道","永定门外街道","崇文门外街道","体育馆路街道",
		//西城
		"西长安街街道","新街口街道","月坛街道","展览路街道","德胜街道","金融街街道","什刹海街道","大栅栏街道",
		//海淀
		"万寿路街道","羊坊店街道","甘家口街道","八里庄街道","紫竹院街道","北下关街道","北太平庄街道","海淀街道","中关村街道","学院路街道","清河街道",
		//朝阳
		"朝外街道","劲松街道","建外街道","呼家楼街道","八里庄街道","三里屯街道","团结湖街道","双井街道",
		//丰台
		"大红门街道","南苑街道","东高地街道","东铁匠营街道","卢沟桥街道","丰台街道","新村街道",
		//通州
		"永顺镇","梨园镇","宋庄镇","漷县镇","张家湾镇","马驹桥镇","西集镇","永乐店镇",
		//昌平
		"昌平镇","阳坊镇","回龙观镇","小汤山镇","南邵镇","崔村镇","百善镇","东小口镇","北七家镇","沙河",
		//大兴
		"黄村镇","西红门","旧宫","亦庄","瀛海","青云店镇","长子营镇","采育镇","礼贤镇","安定镇",
		
	};
	//商品类型 对照
	public static HashMap<String,String> goodsNameToType = new HashMap<String, String>(){
		{
			put("fruit","水果");
			put("vegetable","蔬菜");
			put("cookie","糕点");
			put("fish","生鲜");
			put("cooked","熟食");
			put("all","全部");
		}
	};
	
	//由value获得key
	public static String valueGetKey(Map map,String value) {
	    Set set = map.entrySet();
	    Iterator it = set.iterator();
	    System.out.println(value);
	    while(it.hasNext()) {
	      Map.Entry entry = (Map.Entry)it.next();
	      if(entry.getValue().equals(value)) {
	        String valueStr = entry.getKey().toString();
	        return valueStr;
	      }
	    }
		return "all";
	  }
	
	//注册监听函数
	public static void registerLocation(){
		locationClient.registerLocationListener( myListener );    //注册监听函数
	}
	
	//取消注册监听函数
	public static void unregisterLocation(){
		locationClient.unRegisterLocationListener( myListener );    //注册监听函数
	}
	
}
