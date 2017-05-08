package com.zhaochenxi.bleach.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaochenxi.bleach.aop.annotation.Performance;
import com.zhaochenxi.bleach.dao.IArticleDao;
import com.zhaochenxi.bleach.dao.IArticleTagDao;
import com.zhaochenxi.bleach.dao.ITagDao;
import com.zhaochenxi.bleach.enums.TagTypeEnum;
import com.zhaochenxi.bleach.model.Article;
import com.zhaochenxi.bleach.model.ArticleTag;
import com.zhaochenxi.bleach.model.Tag;
import com.zhaochenxi.bleach.service.IArticleService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@Service
public class ArticleServiceImpl implements IArticleService{

	@Autowired
	private IArticleDao commentDao;
	
	@Autowired
	private ITagDao tagDao;
	
	@Autowired
	private IArticleTagDao comTagDao;
	
	@Override
	public Result<Article> save(Article comment,String tag) {
		if(comment==null||Utils.isEmptyOrNull(tag)){
			return new Result<Article>(CodeEnum.PARAMETERSNULL);
		}
		comment.setId(IdCreater.createId17());
		comment.setCreateTime(new Date());
		comment.setUpdateTime(new Date());
		if(Utils.isEmptyOrNull(comment.getTitle())){
			comment.setKeyword(tag.trim());
		}else{
			comment.setKeyword(comment.getTitle()+tag.trim());
		}
		comment.setReadCount(0);
		comment.setCommentCount(0);
		
		if(!commentDao.save(comment)){
			return new Result<Article>(CodeEnum.FAILURE);
		}
		
		String [] tagArray = tag.split(" ");
		List<String> list = new ArrayList<String>();
		for(String s:tagArray){
			list.add(s);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", TagTypeEnum.comment.getValue());
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
		List<ArticleTag> comTagList = new ArrayList<ArticleTag>();
		for(String s:insertTagList){
			Tag t = new Tag();
			t.setId(IdCreater.createId());
			t.setTagName(s);
			t.setType(TagTypeEnum.comment.getValue());
			tagList.add(t);
			
			ArticleTag ct = new ArticleTag();
			ct.setArticleId(comment.getId());
			ct.setTagId(t.getId());
			comTagList.add(ct);
		}
		for(String s:oldTagList){
			ArticleTag ct = new ArticleTag();
			ct.setArticleId(comment.getId());
			ct.setTagId(s);
			comTagList.add(ct);
		}
		//插入新增加标签
		tagDao.saveBatch(tagList);	
		//插入关联关系
		comTagDao.saveBatch(comTagList);		
		return new Result<Article>(CodeEnum.SUCCESS);
	}

	@Override
	public Result<Article> detail(String id) {
		if(Utils.isEmptyOrNull(id)){
			return new Result<Article>(CodeEnum.PARAMETERSNULL);
		}
		
		Article comment = commentDao.queryById(id);
		if(comment!=null){
			List<Tag> tagList = comment.getTagList();
			if(tagList!=null){
				List<String> tagIdList = new ArrayList<String>();
				for(Tag t : tagList){
					tagIdList.add(t.getId());
				}
				tagDao.updateCountBatch(tagIdList);
			}
			commentDao.updateReadCount(id);
		}		
		return new Result<Article>(CodeEnum.SUCCESS,comment);
	}

	@Performance
	@Override
	public Result<Page<Article>> paginationQuery(int pageNumber, int pageSize, String keyword, String tagId,String userId,int status) {
		if(pageNumber<=0||pageSize<=0){
			return new Result<Page<Article>>(CodeEnum.PARAM_ERROR);
		}
		int start = (pageNumber-1)*pageSize;
		List<Article> list = null;
		if(!Utils.isEmptyOrNull(tagId)&&Utils.isEmptyOrNull(keyword)&&Utils.isEmptyOrNull(userId)){
			list=commentDao.selectByTag(start, pageSize, tagId,status);
		}
		if(Utils.isEmptyOrNull(tagId)&&!Utils.isEmptyOrNull(keyword)&&Utils.isEmptyOrNull(userId)){
			list=commentDao.selectByKeyword(start, pageSize, keyword.trim(),status);
		}

		if(Utils.isEmptyOrNull(tagId)&&Utils.isEmptyOrNull(keyword)&&Utils.isEmptyOrNull(userId)){
			list=commentDao.selectList(start, pageSize,status);
		}
		if(Utils.isEmptyOrNull(tagId)&&Utils.isEmptyOrNull(keyword)&&!Utils.isEmptyOrNull(userId)){
			list=commentDao.selectListByUserId(start, pageSize, status, userId);
		}
		if(Utils.isEmptyOrNull(tagId)&&!Utils.isEmptyOrNull(keyword)&&!Utils.isEmptyOrNull(userId)){
			list=commentDao.selectByKeywordAndUserId(start, pageSize, keyword, status, userId);
		}
		
		Page<Article> page = new Page<Article>();
		page.setList(list);
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		if(list!=null&&list.size()<pageSize){
			page.setLast(true);
		}else{
			page.setLast(false);
		}
		return new Result<Page<Article>>(CodeEnum.SUCCESS,page);
	}

	@Override
	@Transactional
	public Result<Article> update(String userId, String id, String title, String tag, String introduction,
			String context, int status) {
		Article comment = new Article();
		comment.setId(id);
		comment.setTitle(title);
		comment.setContext(context);
		comment.setStatus(status);
		comment.setKeyword(title+tag);
		comment.setUpdateTime(new Date());
		comment.setIntroduction(introduction);
				
		String [] tagArray = tag.split(" ");
		List<String> list = new ArrayList<String>();
		for(String s:tagArray){
			list.add(s);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", TagTypeEnum.comment.getValue());
		map.put("list", list);
		List<Tag> tagList = tagDao.queryByTypeList(map);
		List<String> insertTagList = new ArrayList<String>();
		List<String> oldTagList = new ArrayList<String>();
		if(tagList!=null&&tagList.size()!=0){		
			for(String tagStr:tagArray){
				if(Utils.isEmptyOrNull(tagStr)){
					continue;
				}
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
				if(Utils.isEmptyOrNull(tagStr)){
					continue;
				}
				insertTagList.add(tagStr);
			}
		}
		//如果有新插入的标签
		if(insertTagList.size()!=0){
			tagList = new ArrayList<Tag>();
			List<ArticleTag> comTagList = new ArrayList<ArticleTag>();
			for(String s:insertTagList){
				Tag t = new Tag();
				t.setId(IdCreater.createId());
				t.setTagName(s);
				t.setType(TagTypeEnum.comment.getValue());
				tagList.add(t);
				
				ArticleTag ct = new ArticleTag();
				ct.setArticleId(comment.getId());
				ct.setTagId(t.getId());
				comTagList.add(ct);
			}		
			for(String s:oldTagList){
				ArticleTag ct = new ArticleTag();
				ct.setArticleId(comment.getId());
				ct.setTagId(s);
				comTagList.add(ct);
			}
			
			//删除标签
			comTagDao.deleteByArticleId(id);
			//插入新增加标签
			tagDao.saveBatch(tagList);
			//插入关联关系
			comTagDao.saveBatch(comTagList);
		}

		if(commentDao.update(comment)){
			return new Result<Article>(CodeEnum.SUCCESS);
		}else{
			return new Result<Article>(CodeEnum.FAILURE);
		}
	}

	@Override
	public Result<Article> delete(String id) {
		if(commentDao.delete(id,new Date())){
			return new Result<Article>(CodeEnum.SUCCESS);
		}else{
			return new Result<Article>(CodeEnum.FAILURE);
		}
	}

	@Override
	public Result<Page<Article>> paginationQueryHot(int pageNumber, int pageSize) {
		if(pageNumber<=0||pageSize<=0){
			return new Result<Page<Article>>(CodeEnum.PARAM_ERROR);
		}
		int start = (pageNumber-1)*pageSize;
		List<Article> list = commentDao.selectListByReadCount(start, pageSize);
		
		Page<Article> page = new Page<Article>();
		page.setList(list);
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		if(list!=null&&list.size()<pageSize){
			page.setLast(true);
		}else{
			page.setLast(false);
		}
		return new Result<Page<Article>>(CodeEnum.SUCCESS,page);
	}

	
}
