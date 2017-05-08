package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.model.Article;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface IArticleService {
	public Result<Article> save(Article comment,String tag);
	public Result<Article> update(String userId,String id,String title,String tag,String introduction,String context,int status);
	public Result<Article> detail(String id);
	public Result<Article> delete(String id);
	public Result<Page<Article>> paginationQuery(int pageNumber,int pageSize,String keyword,String tagId,String userId,int status);
	public Result<Page<Article>> paginationQueryHot(int pageNumber,int pageSize);
}
