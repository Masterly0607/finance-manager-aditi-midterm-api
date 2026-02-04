package com.aditi_midterm.financemanager.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountController.BASE_URL)
@RequiredArgsConstructor
public class AccountController {
    public static final String BASE_URL = "/api/accounts";

    public final AccountService accountService;
}
