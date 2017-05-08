package com.zhaochenxi.bleach.enums;
/**
 * @ClassName: NewsTypeEnum 
 * @Description: 动漫情报 
 * @author zhaochenxi
 * @date 2016年11月13日 下午1:13:16
 */
public enum TagTypeEnum {
	comment(0,"评论"), 
	news(1,"新闻");
	
	TagTypeEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static TagTypeEnum getCartoonEnum(int value) {
		for (TagTypeEnum u : TagTypeEnum.values()) {
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
