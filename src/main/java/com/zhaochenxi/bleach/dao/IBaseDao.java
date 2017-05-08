package com.zhaochenxi.bleach.dao;

public interface IBaseDao<T>{
	public boolean save(T t);
	public boolean delete(T t);
	public boolean update(T t);
}
