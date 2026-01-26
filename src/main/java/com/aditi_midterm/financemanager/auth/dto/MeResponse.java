package com.aditi_midterm.financemanager.auth.dto;

import com.aditi_midterm.financemanager.user.Role;

public record MeResponse(
        Long id,
        String email,
        Role role
) {}
