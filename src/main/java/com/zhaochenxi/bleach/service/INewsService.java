package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.model.News;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface INewsService {
	public Result<Object> save(News news,String tag);
	public Result<News> delete(String id);
	public Result<Object> update(News news,String tag);
	public Result<Page<News>>paginationQuery(int pageNumber,int pageSize,int type,String keyword,String tag,int status);
	public Result<News> queryDetail(String id);
	public Result<News> updateCountRead(String id,int readCount);
	public Result<News> updateImages(String id,String imagePath);
}
