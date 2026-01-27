package com.aditi_midterm.financemanager.auth;

import com.aditi_midterm.financemanager.auth.dto.*;
import com.aditi_midterm.financemanager.security.JwtService;
import com.aditi_midterm.financemanager.user.Role;
import com.aditi_midterm.financemanager.user.User;
import com.aditi_midterm.financemanager.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String REFRESH_COOKIE_NAME = "refreshToken";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final boolean cookieSecure;
    private final String cookieSameSite;
    private final int refreshCookieMaxAgeSeconds;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       @Value("${app.jwt.cookie.secure:false}") boolean cookieSecure,
                       @Value("${app.jwt.cookie.samesite:Strict}") String cookieSameSite,
                       @Value("${app.jwt.refresh-expiration-days:7}") long refreshExpirationDays) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

        this.cookieSecure = cookieSecure;
        this.cookieSameSite = cookieSameSite;
        this.refreshCookieMaxAgeSeconds = Math.toIntExact(refreshExpirationDays * 24 * 60 * 60);
    }

//    Register
    public void register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .isActive(true)
                .build();

        userRepository.save(user);
    }

    // Sets refresh cookie and returns access token.
    // Logs user in and issues tokens: Access token returned in response body and Refresh token stored in HttpOnly cookie
    public AuthResponse login(LoginRequest req, HttpServletResponse response) {
        String email = req.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new IllegalArgumentException("User is disabled");
        }

        boolean matches = passwordEncoder.matches(req.password(), user.getPasswordHash());
        if (!matches) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        setRefreshCookie(response, refreshToken);

        return new AuthResponse(accessToken);
    }

    // Refresh endpoint logic(Gives a new access token using the refresh cookie when access expires.)
    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = readCookie(request, REFRESH_COOKIE_NAME);
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Missing refresh token");
        }

        var claims = jwtService.parseRefreshClaims(refreshToken);

        String type = claims.get("type", String.class);
        if (!"refresh".equals(type)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = Long.valueOf(claims.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new IllegalArgumentException("User is disabled");
        }

        // Issue NEW access token
        String newAccessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

        // (Optional) rotate refresh token:
        // String newRefreshToken = jwtService.generateRefreshToken(user.getId());
        // setRefreshCookie(response, newRefreshToken);

        return new AuthResponse(newAccessToken);
    }

    // logout clears refresh cookie(Logs user out by removing refresh cookie.)
    public void logout(HttpServletResponse response) {
        clearRefreshCookie(response);
    }

    // ------------------------
    // Cookie helpers
    // ------------------------

    private void setRefreshCookie(HttpServletResponse response, String refreshToken) {
        // We use Set-Cookie header for SameSite control
        String cookie = REFRESH_COOKIE_NAME + "=" + refreshToken
                + "; Max-Age=" + refreshCookieMaxAgeSeconds
                + "; Path=/api/auth/refresh"
                + "; HttpOnly"
                + (cookieSecure ? "; Secure" : "")
                + "; SameSite=" + cookieSameSite;

        response.addHeader("Set-Cookie", cookie);
    }

    private void clearRefreshCookie(HttpServletResponse response) {
        String cookie = REFRESH_COOKIE_NAME + "=; Max-Age=0; Path=/api/auth/refresh; HttpOnly"
                + (cookieSecure ? "; Secure" : "")
                + "; SameSite=" + cookieSameSite;

        response.addHeader("Set-Cookie", cookie);
    }

    private String readCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }
}
