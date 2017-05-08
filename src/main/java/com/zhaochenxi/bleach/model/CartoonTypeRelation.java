package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class CartoonTypeRelation  implements Serializable{

	private static final long serialVersionUID = 1131975048606008944L;
	private String cartoonId;
	private String typeId;
	public String getCartoonId() {
		return cartoonId;
	}
	public void setCartoonId(String cartoonId) {
		this.cartoonId = cartoonId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	
}
