package com.example.simplecash.aop;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String reqId = UUID.randomUUID().toString().substring(0, 8);
        String method = request.getMethod();
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String remote = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        MDC.put("reqId", reqId);
        MDC.put("method", method);
        MDC.put("path", path + (query != null ? ("?" + query) : ""));
        MDC.put("remote", remote);
        if (ua != null) MDC.put("ua", ua);

        long start = System.nanoTime();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            MDC.put("status", String.valueOf(response.getStatus()));
            MDC.put("took", tookMs + "ms");
            // Let the Aspect perform the pretty logs; nothing to log here to avoid duplicate lines.
            MDC.clear();
        }
    }
}

