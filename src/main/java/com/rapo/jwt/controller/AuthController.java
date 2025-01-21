package com.rapo.jwt.controller;


import com.rapo.jwt.auth.JwtUtils;
import com.rapo.jwt.auth.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> createToken(@RequestBody Map<String, Object> claims) {
        JwtResponse response = JwtUtils.generateToken(claims, "user");
        return ResponseEntity.ok(response);
    }
}

