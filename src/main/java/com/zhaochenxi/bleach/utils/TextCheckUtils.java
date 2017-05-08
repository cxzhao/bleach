package com.zhaochenxi.bleach.utils;

import com.zhaochenxi.bleach.enums.DataStatusEnum;

/**
 * @ClassName: TextCheckUtils
 * @Description: 文本检查工具，检查文本中是否有不文明用语，广告链接
 * @author zhaochenxi
 * @date 2016年11月4日 上午11:42:01
 */
public class TextCheckUtils {

	public static DataStatusEnum checkText(String text) {
		text = text.trim();
		if (text.contains("http") || text.contains("https") || text.contains("HTTP") || text.contains("HTTPS")
				|| text.contains("WWW") || text.contains("www") || text.contains(".com") || text.contains(".COM")
				|| text.contains("。com") || text.contains("。COM")||text.contains(".cn")||text.contains(".CN")) {
			return DataStatusEnum.advertisement;
		}else{
			return DataStatusEnum.active;
		}
	}
	
	public static void main(String[]arg){
		DataStatusEnum res=checkText("发挥大开发的撒发挥大富豪的凯撒红发的baidu cn 发的咖啡好的撒 发货的金卡后付款及第三方");
		System.out.println(res.getName());
	}
}
