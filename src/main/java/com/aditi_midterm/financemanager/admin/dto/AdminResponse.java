package com.aditi_midterm.financemanager.admin.dto;

import java.time.Instant;

import com.aditi_midterm.financemanager.user.Role;

public record AdminResponse(
        Long id,
        String email,
        Role role,
        boolean isActive,
        Instant createdAt
) {

}
