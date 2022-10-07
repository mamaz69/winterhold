package com.winterhold.controller;

import com.winterhold.dto.account.RegisterDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/registerForm")
    public String registerForm(Model model){
        RegisterDTO dto = new RegisterDTO();
        model.addAttribute("account",dto);
        return "account/register-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account")RegisterDTO dto,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()){
            return "account/register-form";
        }else {
            service.registerAccount(dto);
            return "redirect:/account/loginForm";
        }
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model){
        return "account/login-form";
    }

}
