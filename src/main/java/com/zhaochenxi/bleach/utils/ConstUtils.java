package com.zhaochenxi.bleach.utils;

import java.util.HashMap;
import java.util.Map;

public class ConstUtils {
	
	public static final String DEFAULT_USERNAME = "佚名网友";
	//30分钟操作时间间隔
	public static final long OPERATOR_TIME_LIMIT = 1800000;
	
	public static  String themeImageHost = null;
	public static String newsThemeImageHost = null;
	public static String editorImageHost = null;
	public static String headImageHost = null;
	public static String defaultheadImage=null;
	public static Map<Integer,String> headImageMap;
	
	public static String themeImagePath=null;
	public static String newsThemeImage=null;
	public static String editorImage=null;
	public static String headImage=null;
			
	static {
		ConfigReader reader = new ConfigReader("config.properties");
		themeImageHost = reader.getString("themeImageHost");
		newsThemeImageHost = reader.getString("newsThemeImageHost");
		editorImageHost = reader.getString("editorImageHost");
		headImageHost = reader.getString("headImageHost");
		defaultheadImage = reader.getString("defaultheadImage");
		
		themeImagePath = reader.getString("themeImagePath");
		newsThemeImage = reader.getString("newsThemeImage");
		editorImage = reader.getString("editorImage");
		headImage = reader.getString("headImage");
		
		headImageMap = new HashMap<Integer,String>();
		for(int i=0;i<20;i++){
			headImageMap.put(0, headImageHost+i+".jpg");
		}
	}
}
