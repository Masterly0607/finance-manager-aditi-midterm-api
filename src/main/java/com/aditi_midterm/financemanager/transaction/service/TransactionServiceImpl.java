package com.aditi_midterm.financemanager.transaction.service;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.exception.ResourceNotFoundException;
import com.aditi_midterm.financemanager.transaction.Transaction;
import com.aditi_midterm.financemanager.transaction.TransactionMapper;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
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
  public List<TransactionResponse> getAllTransactions(Pagination pagination) {

    Pageable pageable =
        PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.by("id").descending());

    Page<Transaction> transactions = transactionRepository.findAll(pageable);
    List<Transaction> transactionList = transactions.getContent();

    pagination.setTotal(transactions.getTotalElements());
    pagination.setTotalPage(transactions.getTotalPages());

    List<TransactionResponse> transactionResponseList = new ArrayList<>();

    for (Transaction transaction : transactionList) {
      TransactionResponse transactionResponse =
          transactionMapper.toTransactionResponse(transaction);
      transactionResponseList.add(transactionResponse);
    }

    return transactionResponseList;
  }

  @Override
  public TransactionResponse getTransactionById(Long id) {
    return transactionRepository
        .findTransactionById(id)
        .map(transactionMapper::toTransactionResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction", id));
  }

  @Override
  public TransactionResponse addTransaction(AddTransactionRequest addTransactionRequest) {
    Account account =
        accountRepository
            .findById(addTransactionRequest.getAccountId())
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
      Long id, UpdateTransactionRequest updateTransactionRequest) {

    Transaction transaction =
        transactionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction", id));

    transactionMapper.updateEntity(updateTransactionRequest, transaction);
    Transaction updated = transactionRepository.save(transaction);
    return transactionMapper.toTransactionResponse(updated);
  }

  @Override
  public void deleteTransaction(Long id) {
    Transaction transaction =
        transactionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
    transactionRepository.delete(transaction);
  }
}
