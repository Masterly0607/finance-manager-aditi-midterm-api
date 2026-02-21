package com.aditi_midterm.financemanager.dashboard;

import com.aditi_midterm.financemanager.dashboard.dto.DashboardSummaryResponse;
import com.aditi_midterm.financemanager.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse summary(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return dashboardService.getSummary(principal.getId());
    }
}
