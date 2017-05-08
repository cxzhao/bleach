package com.zhaochenxi.bleach.model;

import java.io.Serializable;
import java.util.List;

import com.zhaochenxi.bleach.dto.UserDto;

public class EtComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8969650788656001909L;
	private String id;
	//评论对象
	private String objectId;
	private String userId;
	private String name;
	private String context;
	private float score;
	private long createTime;
	private int agree;
	private int reply;
	private int type;
	private List<EtCommentReply> replyList;
	private UserDto user;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
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
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
	public int getReply() {
		return reply;
	}
	public void setReply(int reply) {
		this.reply = reply;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<EtCommentReply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<EtCommentReply> replyList) {
		this.replyList = replyList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	
}
