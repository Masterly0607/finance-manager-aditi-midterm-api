package com.aditi_midterm.financemanager.transaction.service;

import com.aditi_midterm.financemanager.transaction.dto.AddTransactionRequest;
import com.aditi_midterm.financemanager.transaction.dto.Pagination;
import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import com.aditi_midterm.financemanager.transaction.dto.UpdateTransactionRequest;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAllTransactions(Pagination pagination);
    TransactionResponse getTransactionById(Long id);
    TransactionResponse addTransaction(AddTransactionRequest addTransactionRequest);
    TransactionResponse updateTransaction(Long id, UpdateTransactionRequest updateTransactionRequest);
    void deleteTransaction(Long id);
}
