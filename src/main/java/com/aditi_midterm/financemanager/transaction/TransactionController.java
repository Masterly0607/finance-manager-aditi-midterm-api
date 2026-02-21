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
<<<<<<< HEAD
          Pagination pagination,
          @RequestParam(required = false) Long account,
          @RequestParam(required = false) String type,
          @RequestParam(required = false) String search,
          Authentication authentication) {
=======
      Pagination pagination,
      @RequestParam(required = false) Long account,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String search,
      Authentication authentication) {
>>>>>>> b35b615dbcb19d507e39803e66e5fe647ad91bd2
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    Long userId = principal.getId();
    PaginationResponse<TransactionResponse> response = new PaginationResponse<>();

    response.setData(
<<<<<<< HEAD
            transactionService.getAllTransactions(pagination, userId, account, type, search));
=======
        transactionService.getAllTransactions(pagination, userId, account, type, search));
>>>>>>> b35b615dbcb19d507e39803e66e5fe647ad91bd2
    response.setPagination(pagination);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> getTransactionById(
<<<<<<< HEAD
          @PathVariable Long id, @AuthenticationPrincipal UserPrincipal me) {
=======
      @PathVariable Long id, @AuthenticationPrincipal UserPrincipal me) {
>>>>>>> b35b615dbcb19d507e39803e66e5fe647ad91bd2
    TransactionResponse response = transactionService.getTransactionById(id, me.getId());
    return ResponseEntity.ok(response);
  }

<<<<<<< HEAD
  @PostMapping("/income")
  public ResponseEntity<TransactionResponse> addIncome(
          @Valid @RequestBody AddTransactionRequest request,
          @AuthenticationPrincipal UserPrincipal me
  ) {
    TransactionResponse response = transactionService.addIncome(request, me.getId());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/expense")
  public ResponseEntity<TransactionResponse> addExpense(
          @Valid @RequestBody AddTransactionRequest request,
          @AuthenticationPrincipal UserPrincipal me
  ) {
    TransactionResponse response = transactionService.addExpense(request, me.getId());
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

=======
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

>>>>>>> b35b615dbcb19d507e39803e66e5fe647ad91bd2
    return ResponseEntity.ok(response);
  }
}
