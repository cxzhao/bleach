package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class EtCommentReply implements Serializable{

	private static final long serialVersionUID = 6861440302933279581L;
	private String id;
	private String objId;//回复的评论ID
	private String objUserName;
	private String objUserId;
	private String userId;//发表评论的人
	private String userName;
	private String context;//回复的人
	private long createTime;//时间
	private int agree;//回复数
	private int type;
	
	
	
	
	public String getObjUserName() {
		return objUserName;
	}
	public void setObjUserName(String objUserName) {
		this.objUserName = objUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjUserId() {
		return objUserId;
	}
	public void setObjUserId(String objUserId) {
		this.objUserId = objUserId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
