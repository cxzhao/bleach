package com.zhaochenxi.bleach.dao;

import java.util.Date;
import java.util.List;

import com.zhaochenxi.bleach.model.Article;
/**
 * @ClassName: IArticleDao 
 * @Description: 文章
 * @author zhaochenxi
 * @date 2016年12月12日 下午5:29:46
 */
public interface IArticleDao {
	public boolean save(Article article);
	public boolean update(Article article);
	public boolean updateReadCount(String id);
	public boolean delete(String id,Date updateTime);
	public boolean setReadCount(String id,int count);
	public boolean updateCommentCount(String id);
	public boolean decreaseCommentCount(String id);
	public Article queryById(String id);
	public List<Article> selectListByReadCount(int start,int size);
	public List<Article> selectList(int start,int size,int status);
	public List<Article> selectByTag(int start,int size,String tagId,int status);
	public List<Article> selectByKeyword(int start,int size,String keyword,int status);	
	public List<Article> selectListByUserId(int start,int size,int status,String userId);
	public List<Article> selectByKeywordAndUserId(int start,int size,String keyword,int status,String userId);
	
}
