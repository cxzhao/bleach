package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.CartoonRole;

public interface ICartoonRoleDao {
	public boolean save(CartoonRole role);
	public boolean delete(String cartoonId);
	public boolean saveBatch(List<CartoonRole>list);
}
