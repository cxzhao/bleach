package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.CartoonType;

public interface ICartoonTypeDao {
	public boolean save(CartoonType cartoonType);
	public boolean delete(String id);
	public List<CartoonType> queryAll();
	public List<CartoonType> queryTypeByCartoonId(String cartoonId);	
	
}
