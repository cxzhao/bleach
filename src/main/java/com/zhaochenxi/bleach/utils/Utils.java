package com.zhaochenxi.bleach.utils;

public class Utils {

	private Utils(){}
	
	/**
	 * @Title: isEmptyOrNull 
	 * @Description: 判断字符串是否空字符串和NULL 
	 * @param 
	 * @return boolean 
	 * @throws 
	 * @author zhaochenxi
	 */
	public static boolean isEmptyOrNull(String Str){
		if(Str==null){
			return true;
		}
		if(Str.trim().equals("")){
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Access_Control_Allow_Methods".toUpperCase());
	}

}
