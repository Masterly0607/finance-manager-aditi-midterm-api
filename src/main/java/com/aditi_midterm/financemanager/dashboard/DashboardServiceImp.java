package com.aditi_midterm.financemanager.dashboard;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.account.AccountRepository;
import com.aditi_midterm.financemanager.dashboard.dto.DashboardSummaryResponse;
import com.aditi_midterm.financemanager.transaction.TransactionRepository;
import com.aditi_midterm.financemanager.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImp implements DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public DashboardSummaryResponse getSummary(Long userId) {

        BigDecimal totalBalance = accountRepository.sumBalanceByUserId(userId);
        BigDecimal totalIncome = transactionRepository.sumAmountByUserAndType(userId, TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.sumAmountByUserAndType(userId, TransactionType.EXPENSE);

        BigDecimal net = totalIncome.subtract(totalExpense);

        List<Account> accounts = accountRepository.findAllByUserId(userId);

        List<DashboardSummaryResponse.AccountBalanceItem> items = accounts.stream()
                .map(a -> new DashboardSummaryResponse.AccountBalanceItem(
                        a.getId(),
                        a.getName(),
                        a.getBalance()
                ))
                .toList();

        return new DashboardSummaryResponse(
                totalBalance,
                totalIncome,
                totalExpense,
                net,
                items
        );
    }
}
