package com.qx.rbf.utils;

import java.io.IOException;

import org.bouncycastle.util.encoders.Hex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qx.rbf.HexUtil;
import com.qx.rbf.AES.AESUtil;
import com.qx.rbf.Gzip.GZipUtil;
import com.qx.rbf.MD5.MD5Util;
import com.qx.rbf.RSA.RSAUtil;

public class Instance {
	private static AESUtil aes = new AESUtil();
	private static RSAUtil rsa = new RSAUtil();
	private static MD5Util md5 = new MD5Util();
	private static GZipUtil gzip = new GZipUtil();
	
	public static GZipUtil getGzipInstance(){
		if(gzip==null){
			gzip = new GZipUtil();
			return gzip;
		}else{
			return gzip;
		}
		
	}
	
	public static RSAUtil getRSAInstance(){
		if(rsa==null){
			rsa = new RSAUtil();
			return rsa;
		}else{
			return rsa;
		}
	}
	
	public static AESUtil getAESInstance(){
		if(aes==null){
			aes = new AESUtil();
			return aes;
		}else{
			return aes;
		}
	}
	
	public static MD5Util getMD5Instance(){
		if(md5==null){
			md5 = new MD5Util();
			return md5;
		}else{
			return md5;
		}
	}
	
	public static String EncryptGzipJsonData(JSONObject json){
		JSONObject body = json.getJSONObject("body");
		JSONObject data = new JSONObject();
		JSONObject header = new JSONObject();
		
		String bodyStr = body.toJSONString();
		AESUtil aes = Instance.getAESInstance();
		RSAUtil rsa = Instance.getRSAInstance();
		GZipUtil gzip = Instance.getGzipInstance();
		String AESPassword = aes.generatorKey(16);
		byte[] aesStr = aes.encrypt(bodyStr, AESPassword);
		byte[] rsaStr = null;
		try {
			rsa.loadPublicKey(RSAUtil.SERVER_DEFAULT_PUBLIC_KEY);
			rsaStr = rsa.encrypt(rsa.getPublicKey(), AESPassword.getBytes());
			data.put("body", new String(Hex.encode(aesStr)));
			header.put("sign",new String(Hex.encode(rsaStr)));
			//System.out.println("sign: "+new String(Hex.encode(rsaStr)));
			data.put("header", header);
			//System.out.println(data.toJSONString());
			//Toast.makeText(RegisterActivity.this,AESPassword , 8000).show();
			return data.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static JSONObject DencryptUnGzipJsonData(String json){
		JSONObject result = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject body = new JSONObject();
		
		AESUtil aes = Instance.getAESInstance();
		RSAUtil rsa = Instance.getRSAInstance();
		//GZipUtil gzip = Instance.getGzipInstance();
		try {
			//String unGzip = gzip.uncompress(json);
			JSONObject tempJson = JSON.parseObject(json);
			JSONObject tempHeader = tempJson.getJSONObject("header");
			String signStr = tempHeader.getString("sign");
			String resultStr = tempHeader.getString("result");
			String reasonStr = tempHeader.getString("reason");
			header.put("result", resultStr);
			header.put("reason", reasonStr);
			
			String tempBody = tempJson.getString("body");
			//ªÒ»°aes√‹‘ø
			rsa.loadPrivateKey(RSAUtil.APP_DEFAULT_PRIVATE_KEY);
			byte[] password = rsa.decrypt(rsa.getPrivateKey(),Hex.decode(signStr));
			byte[] bodyStr = aes.decrypt(Hex.decode(tempBody), new String(password));
			
			body = JSON.parseObject(new String(bodyStr));
			System.out.println("bodyStr: "+new String(bodyStr));
			result.put("header", header);
			result.put("body", body);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return result;
		}
		
		
	}
}
