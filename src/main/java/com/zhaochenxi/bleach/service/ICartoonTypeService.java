package com.zhaochenxi.bleach.service;

import java.util.List;
import com.zhaochenxi.bleach.model.CartoonType;
import com.zhaochenxi.bleach.utils.Result;

public interface ICartoonTypeService extends IBaseService<CartoonType>{
	public List<CartoonType> queryTypeByCartoonId(String cartoonId);
	public Result<Object> deleteType(String typeId);
}
