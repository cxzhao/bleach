package com.zhaochenxi.bleach.validator.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.validation.Errors;

import com.zhaochenxi.bleach.validator.IValidateSupport;

public class LengthValidateRule implements IValidateSupport{

	@Override
	public boolean support(Annotation annotation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void valid(Annotation annotation, Object object, Field field, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
