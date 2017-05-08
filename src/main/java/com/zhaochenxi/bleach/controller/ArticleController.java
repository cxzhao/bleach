package com.zhaochenxi.bleach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.aop.annotation.Performance;
import com.zhaochenxi.bleach.model.Article;
import com.zhaochenxi.bleach.service.IArticleService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService commentService;
	
	/**
	 * @Title: detail 
	 * @Description: 查询动漫详情 
	 * @param 
	 * @return Result<Comment> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="detail",method=RequestMethod.GET)
	public Result<Article> detail(String id){
		return commentService.detail(id);
	}
	
	@CacheEvict("ArticleCache")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Result<Article> add(@ModelAttribute Article comment,String tag){
		return commentService.save(comment, tag);
	}
	
		
	@Cacheable("ArticleCache")
	@RequestMapping(value="query",method=RequestMethod.GET)
	public Result<Page<Article>> query(
			@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20")int pageSize,
			@RequestParam(value="keyword",defaultValue="")String keyword,
			@RequestParam(value="tagId",defaultValue="")String tagId,
			@RequestParam(value="status",defaultValue="1")int status){
		
		return commentService.paginationQuery(pageNumber, pageSize, keyword, tagId, null,status);
	}
	
	/**
	 * @Title: hot 
	 * @Description: TODO 
	 * @param  查询阅读量从大到小的文章，后期修改为查询一周中访问量最大的文章,查询结果保存到缓存中
	 * @return Result<Page<Article>> 
	 * @throws 
	 * @author zhaochenxi
	 */	
	@Cacheable("hotArticleCache")
	@RequestMapping(value="hot",method=RequestMethod.GET)
	public Result<Page<Article>> hot(
			@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10")int pageSize){
				return commentService.paginationQueryHot(pageNumber, pageSize);		
	}
	
	@RequestMapping(value="myComment",method=RequestMethod.GET)
	public Result<Page<Article>> myComment(
			@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20")int pageSize,
			@RequestParam(value="keyword",defaultValue="")String keyword,
			@RequestParam(value="userId",defaultValue="")String userId,
			@RequestParam(value="tagId",defaultValue="")String tagId,
			@RequestParam(value="status",defaultValue="1")int status){
		
		return commentService.paginationQuery(pageNumber, pageSize, keyword, tagId, userId,status);
	}
	
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Result<Article> update(
			@RequestParam(value="userId",required=true)String userId,
			@RequestParam(value="id",required=true)String id,
			@RequestParam(value="title",required=true)String title,
			@RequestParam(value="tag",required=true)String tag,
			@RequestParam(value="introduction",required=true)String introduction,
			@RequestParam(value="context",required=true)String context,
			@RequestParam(value="status",defaultValue="1")int status){
		if(Utils.isEmptyOrNull(userId)||Utils.isEmptyOrNull(id)||Utils.isEmptyOrNull(title)||Utils.isEmptyOrNull(tag)){
			return new Result<Article>(CodeEnum.PARAMETERSNULL);
		}
		return commentService.update(userId, id, title, tag, introduction, context, status);		
	}
	
	
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public Result<Article> delete(
			@RequestParam(value="id",required=true)String id){
		if(Utils.isEmptyOrNull(id)){
			return new Result<Article>(CodeEnum.PARAMETERSNULL);
		}
		return commentService.delete(id);
	}
}
