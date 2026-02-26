package com.aditi_midterm.financemanager.account.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    @JsonProperty("account_id")
    private Long id;

    @JsonProperty("account_name")
    private String name;

    @JsonProperty("account_balance")
    private BigDecimal balance;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
}
