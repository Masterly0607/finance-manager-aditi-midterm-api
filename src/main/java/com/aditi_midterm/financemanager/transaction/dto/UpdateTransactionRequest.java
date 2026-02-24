package com.aditi_midterm.financemanager.transaction.dto;

import com.aditi_midterm.financemanager.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateTransactionRequest {

  @NotNull private TransactionType transactionType;

  @NotNull
  @DecimalMin(value = "0.01")
  private BigDecimal amount;

  @Size(max = 500)
  private String note;

  @NotNull
  private LocalDate transactionDate;
}
