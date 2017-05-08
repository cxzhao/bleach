package com.zhaochenxi.bleach.shiro.filter;

import java.util.HashSet;
import java.util.Set;

import com.zhaochenxi.bleach.utils.ConfigReader;
import com.zhaochenxi.bleach.utils.Utils;

public class CorsProp {

	public static String ACCESS_CONTROL_ALLOW_HEADERS=null;
	//请求允许的origin
	public static Set<String> originSet = new HashSet<String>();
			
	static {
		ConfigReader reader = new ConfigReader("cors.properties");
		String origin = reader.getString("origin");
		setOrigin(origin);
	}
	
	private static void setOrigin(String origin){
		if(Utils.isEmptyOrNull(origin)){
			return;
		}
		String [] originArray = origin.split("\\,");
		for(String s:originArray){
			originSet.add(s);
		}
	}
	
}
