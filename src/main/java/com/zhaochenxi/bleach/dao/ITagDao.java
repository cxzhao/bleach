package com.zhaochenxi.bleach.dao;

import java.util.List;
import java.util.Map;

import com.zhaochenxi.bleach.model.Tag;

public interface ITagDao {
	public boolean save(Tag tag);
	public boolean saveBatch(List<Tag> list);
	//单个加1
	public boolean updateCount(String id);
	//批量加1
	public boolean updateCountBatch(List<String> list);
	public List<Tag> queryTagByType(int type);
	public Tag queryById(String id);
	public Tag queryByName(String tagName);
	public List<Tag> queryByTypeList(Map<String,Object> map);
	public List<Tag> queryOrderDescByType(int type,int start,int size);
}
