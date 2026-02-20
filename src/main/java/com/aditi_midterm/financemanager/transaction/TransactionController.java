package com.aditi_midterm.financemanager.transaction;

import com.aditi_midterm.financemanager.security.UserPrincipal;
import com.aditi_midterm.financemanager.transaction.dto.*;
import com.aditi_midterm.financemanager.transaction.service.TransactionService;
import com.aditi_midterm.financemanager.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<PaginationResponse<TransactionResponse>> getAllTransactions(
      Pagination pagination,
      @RequestParam(required = false) Long account,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String search,
      Authentication authentication) {
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    Long userId = principal.getId();
    PaginationResponse<TransactionResponse> response = new PaginationResponse<>();

    response.setData(
        transactionService.getAllTransactions(pagination, userId, account, type, search));
    response.setPagination(pagination);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> getTransactionById(
      @PathVariable Long id, @AuthenticationPrincipal UserPrincipal me) {
    TransactionResponse response = transactionService.getTransactionById(id, me.getId());
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<TransactionResponse> createTransaction(
      @Valid @RequestBody AddTransactionRequest addTransactionRequest,
      @AuthenticationPrincipal UserPrincipal me) {
    TransactionResponse response =
        transactionService.addTransaction(addTransactionRequest, me.getId());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TransactionResponse> updateTransaction(
      @PathVariable Long id,
      @Valid @RequestBody UpdateTransactionRequest updateTransactionRequest,
      @AuthenticationPrincipal UserPrincipal me) {
    TransactionResponse response =
        transactionService.updateTransaction(id, updateTransactionRequest, me.getId());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteTransactionById(
      @PathVariable Long id, @AuthenticationPrincipal UserPrincipal me) {
    transactionService.deleteTransaction(id, me.getId());

    Map<String, String> response = new HashMap<>();
    response.put("message", "Transaction deleted successfully");
    response.put("transactionId", id.toString());

    return ResponseEntity.ok(response);
  }
}
