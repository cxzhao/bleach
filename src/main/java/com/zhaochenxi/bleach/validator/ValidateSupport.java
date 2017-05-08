package com.zhaochenxi.bleach.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * @ClassName: ValidateSupport 
 * @Description: 该验证支持验证方式
 * @author zhaochenxi
 * @date 2017年3月29日 下午12:42:33
 */
public class ValidateSupport implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * target需要验证的对象
	 */
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}
	
}
