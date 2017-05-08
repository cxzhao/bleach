package com.zhaochenxi.bleach.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.validation.Errors;

public interface IValidateSupport {

	/**
	 * @Title: support 
	 * @Description: 判断是否支持该注解 
	 * @param 
	 * @return boolean 
	 * @throws 
	 * @author zhaochenxi
	 */
	public boolean support(Annotation annotation);
	
	/**
	 * 
	 * @Title: valid 
	 * @Description: 验证 
	 * @param 
	 * @return void 
	 * @throws 
	 * @author zhaochenxi
	 */
	public void valid(Annotation annotation, Object object, Field field, Errors errors);
}
