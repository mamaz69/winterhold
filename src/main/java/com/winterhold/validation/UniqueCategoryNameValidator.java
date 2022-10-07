package com.winterhold.validation;

import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName,String>{
    @Autowired
    private CategoryService service;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Boolean exist= service.checkExistingCategory(name);
        return !exist;
    }
}
