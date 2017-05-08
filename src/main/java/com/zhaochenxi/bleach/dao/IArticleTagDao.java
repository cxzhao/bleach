package com.zhaochenxi.bleach.dao;

import java.util.List;
import java.util.Map;

import com.zhaochenxi.bleach.model.ArticleTag;

public interface IArticleTagDao {
	public boolean save(ArticleTag articleTag);
	public boolean saveBatch(List<ArticleTag> list);
	public boolean deleteByArticleId(String articleId);
	public boolean deleteByTagId(Map<String,Object> map);
}
