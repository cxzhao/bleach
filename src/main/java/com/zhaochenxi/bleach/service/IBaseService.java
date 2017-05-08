package com.zhaochenxi.bleach.service;

import java.util.List;

public interface IBaseService<T>{

	public boolean save(T t);
	public boolean delete(T t);
	public boolean update(T t);
	public List<T> queryAll();

}
