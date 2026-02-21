package com.aditi_midterm.financemanager.seed;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.transaction.Transaction;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
import com.aditi_midterm.financemanager.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(3)
public class TransactionDataLoading implements CommandLineRunner {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  @Override
  public void run(String... args) throws Exception {

<<<<<<< HEAD
    if (transactionRepository.count() > 0) return;

    Account cash = accountRepository.findByName("Cash Wallet").orElse(null);
    Account bank = accountRepository.findByName("ABA Bank").orElse(null);

    if (cash == null || bank == null) {
      System.out.println("Accounts not found. Skipping transaction seeding.");
      return;
=======
        if (transactionRepository.count() > 0) return;
        Long targetUserId = 6L;

        Account cash = accountRepository.findByNameAndUserId("Cash Wallet", targetUserId)
                .orElse(null);

        Account bank = accountRepository.findByNameAndUserId("ABA Bank", targetUserId)
                .orElse(null);

        if (cash == null || bank == null) {
            System.out.println("Accounts not found. Skipping transaction seeding.");
            return;
        }

        Transaction t1 = Transaction.builder()
                .type(TransactionType.INCOME)
                .amount(new BigDecimal("500.00"))
                .note("Salary")
                .account(bank)
                .build();

        Transaction t2 = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("120.50"))
                .note("Groceries")
                .account(cash)
                .build();

        Transaction t3 = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("45.00"))
                .note("Coffee")
                .account(cash)
                .build();

        transactionRepository.saveAll(List.of(
           t1, t2, t3
        ));
>>>>>>> b35b615dbcb19d507e39803e66e5fe647ad91bd2
    }

    Transaction t1 =
        Transaction.builder()
            .type(TransactionType.INCOME)
            .amount(new BigDecimal("500.00"))
            .note("Salary")
            .account(bank)
            .build();

    Transaction t2 =
        Transaction.builder()
            .type(TransactionType.EXPENSE)
            .amount(new BigDecimal("120.50"))
            .note("Groceries")
            .account(cash)
            .build();

    Transaction t3 =
        Transaction.builder()
            .type(TransactionType.EXPENSE)
            .amount(new BigDecimal("45.00"))
            .note("Coffee")
            .account(cash)
            .build();

    transactionRepository.saveAll(List.of(t1, t2, t3));
  }
}
