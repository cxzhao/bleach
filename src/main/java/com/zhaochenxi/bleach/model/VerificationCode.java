package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class VerificationCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2254943374152014836L;
	
	private String id;
	private String userId;
	private String email;
	private int code;
	private long createTime;
	private int type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
