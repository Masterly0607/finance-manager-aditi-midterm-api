package com.aditi_midterm.financemanager.dashboard;

import com.aditi_midterm.financemanager.dashboard.dto.DashboardSummaryResponse;

public interface DashboardService {
    DashboardSummaryResponse getSummary(Long userId);
}
