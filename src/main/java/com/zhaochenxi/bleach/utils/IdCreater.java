package com.zhaochenxi.bleach.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: IdCreater
 * 
 * @Description: 全局唯一的id创建类，适用于1000级别的集群服务，保证集群里id在并发时不重复（需借助于机器id）可产生18位的唯一id
 * @date 2016年5月4日
 */

public class IdCreater {
	static int plus = 0;
	static String hostType="07";
	
	/**
	 * @Description: 获取唯一id
	 * @param N/A
	 * @return String<id>
	 * @throws: N/A
	 * @author: dingjingjing058
	 * @date: 2016年4月11日
	 */
	public static String createId() {
		String id = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		id = df.format(new Date());// new Date()为获取当前系统时间
		id = id + String.format("%2d", plus).replace(" ", "0");
		plus++;
		if (plus == 100)
			plus = 0;
		id = id.substring(3, 19);
		return id + hostType;
	}
	
	public static String createId17() {
		String id = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		id = df.format(new Date());// new Date()为获取当前系统时间
		id = id + String.format("%2d", plus).replace(" ", "0");
		plus++;
		if (plus == 100)
			plus = 0;
		id = id.substring(3, 18);
		return "0"+id + hostType;
	}
	

}
