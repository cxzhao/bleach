package com.zhaochenxi.bleach.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.zhaochenxi.bleach.model.CartoonType;
import com.zhaochenxi.bleach.service.ICartoonTypeService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@Controller
@RequestMapping("/cartoonType")
public class CartoonTypeController {

	@Autowired
	private ICartoonTypeService cartoonTypeService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestParam(value="typeName",required=true)String typeName){
		CartoonType type = new CartoonType();
		type.setId(IdCreater.createId17());
		type.setTypeName(typeName);
		if(cartoonTypeService.save(type)){
			return new Result(CodeEnum.SUCCESS);
		}else{
			return new Result(CodeEnum.FAILURE);
		}
	}
	
	/**
	 * 查询所有的动漫类型
	 * @Title: queryAll 
	 * @Description: TODO 
	 * @param 
	 * @return Result 
	 * @throws 
	 * @author zhaochenxi
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/queryAll",method=RequestMethod.GET)
	public Result queryAll(){
		List<CartoonType> list=cartoonTypeService.queryAll();
		return new Result<List<CartoonType>>(CodeEnum.SUCCESS,list);
	}
	
	/**
	 * 删除添加的动漫类型
	 * @Title: delete 
	 * @Description: TODO 
	 * @param 
	 * @return Result 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Result<Object> delete(String typeId){
		if(Utils.isEmptyOrNull(typeId)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		return cartoonTypeService.deleteType(typeId);
	}
	
	/**
	 * @Title: queryByCartoonId 
	 * @Description: 查询某个动漫的类型 
	 * @param 
	 * @return Result<List<CartoonType>> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="/queryByCartoonId",method=RequestMethod.GET)
	public Result<List<CartoonType>> queryByCartoonId(String cartoonId){
		List<CartoonType> list=cartoonTypeService.queryTypeByCartoonId(cartoonId);

		return new Result<List<CartoonType>>(CodeEnum.SUCCESS,list);	
	}
}
