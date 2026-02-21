package com.aditi_midterm.financemanager.account.mapper;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.dto.AccountResponse;
import com.aditi_midterm.financemanager.account.dto.CreateAccountRequest;
import com.aditi_midterm.financemanager.account.dto.UpdateAccountRequest;
import com.aditi_midterm.financemanager.user.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    // convert CreateAccountRequest to Account Entity
    public Account toEntity(CreateAccountRequest request, User user) {
        Account acc = new Account();
        acc.setName(request.getName());
        acc.setBalance(request.getBalance());
        acc.setUser(user);

        return acc;
    }

    public AccountResponse toResponse(Account a) {
        return new AccountResponse(
                a.getId(),
                a.getName(),
                a.getBalance(),
                a.getUser().getId(),
                a.getCreatedAt()
        );
    }

    public void updateEntityFromRequest(Account account, UpdateAccountRequest request) {
        account.setName(request.getName());
        account.setBalance(request.getBalance());
    }
}
