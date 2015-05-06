package com.qx.rbf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	//是否为数字
	public static boolean isNumeric(String str){ 
		
	     Pattern pattern = Pattern.compile("[0-9]*"); 
	     return pattern.matcher(str).matches();    
	}
	
	//验证手机号是否合法
	public static boolean isAccountValid(String account){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
		Matcher m = pattern.matcher(account);
		return m.matches();
	}
}
