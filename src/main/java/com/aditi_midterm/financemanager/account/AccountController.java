package com.aditi_midterm.financemanager.account;

import com.aditi_midterm.financemanager.account.dto.AccountResponse;
import com.aditi_midterm.financemanager.account.dto.CreateAccountRequest;
import com.aditi_midterm.financemanager.account.dto.UpdateAccountRequest;
import com.aditi_midterm.financemanager.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AccountController.BASE_URL)
@RequiredArgsConstructor
public class AccountController {

    public static final String BASE_URL = "/api/accounts";

    private final AccountService accountService;

    //  CREATE ACCOUNT
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request,
            @AuthenticationPrincipal UserPrincipal me
    ) {

        AccountResponse response =
                accountService.createAccount(request, me.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //  LIST USER ACCOUNTS
    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAccount(
            @AuthenticationPrincipal UserPrincipal me
    ) {

        List<AccountResponse> accounts =
                accountService.listByUser(me.getId());

        return ResponseEntity.ok(accounts);
    }

    //  GET ACCOUNT BY ID
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable Long accountId,
            @AuthenticationPrincipal UserPrincipal me
    ) {

        AccountResponse account =
                accountService.getAccountById(accountId, me.getId());

        return ResponseEntity.ok(account);
    }

    //  UPDATE ACCOUNT
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long accountId,
            @Valid @RequestBody UpdateAccountRequest request,
            @AuthenticationPrincipal UserPrincipal me
    ) {

        AccountResponse response =
                accountService.updateAccount(accountId, me.getId(), request);

        return ResponseEntity.ok(response);
    }

    //  DELETE ACCOUNT
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long accountId,
            @AuthenticationPrincipal UserPrincipal me
    ) {

        accountService.deleteAccount(accountId, me.getId());

        return ResponseEntity.noContent().build();
    }
}
