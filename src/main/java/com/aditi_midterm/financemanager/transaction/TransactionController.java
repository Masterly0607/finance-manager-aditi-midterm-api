package com.aditi_midterm.financemanager.transaction;

import com.aditi_midterm.financemanager.transaction.dto.*;
import com.aditi_midterm.financemanager.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(TransactionController.BASE_URL)
@RequiredArgsConstructor
public class TransactionController {

    public static final String BASE_URL = "/api/transactions";

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<PaginationResponse<TransactionResponse>> getAllTransactions(Pagination pagination) {
        PaginationResponse<TransactionResponse> response = new PaginationResponse<>();

        response.setData(transactionService.getAllTransactions(pagination));
        response.setPagination(pagination);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody AddTransactionRequest addTransactionRequest) {
        TransactionResponse response = transactionService.addTransaction(addTransactionRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest updateTransactionRequest) {
        TransactionResponse response = transactionService.updateTransaction(id, updateTransactionRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransaction(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Transaction deleted successfully");
        response.put("transactionId", id.toString());

        return ResponseEntity.ok(response);
    }
}
