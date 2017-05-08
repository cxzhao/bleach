package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class CartoonType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1719608600730053569L;
	
	private String id;
	private String typeName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
