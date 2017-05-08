package com.zhaochenxi.bleach.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @ClassName: LengthValidate 
 * @Description: 参数长度验证
 * @author zhaochenxi
 * @date 2017年4月18日 下午10:49:19
 */
@Aspect
@Component
public class LengthValidate {
	
	@Before("")
	public void checkParam(){
		
	}
	
	@AfterReturning("")
	public void returnResult(){
		
	}

}
