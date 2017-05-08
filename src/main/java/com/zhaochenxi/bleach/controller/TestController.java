package com.zhaochenxi.bleach.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.test.TestBean;
import com.zhaochenxi.bleach.validator.annotation.Length;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	@RequestMapping(value="/bean",method=RequestMethod.GET)		
	public String test(@Qualifier("testBean") TestBean testBean){
		return testBean.getBeanName();
	}
	
	
	@RequestMapping(value="/length",method=RequestMethod.GET)
	public String length(@Valid @Length(min=10,max=100) String name){
		return "yes";	
	}
	
	/**
	 * @ModelAttribute BindingResult必须一起使用才可以，不可和@ResquestParam一起使用，要报错
	 * @Title: test2 
	 * @Description: TODO 
	 * @param 
	 * @return String 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="/test2",method=RequestMethod.GET)	
	public String test2(@Valid @ModelAttribute @Min(value=10) String name,BindingResult bindResult){
		if(bindResult.hasErrors()){
			return "erros";
		}else{
			return "yes";
		}	
	}
}
