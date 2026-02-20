package com.aditi_midterm.financemanager.transaction.service;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.exception.ResourceNotFoundException;
import com.aditi_midterm.financemanager.transaction.Transaction;
import com.aditi_midterm.financemanager.transaction.TransactionMapper;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
import com.aditi_midterm.financemanager.transaction.TransactionType;
import com.aditi_midterm.financemanager.transaction.dto.AddTransactionRequest;
import com.aditi_midterm.financemanager.transaction.dto.Pagination;
import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import com.aditi_midterm.financemanager.transaction.dto.UpdateTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final TransactionMapper transactionMapper;

  @Override
  public List<TransactionResponse> getAllTransactions(
      Pagination pagination, Long userId, Long account, String type, String search) {

    Pageable pageable =
        PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.by("id").descending());

    Page<Transaction> transactions;

    if (account != null) {
      transactions =
          transactionRepository.findByAccountUserIdAndAccountId(userId, account, pageable);
    } else if (type != null && !type.isBlank()) {
      transactions =
          transactionRepository.findByAccountUserIdAndType(userId, TransactionType.valueOf(type), pageable);
    } else {
      transactions = transactionRepository.findByAccountUserId(userId, pageable);
    }

    pagination.setTotal(transactions.getTotalElements());
    pagination.setTotalPage(transactions.getTotalPages());

    return transactions.getContent().stream()
        .map(transactionMapper::toTransactionResponse)
        .toList();
  }

  @Override
  public TransactionResponse getTransactionById(Long id, Long userId) {
    return transactionRepository
        .findByIdAndAccountUserId(id, userId)
        .map(transactionMapper::toTransactionResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction", id));
  }

  @Override
  public TransactionResponse addTransaction(
      AddTransactionRequest addTransactionRequest, Long userId) {
    Account account =
        accountRepository
            .findByIdAndUserId(addTransactionRequest.getAccountId(), userId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Account", "id", addTransactionRequest.getAccountId()));
    Transaction transaction = transactionMapper.toTransaction(addTransactionRequest);
    transaction.setAccount(account);

    Transaction savedTransaction = transactionRepository.save(transaction);
    return transactionMapper.toTransactionResponse(savedTransaction);
  }

  @Override
  public TransactionResponse updateTransaction(
      Long id, UpdateTransactionRequest updateTransactionRequest, Long userId) {

    Transaction transaction =
        transactionRepository
            .findByIdAndAccountUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction", id));

    transactionMapper.updateEntity(updateTransactionRequest, transaction);
    Transaction updated = transactionRepository.save(transaction);
    return transactionMapper.toTransactionResponse(updated);
  }

  @Override
  public void deleteTransaction(Long id, Long userId) {
    Transaction transaction =
        transactionRepository
            .findByIdAndAccountUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
    transactionRepository.delete(transaction);
  }
}
