package com.zhaochenxi.bleach.enums;

public enum DataStatusEnum {
	active(0,"有效数据"), 
	delete(1,"删除数据"), 
	garbage(2,"垃圾数据"),
	advertisement(3,"广告数据"), 
	badWord(4,"不文明用语");
	
	DataStatusEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static DataStatusEnum getDataStatusEnum(int value) {
		for (DataStatusEnum u : DataStatusEnum.values()) {
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
