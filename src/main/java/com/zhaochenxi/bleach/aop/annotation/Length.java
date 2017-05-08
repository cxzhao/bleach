package com.zhaochenxi.bleach.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import org.springframework.stereotype.Component;
import com.zhaochenxi.bleach.validator.impl.LengthValidate;

@Target({ElementType.PARAMETER,ElementType.LOCAL_VARIABLE,ElementType.FIELD,ElementType.TYPE_PARAMETER,ElementType.TYPE})
@Constraint(validatedBy = LengthValidate.class)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Length {
	int min() default -1;
	
	int max() default -1;
	
	int value() default -1;
	
	String message() default "";
}
