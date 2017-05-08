package com.zhaochenxi.bleach.service;

import java.util.Map;

import com.zhaochenxi.bleach.model.Recommend;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface IRecommendService {
	public Result<Object> delete(String id);
	public Result<Object> recommend(String userId,String cartoonName,String reason);
	public Result<Map<String,Integer>> agree(String id,String userId);
	public Result<Page<Recommend>> paginationQuery(int pageNumber,int pageSize,int orderRule);
	public Result<Page<Recommend>> paginationAdminQuery(int pageNumber,int pageSize);
	public Result<Object> check(String id,int status);
	public Result<Recommend> detail(String id);
}
