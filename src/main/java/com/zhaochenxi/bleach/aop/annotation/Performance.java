package com.zhaochenxi.bleach.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Performance 
 * @Description: 性能测试代码注解
 * @author zhaochenxi
 * @date 2017年4月18日 下午5:03:15
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Performance {
	public String value() default "";
}
