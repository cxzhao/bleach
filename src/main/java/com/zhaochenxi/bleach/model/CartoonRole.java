package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class CartoonRole implements Serializable{

	private static final long serialVersionUID = -559432073396101097L;

	private String cartoonId;
	private String roleName;
	public String getCartoonId() {
		return cartoonId;
	}
	public void setCartoonId(String cartoonId) {
		this.cartoonId = cartoonId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
