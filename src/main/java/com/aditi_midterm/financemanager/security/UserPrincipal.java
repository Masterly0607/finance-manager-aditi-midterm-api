package com.aditi_midterm.financemanager.security;

import com.aditi_midterm.financemanager.user.Role;

public record UserPrincipal(Long userId, String email, Role role) {}
