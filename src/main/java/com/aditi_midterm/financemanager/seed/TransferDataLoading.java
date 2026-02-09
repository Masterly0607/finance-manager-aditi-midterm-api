package com.aditi_midterm.financemanager.seed;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.transaction.Transaction;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
import com.aditi_midterm.financemanager.transaction.TransactionType;
import com.aditi_midterm.financemanager.transfer.Transfer;
import com.aditi_midterm.financemanager.transfer.TransferRepository;
import com.aditi_midterm.financemanager.transfer.TransferService;
import com.aditi_midterm.financemanager.transfer.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(4)
public class TransferDataLoading implements CommandLineRunner {

  private final AccountRepository accountRepository;
  private final TransferRepository transferRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public void run(String... args) throws Exception {
    if (transactionRepository.count() > 0) return;

    Account bank = accountRepository.findById(1L).orElseThrow();
    Account cash = accountRepository.findById(2L).orElseThrow();

    Transfer t1 =
        Transfer.builder()
            .fromAccount(bank)
            .toAccount(cash)
            .amount(new BigDecimal("300.00"))
            .note("Transfer to Cash")
            .createdAt(LocalDateTime.now())
            .build();

    transferRepository.save(t1);

    Transaction t1Expense =
        Transaction.builder()
            .account(bank)
            .type(TransactionType.EXPENSE)
            .amount(new BigDecimal("300.00"))
            .note("Transfer to Cash")
            .build();

    Transaction t1Income =
        Transaction.builder()
            .account(cash)
            .type(TransactionType.INCOME)
            .amount(new BigDecimal("300.00"))
            .note("Transfer from Bank")
            .build();

    Transfer t2 =
        Transfer.builder()
            .fromAccount(cash)
            .toAccount(bank)
            .amount(new BigDecimal("100.00"))
            .note("Transfer to Bank")
            .createdAt(LocalDateTime.now())
            .build();

    transferRepository.save(t2);

    Transaction t2Expense =
        Transaction.builder()
            .account(cash)
            .type(TransactionType.EXPENSE)
            .amount(new BigDecimal("100.00"))
            .note("Transfer to Bank")
            .build();

    Transaction t2Income =
        Transaction.builder()
            .account(bank)
            .type(TransactionType.INCOME)
            .amount(new BigDecimal("100.00"))
            .note("Transfer from Cash")
            .build();

    transactionRepository.saveAll(List.of(t1Expense, t1Income, t2Expense, t2Income));
  }
}
