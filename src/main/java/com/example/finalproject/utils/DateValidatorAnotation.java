package com.example.finalproject.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidatorAnotation {

	public static final String REGEX_DEFAULT = "^(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/\\d\\d\\d\\d$";

	public static final String PATTERN_DEFAULT = "MM/dd/yyyy";

	String regex() default REGEX_DEFAULT;
	
	String pattern() default PATTERN_DEFAULT;

}
