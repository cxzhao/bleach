package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.CartoonTypeRelation;

public interface ICartoonTypeRelationDao {

	public boolean save(CartoonTypeRelation cartoonTypeRelation);
	public boolean delete(String cartoonId);
	public int deleteByTypeId(String typeId);
	public boolean saveBatch(List<CartoonTypeRelation> list);
}
