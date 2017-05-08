package com.zhaochenxi.bleach.enums;
/**
 * @ClassName: NewsTypeEnum 
 * @Description: 动漫情报 
 * @author zhaochenxi
 * @date 2016年11月13日 下午1:13:16
 */
public enum NewsTypeEnum {
	info(0,"情报"), 
	news(1,"新闻");
	
	NewsTypeEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static NewsTypeEnum getCartoonEnum(int value) {
		for (NewsTypeEnum u : NewsTypeEnum.values()) {
			if (value == u.ordinal()) {
				return u;
			}
		}
		return null;
	}

	private int value;
	private String name;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
