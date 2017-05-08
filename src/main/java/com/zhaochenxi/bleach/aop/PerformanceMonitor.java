package com.zhaochenxi.bleach.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @ClassName: PerformanceMonitor 
 * @Description: 性能检测AOP
 * @author zhaochenxi
 * @date 2017年4月15日 下午3:46:56
 */
@Aspect
@Component
public class PerformanceMonitor {
	
	private long begin=0L;
	private long end=0L;
	private long result=0L;
	
	//execution(* *Query(..))
	//@annotation(com.zhaochenxi.bleach.aop.annotation.Performance)
	@Before("execution(* *Query(..))")
	public void begin(){
		System.out.println("--------begin-----------------");
		this.begin=System.currentTimeMillis();
	}
	//execution(* *Query(..)) && 
	//@AfterReturning("@annotation(com.zhaochenxi.bleach.aop.annotation.Performance)")
	@AfterReturning("execution(* *Query(..))")
	public void end(){		
		this.end=System.currentTimeMillis();
		this.result = this.end-this.begin;
		System.out.println("执行该代码块花费时间为："+this.result);
		System.out.println("--------end-----------------");
	}
}
