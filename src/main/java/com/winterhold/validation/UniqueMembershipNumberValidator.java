package com.winterhold.validation;


import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueMembershipNumberValidator implements ConstraintValidator<UniqueMembershipNumber,String> {
    @Autowired
    private CustomerService service;

    @Override
    public boolean isValid(String membershipNumber, ConstraintValidatorContext context) {
        Boolean exist= service.checkExistingMembershipNumber(membershipNumber);
        return !exist;
    }
}
