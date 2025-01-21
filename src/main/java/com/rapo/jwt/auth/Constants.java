package com.rapo.jwt.auth;

import java.util.List;

public class Constants {
    public static final List<String> EXCLUDED_ENDPOINTS = List.of(
            "/auth/token",
            "/actuator/**",
            "/error"// Add more endpoints as needed
    );
}
