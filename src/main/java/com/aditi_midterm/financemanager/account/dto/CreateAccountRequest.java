package com.aditi_midterm.financemanager.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    @NotBlank(message = "Account name is required")
    private String name;

    @NotNull(message = "Initial account is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "balance must be greater than or equal to 0")
    private BigDecimal balance;
}
