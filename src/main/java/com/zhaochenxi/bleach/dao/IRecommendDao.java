package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.Recommend;

public interface IRecommendDao{
	
	public boolean save(Recommend recommend);
	public boolean delete(String id);
	public int rowCount();
	public int rowCountActive();
	public int queryAgreeById(String id);
	public boolean updateStatus(String id,int status);
	public boolean updateAgree(String id,int agree);
	public List<Recommend> queryAllOrderByTime(int start,int size);
	public List<Recommend> queryOrderByTime(int start,int size);
	public List<Recommend> queryOrderByAgree(int start,int size);
}
