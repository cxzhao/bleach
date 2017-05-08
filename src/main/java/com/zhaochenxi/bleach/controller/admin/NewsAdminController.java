package com.zhaochenxi.bleach.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zhaochenxi.bleach.form.NewsForm;
import com.zhaochenxi.bleach.model.News;
import com.zhaochenxi.bleach.service.INewsService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@RestController
@RequestMapping(value = "/newsAdmin")
public class NewsAdminController {

	@Autowired
	private INewsService newsService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Result<Object> add(@ModelAttribute NewsForm newsform, String tag) {
		News news = new News();
		if (newsform != null) {
			news.setContext(newsform.getContext());
			news.setIntroduction(newsform.getIntroduction());
			news.setNewsImage(newsform.getNewsImage());
			news.setTitle(newsform.getTitle());
			news.setKeyword(newsform.getKeyword());
			news.setSource(newsform.getSource());
			news.setStatus(newsform.getStatus());
			news.setType(newsform.getType());
		}
		return newsService.save(news, tag);
	}

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
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Result<Object> update(@ModelAttribute NewsForm newsform, String tag) {
		News news = new News();
		if (newsform != null) {
			news.setId(newsform.getId());
			news.setContext(newsform.getContext());
			news.setIntroduction(newsform.getIntroduction());
			news.setNewsImage(newsform.getNewsImage());
			news.setTitle(newsform.getTitle());
			news.setKeyword(newsform.getKeyword());
			news.setSource(newsform.getSource());
			news.setStatus(newsform.getStatus());
			news.setType(newsform.getType());
		}
		return newsService.update(news, tag);
	}

	@RequestMapping(value = "updateReadCount", method = RequestMethod.POST)
	public Result<News> updateReadCount(String id,@RequestParam(value="readCount",defaultValue="1")int readCount){
		return newsService.updateCountRead(id, readCount);
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public Result<News> delete(String id){
		if (Utils.isEmptyOrNull(id)) {
			return new Result<News>(CodeEnum.PARAM_ERROR);
		}
		return newsService.delete(id);
	}
}
