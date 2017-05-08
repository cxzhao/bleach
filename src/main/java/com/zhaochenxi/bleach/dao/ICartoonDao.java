package com.zhaochenxi.bleach.dao;

import java.util.List;
import java.util.Map;

import com.zhaochenxi.bleach.model.Cartoon;

public interface ICartoonDao {
	public boolean save(Cartoon cartoon);
	public Cartoon queryDetailById(String id);
	public boolean delete(Cartoon cartoon);
	public boolean update(Cartoon cartoon);
	public boolean updateLove(int loveCount,String cartoonId);
	public boolean updateCommentCount(int commentCount,String cartoonId);
	public boolean updateComment(String cartoonId);
	public boolean decreaseComment(String id);
	public boolean updateScore(float score,String cartoonId);
	public boolean updateThemeImage(String path,String cartoonId);
	public Cartoon queryById(String id);
	public Cartoon queryLoveById(String id);
	public Cartoon queryScoreById(String id);
	public Cartoon queryCommentCountById(String id);
	
	public List<Cartoon> queryPageList(int start,int size);
	public List<Cartoon> queryByKeyword(String keyword,int start,int size);
	public List<Cartoon> queryByType(Map<String,Object> map);
	public List<Cartoon> queryByTypeAndArea(Map<String,Object> map);
	public List<Cartoon> queryByTypeAndIsEnd(String typeId,int isEnd,int start,int size);
	public List<Cartoon> queryByTypeAndIsEndAndArea(Map<String,Object> map);
	public List<Cartoon> queryByArea(Map<String,Object> map);
	public List<Cartoon> queryByIsEnd(int isEnd,int start,int size);
	public List<Cartoon> queryByAreaAndIsEnd(Map<String,Object> map);
}
