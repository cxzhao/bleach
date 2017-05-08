package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class CartoonAkira implements Serializable{

	private static final long serialVersionUID = -8844704082651486384L;

	private String cartoonId;
	private String akiraName;
	
	public String getCartoonId() {
		return cartoonId;
	}
	public void setCartoonId(String cartoonId) {
		this.cartoonId = cartoonId;
	}
	public String getAkiraName() {
		return akiraName;
	}
	public void setAkiraName(String akiraName) {
		this.akiraName = akiraName;
	}
	
	
}
