package com.zhaochenxi.bleach.utils;

public class CorsUtils {

	public static String ORIGIN=null;
	public static String ACCESS_CONTROL_ALLOW_HEADERS=null;
	
	static {
		ConfigReader reader = new ConfigReader("cors.properties");
		ORIGIN = reader.getString("origin");
	}
	
	
}
