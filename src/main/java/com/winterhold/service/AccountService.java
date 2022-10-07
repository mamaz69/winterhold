package com.winterhold.service;

import com.winterhold.dto.account.RegisterDTO;

public interface AccountService {

    public void registerAccount(RegisterDTO dto);
    public Boolean checkExistingAccount(String username);
}
