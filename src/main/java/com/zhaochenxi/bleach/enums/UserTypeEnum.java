package com.zhaochenxi.bleach.enums;

public enum UserTypeEnum {

	et(0,"本站用户"), 
	qq(1,"QQ用户"), 
	weixin(2,"微信用户");	
	
	UserTypeEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static UserTypeEnum getUserTypeEnum(int value) {
		for (UserTypeEnum u : UserTypeEnum.values()) {
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
