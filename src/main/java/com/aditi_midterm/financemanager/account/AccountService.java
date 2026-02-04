package com.aditi_midterm.financemanager.account;

import com.aditi_midterm.financemanager.account.dto.AccountResponse;
import com.aditi_midterm.financemanager.account.dto.CreateAccountRequest;
import com.aditi_midterm.financemanager.account.mapper.AccountMapper;
import com.aditi_midterm.financemanager.user.User;
import com.aditi_midterm.financemanager.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;
    public final UserRepository userRepository;
    public final AccountMapper accountMapper;

    public AccountResponse createAccount(@Valid CreateAccountRequest request, Long userId) throws AccountNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("User not found"));

        Account account = accountMapper.toEntity(request, user);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }
}
