package com.zhaochenxi.bleach.enums;

public enum OperatorEnum {

	recommendAgree(0,"用户推荐同意"),
	cartoonLove(1,"喜欢这个动漫"),
	articleLove(2,"喜欢评论文章");
	OperatorEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static OperatorEnum getOperatorEnum(int value) {
		for (OperatorEnum u : OperatorEnum.values()) {
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
