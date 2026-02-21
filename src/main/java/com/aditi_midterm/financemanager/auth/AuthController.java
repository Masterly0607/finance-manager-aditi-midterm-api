package com.aditi_midterm.financemanager.auth;

import com.aditi_midterm.financemanager.auth.dto.*;
import com.aditi_midterm.financemanager.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        String token = authService.register(req); // return access token
        return new AuthResponse(token);
    }

    /**
     * Login:
     * - returns access token in body (AuthResponse)
     * - sets refresh token as HttpOnly cookie in response
     */
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req, HttpServletResponse response) {
        return authService.login(req, response);
    }

    /**
     * Refresh:
     * - browser sends refresh token cookie automatically
     * - backend verifies it and returns a NEW access token
     * - optional: rotate refresh token and set a new cookie
     * Note: Send refresh when access token is expired or when we refresh page or close tab(these will delete access from memory)
     */
    @PostMapping("/refresh")
    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        return authService.refresh(request, response);
    }

    /**
     * Logout:
     * - clears refresh token cookie
     * - backend should revoke refresh token server-side (db/redis) if you store it
     */
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }

    /**
     * Current user info (needs valid ACCESS token via Authorization: Bearer ...)
     */
    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal p)) {
            throw new IllegalArgumentException("Not authenticated");
        }
        return new MeResponse(p.getId(), p.email(), p.role());
    }
}
