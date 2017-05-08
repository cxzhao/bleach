package com.zhaochenxi.bleach.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private Properties properties;

	public ConfigReader(String filename) {
		properties = new Properties();
		InputStream in = ConfigReader.class.getResourceAsStream("/" + filename);
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getString(String key){
		return properties.getProperty(key);
	}
	
	public static void main(String[] args) {

	}

}
