package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.CartoonDirector;

public interface ICartoonDirectorDao {
	public boolean save(CartoonDirector director);	
	public boolean delete(String cartoonId);
	public boolean saveBatch(List<CartoonDirector>list);
}
