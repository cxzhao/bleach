package com.zhaochenxi.bleach.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaochenxi.bleach.dao.ICartoonTypeDao;
import com.zhaochenxi.bleach.dao.ICartoonTypeRelationDao;
import com.zhaochenxi.bleach.model.CartoonType;
import com.zhaochenxi.bleach.service.ICartoonTypeService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Result;
@Service
public class CartoonTypeServiceImpl implements ICartoonTypeService{

	@Autowired
	private ICartoonTypeDao cartoonTypeDao;
		
	@Autowired
	private ICartoonTypeRelationDao cartoonTypeRelationDao;
	
	@Override
	public boolean save(CartoonType cartoonType) {
		return cartoonTypeDao.save(cartoonType);
	}

	@Override
	public boolean delete(CartoonType t) {		
		return cartoonTypeDao.delete(t.getId());
	}

	@Override
	public boolean update(CartoonType t) {
		return false;
	}

	@Override
	public List<CartoonType> queryAll() {
		return cartoonTypeDao.queryAll();
	}

	@Override
	public List<CartoonType> queryTypeByCartoonId(String cartoonId) {
		return cartoonTypeDao.queryTypeByCartoonId(cartoonId);
	}

	@Transactional
	public Result<Object> deleteType(String typeId){
		int deletecount=cartoonTypeRelationDao.deleteByTypeId(typeId);
		CartoonType cartoonType = new CartoonType();
		cartoonType.setId(typeId);
		Map<String,Object> map= new HashMap<String,Object>();
		if(delete(cartoonType)){	
			map.put("deleteRelationCount", deletecount);
			return new Result<Object>(CodeEnum.SUCCESS,map);
		}else{
			map.put("deleteRelationCount", 0);
			return new Result<Object>(CodeEnum.SQLERROR,map);
		}
		
		
	}
}
