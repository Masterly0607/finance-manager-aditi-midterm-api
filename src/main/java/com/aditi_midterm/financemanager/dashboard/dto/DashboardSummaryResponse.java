package com.aditi_midterm.financemanager.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryResponse(
        BigDecimal totalBalance,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal net,
        List<AccountBalanceItem> accounts
) {
    public record AccountBalanceItem(
            Long accountId,
            String name,
            BigDecimal balance
    ) {}
}
