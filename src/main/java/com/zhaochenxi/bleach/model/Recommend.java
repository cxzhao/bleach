package com.zhaochenxi.bleach.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Recommend 
 * @Description: 用户推荐动漫 
 * @author zhaochenxi
 * @date 2016年10月30日 下午4:15:50
 */
public class Recommend implements Serializable{
	
	private static final long serialVersionUID = 6495587919556958245L;
	
	private String id;
	private String cartoonName;
	private String reason;
	private String userName;
	private String userId;
	private Date createTime;
	private int status;
	private int agree;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCartoonName() {
		return cartoonName;
	}
	public void setCartoonName(String cartoonName) {
		this.cartoonName = cartoonName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
 
	
}
