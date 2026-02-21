package com.aditi_midterm.financemanager.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class IncomingRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(IncomingRequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Wrap the request so we can read the body multiple times (important!)
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 0);

        // ────────────────────────────────────────────────
        // Log BEFORE the request goes further
        // ────────────────────────────────────────────────
        logRequestDetails(wrappedRequest);

        // Continue the chain (your JwtAuthFilter, controllers, etc.)
        filterChain.doFilter(wrappedRequest, response);

        // You can also log something AFTER if you want (response status, etc.)
        // log.info("Response status: {}", response.getStatus());
    }

    private void logRequestDetails(ContentCachingRequestWrapper request) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("\n╔═══════════════════════════════════════════════ Incoming Request ═══════════════════════════════════════════════╗\n");

        // Method + full URI + query
        String query = request.getQueryString();
        String fullUrl = request.getRequestURI() + (query != null ? "?" + query : "");
        sb.append("Method     : ").append(request.getMethod()).append("\n");
        sb.append("URL        : ").append(fullUrl).append("\n");
        sb.append("Remote Addr: ").append(request.getRemoteAddr()).append("\n");

        // Headers
        sb.append("Headers    :\n");
        Collections.list(request.getHeaderNames()).forEach(name -> {
            String value = request.getHeader(name);
            // Mask sensitive headers if you want
            if ("authorization".equalsIgnoreCase(name) || "cookie".equalsIgnoreCase(name)) {
                value = "[masked]";
            }
            sb.append("  ").append(name).append(": ").append(value).append("\n");
        });

        // Body (only for methods that usually have body + supported content type)
        String contentType = request.getContentType();
        if (request.getContentLength() > 0 &&
                (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType) ||
                 MediaType.TEXT_PLAIN_VALUE.equalsIgnoreCase(contentType) ||
                 (contentType != null && contentType.startsWith("application/json")))) {

            byte[] bodyBytes = request.getContentAsByteArray();
            if (bodyBytes.length > 0) {
                String body = new String(bodyBytes, StandardCharsets.UTF_8);
                sb.append("Body       :\n").append(body).append("\n");
            }
        }

        sb.append("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

        log.info(sb.toString());
    }
}
