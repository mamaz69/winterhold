package com.winterhold.validation;


import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCodeValidator implements ConstraintValidator<UniqueCode,String> {

    @Autowired
    private CategoryService service;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        Boolean exist= service.checkExistingCode(code);
        return !exist;
    }
}
