package com.zhaochenxi.bleach.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.model.Recommend;
import com.zhaochenxi.bleach.service.IRecommendService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping("/recommend")
public class RecommendController {

	@Autowired
	private IRecommendService recommendService;
	
	/**
	 * @Title: userRecommend 
	 * @Description: 用户推荐接口 
	 * @param 
	 * @return Result<Object> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="userRecommend",method=RequestMethod.POST)
	public Result<Object> userRecommend(String userId,String cartoonName,String reason){		
		if(Utils.isEmptyOrNull(cartoonName)||Utils.isEmptyOrNull(reason)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}else{
			return recommendService.recommend(userId, cartoonName, reason);
		}	
	}
	
	/**
	 * @Title: agree 
	 * @Description: 同意这个推荐 
	 * @param 
	 * @return Result<Map<String,Integer>> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="agree",method=RequestMethod.POST)
	public Result<Map<String,Integer>> agree(String id,String userId){
		return recommendService.agree(id,userId);
	}
	
	/**
	 * @Title: query 
	 * @Description: 查询推荐内容 
	 * @param  
	 * @return Result<Page<Recommend>> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="query",method=RequestMethod.GET)
	public Result<Page<Recommend>> query(@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20")int pageSize,
			@RequestParam(value="orderRule",defaultValue="0")int orderRule){
		if(pageNumber<0||(orderRule<0&&orderRule>2)){
			return new Result<Page<Recommend>>(CodeEnum.PARAM_ERROR);
		}
		return recommendService.paginationQuery(pageNumber, pageSize, orderRule);		
	}
	
	@RequestMapping(value="adminQuery",method=RequestMethod.GET)
	public Result<Page<Recommend>> adminQuery(@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20")int pageSize){
		if(pageNumber<0){
			return new Result<Page<Recommend>>(CodeEnum.PARAM_ERROR);
		}
		return recommendService.paginationAdminQuery(pageNumber, pageSize);		
	}
	
	/**
	 * @Title: check 
	 * @Description: 检查推荐内容是否合法 
	 * @param 
	 * @return Result<Object> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="check",method=RequestMethod.POST)
	public Result<Object> check(String id,int status){
		if(Utils.isEmptyOrNull(id)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		return recommendService.check(id, status);
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public Result<Object> delete(String id,@RequestParam(value="status",defaultValue="0")int status){
		if(status==0){
			return new Result<Object>(CodeEnum.FAILURE);
		}
		if(Utils.isEmptyOrNull(id)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		return recommendService.delete(id);
	}
}
