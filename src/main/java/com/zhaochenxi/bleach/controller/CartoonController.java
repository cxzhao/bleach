package com.zhaochenxi.bleach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zhaochenxi.bleach.dto.CartoonDto;
import com.zhaochenxi.bleach.enums.CountryEnum;
import com.zhaochenxi.bleach.model.Cartoon;
import com.zhaochenxi.bleach.service.ICartoonService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping("/cartoon")
public class CartoonController {
	
	@Autowired
	private ICartoonService cartoonService;
	
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public Result<Page<CartoonDto>> query(
			@RequestParam(value="isEnd",defaultValue="-1")String isEnd,
			@RequestParam(value="area",defaultValue="")String area,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="keyword",required=false)String keyword,
			@RequestParam(value="pageNumber",defaultValue="1",required=false)int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20",required=false)int pageSize){
		if(pageNumber<=0||pageSize<=0){
			return new Result<Page<CartoonDto>>(CodeEnum.PARAM_ERROR,null);
		}
		//判断查询的是完结还是更新中的动漫
		int end = -1;
		String [] array = isEnd.split("\\,");
		if(array.length==2){
			end = -1;
		}else if(array.length==1){
			end  = Integer.parseInt(isEnd);
		}
		
		return cartoonService.paginationQuery(pageNumber, pageSize, type, keyword,end,CountryEnum.getCountryName(area));
	}
	
	@RequestMapping(value="/love",method=RequestMethod.POST)
	public Result<Object> love(String cartoonId,String userId){
		if(Utils.isEmptyOrNull(cartoonId)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		return cartoonService.updateLove(cartoonId,userId);
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public Result<Cartoon> detail(String cartoonId){
		if(Utils.isEmptyOrNull(cartoonId)){
			return new Result<Cartoon>(CodeEnum.PARAMETERSNULL);
		}		
		return cartoonService.queryCartoonDeatail(cartoonId);
	}
	
}
