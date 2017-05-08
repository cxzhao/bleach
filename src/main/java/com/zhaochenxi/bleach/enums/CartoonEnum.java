package com.zhaochenxi.bleach.enums;
/**
 * @ClassName: CartoonEnum 
 * @Description: 动漫枚举
 * @author zhaochenxi
 * @date 2016年11月10日 下午8:37:40
 */
public enum CartoonEnum {
	updating(0,"连载中"), 
	end(1,"已完结");
	
	CartoonEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static CartoonEnum getCartoonEnum(int value) {
		for (CartoonEnum u : CartoonEnum.values()) {
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
