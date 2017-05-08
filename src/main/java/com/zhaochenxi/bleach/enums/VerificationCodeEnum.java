package com.zhaochenxi.bleach.enums;
/**
 * @ClassName: CodeEnum 
 * @Description: 验证码
 * @author zhaochenxi
 * @date 2016年11月13日 下午1:13:16
 */
public enum VerificationCodeEnum {
	forgetPass(0,"忘记密码");
	
	VerificationCodeEnum(int value,String name){
	this.setValue(value);
	this.setName(name);
	}
	
	public static VerificationCodeEnum getCartoonEnum(int value) {
		for (VerificationCodeEnum u : VerificationCodeEnum.values()) {
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
