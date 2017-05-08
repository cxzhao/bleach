package com.zhaochenxi.bleach.enums;

public enum SaveTypeEnum {

	draft(0,"草稿"), 
	publish(1,"发布"),
	delete(2,"删除");
	SaveTypeEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static SaveTypeEnum getSaveTypeEnum(int value) {
		for (SaveTypeEnum u : SaveTypeEnum.values()) {
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
