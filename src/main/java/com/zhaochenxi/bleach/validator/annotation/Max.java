package com.zhaochenxi.bleach.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.zhaochenxi.bleach.validator.impl.LengthValidate;

@Constraint(validatedBy = LengthValidate.class) 
@Target({ElementType.PARAMETER,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Max {

}
