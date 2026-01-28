package com.aditi_midterm.financemanager.seed;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.user.User;
import com.aditi_midterm.financemanager.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 2)
public class AccountDataLoading implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (accountRepository.count() > 0) return;

        User user = userRepository.findById(1L).orElse(null);

        Account cashAccount = Account.builder()
                .name("Cash Wallet")
                .balance(new BigDecimal("1500.00"))
                .user(user)
                .build();

        Account bankAccount = Account.builder()
                .name("ABA Bank")
                .balance(new BigDecimal("5200.50"))
                .user(user)
                .build();

        Account savingAccount = Account.builder()
                .name("Savings")
                .balance(new BigDecimal("10000.00"))
                .user(user)
                .build();

        accountRepository.saveAll(List.of(
                cashAccount,
                bankAccount,
                savingAccount
        ));
    }
}
