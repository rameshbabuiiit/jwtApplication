package com.rapo.jwt.auth;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (Constants.EXCLUDED_ENDPOINTS.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = header.substring(7);
        try {
            Claims claims = JwtUtils.validateToken(token);
            if (claims == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }

            String username = claims.getSubject();
            String scopeString = claims.get("scope", String.class);
            List<String> scopes = scopeString != null ? Arrays.asList(scopeString.split(" ")) : List.of();

            if (!hasRequiredScope(scopes, request.getMethod())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Insufficient Scope");
                return;
            }

            List<SimpleGrantedAuthority> authorities = scopes.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasRequiredScope(List<String> scopes, String method) {
        if (scopes.contains("all")) {
            return true;
        }
        return switch (method) {
            case "POST" -> scopes.contains("create");
            case "PUT", "PATCH" -> scopes.contains("update");
            case "GET" -> scopes.contains("read");
            case "DELETE" -> scopes.contains("delete");
            default -> false;
        };
    }
}