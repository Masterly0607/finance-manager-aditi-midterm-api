package com.aditi_midterm.financemanager.account;

import com.aditi_midterm.financemanager.account.dto.AccountResponse;
import com.aditi_midterm.financemanager.account.dto.CreateAccountRequest;
import com.aditi_midterm.financemanager.account.dto.UpdateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AccountController.BASE_URL)
@RequiredArgsConstructor
public class AccountController {
    public static final String BASE_URL = "/api/accounts";

    public final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request, @PathVariable Long userId) {
        AccountResponse response = accountService.createAccount(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<AccountResponse> listAccount(@PathVariable Long userId) {
        return accountService.listByUser(userId);
    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(@PathVariable Long accountId, @PathVariable Long userId) {
        return accountService.getAccountById(accountId, userId);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long accountId, @PathVariable Long userId, @Valid @RequestBody UpdateAccountRequest request) {
        AccountResponse response = accountService.updateAccount(accountId, userId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        accountService.deleteAccount(accountId, userId);

        return ResponseEntity.noContent().build();
    }
}
