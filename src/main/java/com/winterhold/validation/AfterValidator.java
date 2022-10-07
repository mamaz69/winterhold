package com.winterhold.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterValidator implements ConstraintValidator<After, Object> {

	private String previousDateField;
	private String subsequentDateField;
	
	@Override
	public void initialize(After constraintAnnotation) {
        this.previousDateField = constraintAnnotation.previousDateField();
        this.subsequentDateField = constraintAnnotation.subsequentDateField();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate previousDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(previousDateField));
        LocalDate subsequentDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(subsequentDateField));
        if(previousDateValue == null && subsequentDateValue == null) {
        	return true;
        }else if(previousDateValue == null && subsequentDateValue != null){
			return false;
		}else if (previousDateValue != null && subsequentDateValue == null){
			return true;
		}else{
		Boolean validation = previousDateValue.isBefore(subsequentDateValue);
        return validation;
		}
	}
}