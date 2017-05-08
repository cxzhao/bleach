package com.zhaochenxi.bleach.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

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
	
	Class<?>[] groups() default { };  
	  
    Class<? extends Payload>[] payload() default { };  
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

    	Length[] value();
	}
}
