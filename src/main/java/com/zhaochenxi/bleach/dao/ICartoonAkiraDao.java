package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.CartoonAkira;

public interface ICartoonAkiraDao {

	public boolean save(CartoonAkira akira);
	public boolean delete(String cartoonId);
	public boolean saveBatch(List<CartoonAkira>list);
}
