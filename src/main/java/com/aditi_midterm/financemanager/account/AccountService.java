package com.aditi_midterm.financemanager.account;

import com.aditi_midterm.financemanager.account.dto.AccountResponse;
import com.aditi_midterm.financemanager.account.dto.CreateAccountRequest;
import com.aditi_midterm.financemanager.account.dto.UpdateAccountRequest;
import com.aditi_midterm.financemanager.account.mapper.AccountMapper;
import com.aditi_midterm.financemanager.exception.NotFoundException;
import com.aditi_midterm.financemanager.exception.ResourceNotFoundException;
import com.aditi_midterm.financemanager.user.User;
import com.aditi_midterm.financemanager.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;
    public final UserRepository userRepository;
    public final AccountMapper accountMapper;

    public AccountResponse createAccount(@Valid CreateAccountRequest request, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));


        Account account = accountMapper.toEntity(request, user);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }

    public List<AccountResponse> listByUser(Long userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(Long accountId, Long userId) {

        Account acc = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException("Account " + accountId + " not found for user " + userId));
        return accountMapper.toResponse(acc);

    }

    public AccountResponse updateAccount(Long accountId, Long userId, UpdateAccountRequest request) {
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException("Account " + accountId + " not found for user " + userId));

       accountMapper.updateEntityFromRequest(account, request);
       Account updateAccount = accountRepository.save(account);

       return accountMapper.toResponse(updateAccount);
    }

    public void deleteAccount(Long accountId, Long userId) {

        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException("Account " + accountId + " not found for user " + userId));

        accountRepository.delete(account);
    }
}
