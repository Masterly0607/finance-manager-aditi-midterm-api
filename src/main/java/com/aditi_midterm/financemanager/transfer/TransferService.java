package com.aditi_midterm.financemanager.transfer;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.exception.ResourceNotFoundException;
import com.aditi_midterm.financemanager.transaction.Transaction;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
import com.aditi_midterm.financemanager.transaction.TransactionType;
import com.aditi_midterm.financemanager.transfer.dto.TransferRequest;
import com.aditi_midterm.financemanager.transfer.dto.TransferResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

  private final TransactionRepository transactionRepository;
  private final TransferRepository transferRepository;
  private final AccountRepository accountRepository;
  private final TransferMapper transferMapper;

  @Transactional
  public TransferResponse transfer(TransferRequest request) {

    if (request.getFromAccountId().equals(request.getToAccountId())) {
      throw new IllegalArgumentException("Cannot transfer to same account");
    }

    Account from =
        accountRepository
            .findById(request.getFromAccountId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", request.getFromAccountId()));

    Account to =
        accountRepository
            .findById(request.getToAccountId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", request.getToAccountId()));

    Transfer transfer = transferMapper.toTransfer(request, from, to);
    transferRepository.save(transfer);

    Transaction expense =
        Transaction.builder()
            .account(from)
            .type(TransactionType.EXPENSE)
            .amount(request.getAmount())
            .note("Transfer to account " + to.getId())
            .build();

    Transaction income =
        Transaction.builder()
            .account(to)
            .type(TransactionType.INCOME)
            .amount(request.getAmount())
            .note("Transfer from account " + from.getId())
            .build();

    transactionRepository.saveAll(List.of(expense, income));

    return transferMapper.toResponse(transfer);
  }
}
