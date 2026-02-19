package com.aditi_midterm.financemanager.transaction.service;

import com.aditi_midterm.financemanager.transaction.dto.AddTransactionRequest;
import com.aditi_midterm.financemanager.transaction.dto.Pagination;
import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import com.aditi_midterm.financemanager.transaction.dto.UpdateTransactionRequest;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAllTransactions(Pagination pagination, Long userId);
    TransactionResponse getTransactionById(Long id, Long userId);
    TransactionResponse addTransaction(AddTransactionRequest addTransactionRequest, Long userId);
    TransactionResponse updateTransaction(Long id, UpdateTransactionRequest updateTransactionRequest, Long userId);
    void deleteTransaction(Long id,  Long userId);
}
