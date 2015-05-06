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
	public static boolean isLogin = false;  //�Ƿ��¼
	public static LocationClient locationClient = null;  //�ٶȶ�λ
	public static final int UPDATE_TIME = 5000;   //��λˢ��ʱ��
	public static int LOCATION_COUTNS = 0;        //��λ����
	public static BDLocationListener myListener = null;
	public static double lan = 0.0d;
	public static double lng = 0.0d; //��γ��
	public static String addr = "";//����λ��
    
	public static FinalBitmap fb = null;
	public static FinalHttp fhttp = null;
	public static String baseUrl = "http://10.108.167.26:8080/";
	public static String imageUrl = baseUrl + "ic_launcher.png";
	public static String[] beijingArea = new String[] {"ȫ��","����","����","����","����","��̨","ͨ��","��ƽ","����"};
	public static int[] beijingArea2Sub = new int[]{1,17,8,11,8,7,8,10,10};  //ÿ�������ж���������
	
	//����������������ҵ��
	public static String[] beijingAreaSub = new String[]{
		//ȫ��
		"ȫ��",
		//����
		"�����Žֵ�","�����Žֵ�","�����Žֵ�","��ֱ�Žֵ�","�����Žֵ�","��ƽ��ֵ�","�����Žֵ�","�����ڽֵ�","��ɽ�ֵ�","���Ľֵ�","��̳�ֵ�","�����нֵ�","ǰ�Žֵ�","��̶�ֵ�","��������ֵ�","��������ֵ�","������·�ֵ�",
		//����
		"�������ֵֽ�","�½ֿڽֵ�","��̳�ֵ�","չ��·�ֵ�","��ʤ�ֵ�","���ڽֵֽ�","ʲɲ���ֵ�","��դ���ֵ�",
		//����
		"����·�ֵ�","�򷻵�ֵ�","�ʼҿڽֵ�","����ׯ�ֵ�","����Ժ�ֵ�","���¹ؽֵ�","��̫ƽׯ�ֵ�","����ֵ�","�йش�ֵ�","ѧԺ·�ֵ�","��ӽֵ�",
		//����
		"����ֵ�","���ɽֵ�","����ֵ�","����¥�ֵ�","����ׯ�ֵ�","�����ͽֵ�","�Ž���ֵ�","˫���ֵ�",
		//��̨
		"����Žֵ�","��Է�ֵ�","���ߵؽֵ�","������Ӫ�ֵ�","¬���Žֵ�","��̨�ֵ�","�´�ֵ�",
		//ͨ��
		"��˳��","��԰��","��ׯ��","�t����","�ż�����","�������","������","���ֵ���",
		//��ƽ
		"��ƽ��","������","��������","С��ɽ��","������","�޴���","������","��С����","���߼���","ɳ��",
		//����
		"�ƴ���","������","�ɹ�","��ׯ","孺�","���Ƶ���","����Ӫ��","������","������","������",
		
	};
	//��Ʒ���� ����
	public static HashMap<String,String> goodsNameToType = new HashMap<String, String>(){
		{
			put("fruit","ˮ��");
			put("vegetable","�߲�");
			put("cookie","���");
			put("fish","����");
			put("cooked","��ʳ");
			put("all","ȫ��");
		}
	};
	
	//��value���key
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
	
	//ע���������
	public static void registerLocation(){
		locationClient.registerLocationListener( myListener );    //ע���������
	}
	
	//ȡ��ע���������
	public static void unregisterLocation(){
		locationClient.unRegisterLocationListener( myListener );    //ע���������
	}
	
}
