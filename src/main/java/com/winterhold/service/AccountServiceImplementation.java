package com.winterhold.service;

import com.winterhold.ApplicationUserDetails;
import com.winterhold.MvcSecurityConfiguration;
import com.winterhold.dao.AccountRepository;
import com.winterhold.dto.account.RegisterDTO;
import com.winterhold.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImplementation implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void registerAccount(RegisterDTO dto) {
        PasswordEncoder encoder = MvcSecurityConfiguration.getPasswordEncoder();
        String hashedPassword = encoder.encode(dto.getPassword());
        Account entity = new Account(dto.getUsername(),
                hashedPassword);
        accountRepository.save(entity);
    }

    @Override
    public Boolean checkExistingAccount(String username) {
        Long totalAccount = accountRepository.count(username);
        return (totalAccount > 0) ? true : false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account entity = accountRepository.findById(username).get();
        UserDetails userDetails = new ApplicationUserDetails(entity);
        return userDetails;
    }
}
