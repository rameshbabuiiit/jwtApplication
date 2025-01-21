package com.rapo.jwt.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
}
