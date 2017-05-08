package com.zhaochenxi.bleach.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * @ClassName: ValidatorWrap 
 * @Description: 实现Spring 的验证接口
 * @author zhaochenxi
 * @date 2017年3月29日 下午12:36:06
 */
public class ValidatorWrap implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

}
