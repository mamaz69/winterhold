package com.winterhold.validation;

import com.winterhold.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

    @Autowired
    private AccountService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !service.checkExistingAccount(value);
    }
}
