package com.aditi_midterm.financemanager.transaction.dto;

import com.aditi_midterm.financemanager.transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

  private Long id;
  private TransactionType transactionType;
  private BigDecimal amount;
  private String note;

  private Long accountId;
  private LocalDateTime createdDate;
}
