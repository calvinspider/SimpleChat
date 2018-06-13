package org.yang.zhang.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtils {
	
	public static String validAll(Object obj) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> errorSet = validator.validate(obj);
		Iterator<ConstraintViolation<Object>> it = errorSet.iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			String message = it.next().getMessage();
			sb.append(message);
			sb.append("！");
		}
		return sb.length() > 0 ? sb.toString() : null;
	}
	
	public static String valid(Object obj) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> errorSet = validator.validate(obj);
		Iterator<ConstraintViolation<Object>> it = errorSet.iterator();
		while(it.hasNext()) {
			String message = it.next().getMessage();
			return message;
		}
		return null;
	}

}
