package com.zhaochenxi.bleach.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaochenxi.bleach.dao.INewsDao;
import com.zhaochenxi.bleach.dao.INewsTagDao;
import com.zhaochenxi.bleach.dao.ITagDao;
import com.zhaochenxi.bleach.enums.SaveTypeEnum;
import com.zhaochenxi.bleach.enums.TagTypeEnum;
import com.zhaochenxi.bleach.model.News;
import com.zhaochenxi.bleach.model.NewsTag;
import com.zhaochenxi.bleach.model.Tag;
import com.zhaochenxi.bleach.service.INewsService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@Service
public class NewsServiceImpl implements INewsService{
	
	@Autowired
	private ITagDao tagDao;
	
	@Autowired
	private INewsTagDao newsTagDao;
	
	@Autowired
	private INewsDao newsDao;
	
	/**
	 * @Title: save 
	 * @Description: 保存新闻 
	 * @param 
	 * @return boolean 
	 * @throws 
	 * @author zhaochenxi
	 */
	@Override
	@Transactional
	public Result<Object> save(News news, String tag) {
		if(news==null){
			return new Result<Object>(CodeEnum.FAILURE);
		}
		news.setId(IdCreater.createId17());
		news.setCreateTime(new Date());
		newsDao.save(news);
		if(!Utils.isEmptyOrNull(tag)){
			tag = tag.trim();
			String [] tagArray = tag.split("\\,");
			List<String> list = new ArrayList<String>();
			for(String s:tagArray){
				list.add(s);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", TagTypeEnum.news.getValue());
			map.put("list", list);
			List<Tag> tagList = tagDao.queryByTypeList(map);
			List<String> insertTagList = new ArrayList<String>();
			List<String> oldTagList = new ArrayList<String>();
			if(tagList!=null&&tagList.size()!=0){
				
				for(String tagStr:tagArray){
					if(Utils.isEmptyOrNull(tagStr)){continue;}
					for(int i=0;i<tagList.size();i++){
						if(tagStr.equals(tagList.get(i).getTagName())){							
							oldTagList.add(tagList.get(i).getId());
							break;
						}else{
							if(i==tagList.size()-1){
								insertTagList.add(tagStr);
							}
						}
					}
					
				}
			}else{
				for(String tagStr:tagArray){
					if(Utils.isEmptyOrNull(tagStr)){continue;}
					insertTagList.add(tagStr);
				}
			}
			tagList = new ArrayList<Tag>();
			List<NewsTag> newsTagList = new ArrayList<NewsTag>();
			for(String s:insertTagList){
				Tag t = new Tag();
				t.setId(IdCreater.createId());
				t.setTagName(s);
				t.setType(TagTypeEnum.news.getValue());
				tagList.add(t);
				
				NewsTag nt = new NewsTag();
				nt.setNewsId(news.getId());
				nt.setTagId(t.getId());
				newsTagList.add(nt);
			}
			
			
			if(tagList.size()!=0){
				for(String s:oldTagList){
					NewsTag nt = new NewsTag();
					nt.setNewsId(news.getId());
					nt.setTagId(s);
					newsTagList.add(nt);
				}
				//插入新增加标签
				tagDao.saveBatch(tagList);	
				//插入关联关系
				newsTagDao.saveBatch(newsTagList);
			}
			
		}
		return new Result<Object>(CodeEnum.SUCCESS);
	}

	@Override
	public Result<Page<News>> paginationQuery(int pageNumber, int pageSize, int type, String keyword, String tag,int status) {
		int start = (pageNumber-1)*pageSize;
		int count = 0;
		List<News> list = null;
		if(type!=-1&&Utils.isEmptyOrNull(keyword)&&Utils.isEmptyOrNull(tag)){
			list = newsDao.queryByType(type, start, pageSize,status);
			count = newsDao.rowCountByType(type,status);
		}
		if(type!=-1&&!Utils.isEmptyOrNull(keyword)&&Utils.isEmptyOrNull(tag)){
			list = newsDao.queryByTypeAndKey(type,keyword,start, pageSize,status);
			count = newsDao.rowCountByTypeAndKey(type,keyword,status);
		}
		
		if(!Utils.isEmptyOrNull(keyword)&&type==-1&&Utils.isEmptyOrNull(tag)){
			list = newsDao.queryByKeyword(keyword, start, pageSize,status);
			count = newsDao.rowCountByKeyword(keyword,status);
		}
		if(Utils.isEmptyOrNull(keyword)&&type==-1&&!Utils.isEmptyOrNull(tag)){
			list = newsDao.queryByTag(tag, start, pageSize,status);
			count = newsDao.rowCountByTag(tag,status);
		}
		if((type!=-1&&!Utils.isEmptyOrNull(tag))){
			list = newsDao.queryByTypeAndTag(tag, type, start, pageSize,status);
			count = newsDao.rowCountByTypeAndTag(tag, type,status);
		}
		Page<News> page = new Page<News>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setList(list);
		if((count%pageSize)==0){
			page.setTotal(count/pageSize);
		}else{
			page.setTotal(count/pageSize+1);
		}
		return new Result<Page<News>>(CodeEnum.SUCCESS, page);
	}

	/**
	 * 查询数据详情
	 */
	@Override
	public Result<News> queryDetail(String id) {
		if(id==null){
			return new Result<News>(CodeEnum.FAILURE);
		}		
		News news = newsDao.queryById(id);
		newsDao.updateReadCount(id, news.getReadCount()+1);
		List<Tag> tagList = news.getTagList();
		if(tagList!=null){
			List<String> tagIdList = new ArrayList<String>();
			for(Tag t : tagList){
				tagIdList.add(t.getId());
			}
			tagDao.updateCountBatch(tagIdList);
		}
		return new Result<News>(CodeEnum.SUCCESS,news);
	}

	@Override
	@Transactional
	public Result<Object> update(News news, String tag) {
		if(!Utils.isEmptyOrNull(tag)){
			tag = tag.trim();
			String [] tagArray = tag.split("\\,");
			List<String> list = new ArrayList<String>();
			for(String s:tagArray){
				list.add(s);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", TagTypeEnum.news.getValue());
			map.put("list", list);
			List<Tag> tagList = tagDao.queryByTypeList(map);
			List<String> insertTagList = new ArrayList<String>();
			List<String> oldTagList = new ArrayList<String>();
			if(tagList!=null&&tagList.size()!=0){					
				for(String tagStr:tagArray){
					if(Utils.isEmptyOrNull(tagStr)){continue;}
					for(int i=0;i<tagList.size();i++){
						if(tagStr.equals(tagList.get(i).getTagName())){								
							oldTagList.add(tagList.get(i).getId());
							break;
						}else{
							if(i==(tagList.size()-1)){
								insertTagList.add(tagStr);
							}
						}
					}
					
				}
			}else{
				for(String tagStr:tagArray){
					if(Utils.isEmptyOrNull(tagStr)){continue;}
					insertTagList.add(tagStr);
				}
			}
			tagList = new ArrayList<Tag>();
			List<NewsTag> newsTagList = new ArrayList<NewsTag>();
			for(String s:insertTagList){
				Tag t = new Tag();
				t.setId(IdCreater.createId());
				t.setTagName(s);
				t.setType(TagTypeEnum.news.getValue());
				tagList.add(t);
				
				NewsTag nt = new NewsTag();
				nt.setNewsId(news.getId());
				nt.setTagId(t.getId());
				newsTagList.add(nt);
			}
			
			if(tagList.size()!=0){
				for(String s:oldTagList){
					NewsTag nt = new NewsTag();
					nt.setNewsId(news.getId());
					nt.setTagId(s);
					newsTagList.add(nt);
				}
				//插入新增加标签
				tagDao.saveBatch(tagList);
				//插入关联关系
				newsTagDao.deleteByNewsId(news.getId());
				newsTagDao.saveBatch(newsTagList);
			}
			newsDao.update(news);
			return new Result<Object>(CodeEnum.SUCCESS);
		}
		return new Result<Object>(CodeEnum.FAILURE);
	}

	@Override
	public Result<News> updateCountRead(String id, int readCount) {
		if(Utils.isEmptyOrNull(id)||readCount<=0){
			return new Result<News>(CodeEnum.PARAM_ERROR);
		}	
		if(newsDao.updateReadCount(id, readCount)){
			return new Result<News>(CodeEnum.SUCCESS);
		}else{
			return new Result<News>(CodeEnum.FAILURE);
		}
	}

	/**
	 * 删除草稿新闻，已经发布的不可以删除
	 */
	@Override
	@Transactional
	public Result<News> delete(String id) {
		News news = newsDao.queryNewsById(id);
		if(news==null){
			return new Result<News>(CodeEnum.FAILURE);
		}else{
			if(news.getStatus()==SaveTypeEnum.publish.getValue()){
				return new Result<News>(CodeEnum.CAN_NOT_DELETE);
			}else{
				newsDao.delete(id);
				newsTagDao.deleteByNewsId(id);
				return new Result<News>(CodeEnum.SUCCESS);
			}
		}
	}

	@Override
	public Result<News> updateImages(String id, String imagePath) {
		if(Utils.isEmptyOrNull(id)||Utils.isEmptyOrNull(imagePath)){
			return new Result<News>(CodeEnum.PARAMETERSNULL);
		}
		if(newsDao.updateImage(id, imagePath)){
			return new Result<News>(CodeEnum.SUCCESS);
		}
		
		return new Result<News>(CodeEnum.FAILURE);
	}
	
}
