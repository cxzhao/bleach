package com.zhaochenxi.bleach.enums;

public enum CountryEnum {
	
	china("china","中国"), 
	japan("japan","日本");
	
	CountryEnum(String value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static String getCountryName(String value){
		if("china".equals(value)){
			return china.getName();
		}else if("japan".equals(value)){
			return japan.getName();
		}else{
			return null;
		}
	}

	private String value;
	private String name;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
