package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.NewsTag;

public interface INewsTagDao {
	public boolean save(NewsTag newsTag);
	public boolean saveBatch(List<NewsTag> list);
	public boolean deleteByNewsId(String newsId);
}
