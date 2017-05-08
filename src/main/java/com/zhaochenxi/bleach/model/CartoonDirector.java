package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class CartoonDirector implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809616407272854834L;
	
	private String cartoonId;
	private String directorName;
	public String getCartoonId() {
		return cartoonId;
	}
	public void setCartoonId(String cartoonId) {
		this.cartoonId = cartoonId;
	}
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

}
