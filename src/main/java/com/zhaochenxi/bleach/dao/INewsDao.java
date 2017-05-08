package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.News;

public interface INewsDao {
	public boolean save(News news);
	public boolean update(News news);
	public boolean updateImage(String id, String image);
	public boolean delete(String id);
	public News queryById(String id);
	public News queryNewsById(String id);
	public int rowCountByKeyword(String keyword,int status);
	public int rowCountByType(int type,int status);
	public int rowCountByTag(String tagId,int status);
	public List<News> queryByKeyword(String keyword,int start,int size,int status);
	public List<News> queryByType(int type,int start,int size,int status);
	public List<News> queryByTag(String tagId,int start,int size,int status);
	public List<News> queryByTypeAndTag(String tagId,int type,int start,int size,int status);
	public int rowCountByTypeAndTag(String tagId,int type,int status);
	public List<News> queryByTypeAndKey(int type,String keyword,int start,int size,int status);
	public int rowCountByTypeAndKey(int type,String keyword,int status);
	public boolean updateReadCount(String id,int readCount);
	public boolean updateCommentCount(String id);
	public boolean decreaseCommentCount(String id);
}
