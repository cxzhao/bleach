package com.zhaochenxi.bleach.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.zhaochenxi.bleach.validator.annotation.Length;

/**
 * @ClassName: LengthValidate
 * @Description: 字符串长度验证
 * @author zhaochenxi
 * @date 2017年3月31日 下午11:40:13
 */
public class LengthValidate implements ConstraintValidator<Length,String>{

	private int min;
	private int max;
	private String message;
		
	@Override
	public void initialize(Length constraintAnnotation) {
		this.min=constraintAnnotation.min();
		this.max=constraintAnnotation.max();
		this.message=constraintAnnotation.message();	
		System.out.println("------------------------------------------验证长度初始化------------------------------------------");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//同时验证最小值和最大值，如果有一个不满足就返回false
		if(this.min!=-1&&this.max!=-1){
			return min(value)&&max(value);
		}
		//只需要验证最小值
		if(this.min!=-1){
			return min(value);
		}
		//只需要验证最大值
		if(this.max!=-1){
			return max(value);
		}
		return false;
	}
	
	public boolean min(String target){
		if(target!=null){
			if(target.length()>=this.min){
				return true;
			}else{
				return false;
			}
		}else{
			this.setMessage("异常，被校验的对象为空引用");
			throw new NullPointerException("被校验的字段为空！");
		}
		
	}
	
	public boolean max(String target){
		if(target!=null){
			if(target.length()<=this.max){
				return true;
			}else{
				return false;
			}
		}else{
			this.setMessage("异常，被校验的对象为空引用");
			throw new NullPointerException("被校验的字段为空！");
		}
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
