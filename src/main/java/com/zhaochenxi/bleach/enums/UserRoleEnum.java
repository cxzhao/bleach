package com.zhaochenxi.bleach.enums;

/**
 * @ClassName: UserRoleEnum
 * @Description: 用户枚举
 * @author zhaochenxi
 * @date 2016年10月29日 下午5:17:37
 */
public enum UserRoleEnum {
	admin(0,"管理员"), 
	visitor(1,"游客"), 
	register(2,"注册用户");	
	
	UserRoleEnum(int value,String name){
	this.setValue(value);
	this.name=name;
	}

	private int value;
	@SuppressWarnings("unused")
	private String name;

	/**
	 * 根据传入的参数返回对应的枚举
	 * @param num
	 * @return
	 */
	public static UserRoleEnum getUserRoleEnum(int value) {
		for (UserRoleEnum u : UserRoleEnum.values()) {
			if (value == u.ordinal()) {
				return u;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}



}
