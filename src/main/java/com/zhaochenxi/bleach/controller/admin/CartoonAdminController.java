package com.zhaochenxi.bleach.controller.admin;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.enums.CountryEnum;
import com.zhaochenxi.bleach.form.CartoonForm;
import com.zhaochenxi.bleach.model.Cartoon;
import com.zhaochenxi.bleach.service.ICartoonService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Result;

@RestController
@RequestMapping("/cartoonAdmin")
public class CartoonAdminController {

	@Autowired
	private ICartoonService cartoonAdminService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(@ModelAttribute CartoonForm cartoon) {
		Cartoon c = new Cartoon();
		c.setId(IdCreater.createId17());
		if(cartoon!=null){
			c.setName(cartoon.getName());
			c.setCommentCount(0);
			c.setCountry(cartoon.getCountry());
			c.setCreateTime(new Date());
			c.setEnglishName(cartoon.getEnglishName());
			c.setIntroduction(cartoon.getIntroduction());
			c.setKeyword(cartoon.getKeyword());
			c.setLanguage(cartoon.getLanguage());
			c.setLoveCount(cartoon.getLoveCount());
			c.setScore(cartoon.getScore());
			c.setShortIntro(cartoon.getShortIntro());
			c.setThemeImage(cartoon.getThemeImage());
			c.setYears(cartoon.getYears());
			c.setIsEnd(cartoon.getIsEnd());
			c.setAuthor(cartoon.getAuthor());
		}
		return cartoonAdminService.add(c, cartoon.getType(), cartoon.getRole(), cartoon.getDirector(), cartoon.getAkira());
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(@ModelAttribute CartoonForm cartoon){
		Cartoon c = new Cartoon();		
		if(cartoon!=null){
			c.setId(cartoon.getId());
			c.setName(cartoon.getName());
			c.setCommentCount(cartoon.getCommentCount());
			c.setCountry(cartoon.getCountry());
			c.setCreateTime(new Date());
			c.setEnglishName(cartoon.getEnglishName());
			c.setIntroduction(cartoon.getIntroduction());
			c.setKeyword(cartoon.getKeyword());
			c.setLanguage(cartoon.getLanguage());
			c.setLoveCount(cartoon.getLoveCount());
			c.setScore(cartoon.getScore());
			c.setShortIntro(cartoon.getShortIntro());
			c.setThemeImage(cartoon.getThemeImage());
			c.setYears(cartoon.getYears());
			c.setIsEnd(cartoon.getIsEnd());
			c.setAuthor(cartoon.getAuthor());
		}
		return cartoonAdminService.updateCartoon(c, cartoon.getType(), cartoon.getRole(), cartoon.getDirector(), cartoon.getAkira());		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(String id){
		return cartoonAdminService.deleteCartoon(id);		
	}
	
	@RequestMapping(value = "/cartoonDetail", method = RequestMethod.GET)
	public Result<Cartoon> queryDetailById(String id){
		return cartoonAdminService.queryCartoonDeatail(id);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryCartoonList", method = RequestMethod.GET)
	public Result paginationQuery(int pageNumber,int pageSize,String type,String keyword,@RequestParam(value="isEnd",defaultValue="-1")int isEnd,String area){
		if(pageNumber<=0||pageSize<=0){
			return new Result(CodeEnum.PARAM_ERROR);
		}
		return cartoonAdminService.paginationQuery(pageNumber, pageSize, type, keyword,isEnd,CountryEnum.getCountryName(area));
	}
	
	@RequestMapping(value = "/updateScore", method = RequestMethod.POST)
	public Result<Object> updateScore(float score,String id){
		return cartoonAdminService.adminUpdateScore(score, id);
	}
	
	@RequestMapping(value = "/updateLove", method = RequestMethod.POST)
	public Result<Object> updateLove(int love,String id){
		return cartoonAdminService.adminUpdateLove(love, id);
	}
	
}
