package com.zhaochenxi.bleach.model;

import java.io.Serializable;
/**
 * @ClassName: Message 
 * @Description: 消息留言
 * @author zhaochenxi
 * @date 2016年12月17日 下午8:57:26
 */
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1692705551402279687L;

	private String id;
	private String context;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	
}
