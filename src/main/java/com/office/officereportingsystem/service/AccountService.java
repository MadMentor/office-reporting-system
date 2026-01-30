package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.AccountCreateRequestDto;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;

public interface AccountService {

    void createAdmin(AccountCreateRequestDto dto);

    void createUser(AccountCreateRequestDto dto);

    void updateAccount(Integer accountId, AccountUpdateRequestDto dto);

    void deleteAccount(Integer accountId, String currentUserEmail);
}
