package com.zhaochenxi.bleach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.model.News;
import com.zhaochenxi.bleach.service.INewsService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping(value = "/news")
public class NewsController {

	@Autowired
	private INewsService newsService;
	
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public Result<Page<News>> query(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			@RequestParam(value = "type", defaultValue = "-1") int type, String keyword, String tagId,@RequestParam(value = "status", defaultValue = "0")int status) {
		if (pageNumber <= 0 || pageSize <= 0) {
			return new Result<Page<News>>(CodeEnum.PARAM_ERROR);
		}
		return newsService.paginationQuery(pageNumber, pageSize, type, keyword, tagId,status);
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public Result<News> queryDetail(String id){
		if(Utils.isEmptyOrNull(id)){
			return new Result<News>(CodeEnum.PARAMETERSNULL);
		}
		return newsService.queryDetail(id);
	}
	
}
