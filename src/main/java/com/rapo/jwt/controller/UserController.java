package com.rapo.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @PostMapping("/info")
    public ResponseEntity<String> createUser() {
        return ResponseEntity.ok("Access Granted to POST!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("Access Granted to GET!");
    }

    @PutMapping("/info")
    public ResponseEntity<String> updateUserInfo() {
        return ResponseEntity.ok("Access Granted to PUT!");
    }

    @PatchMapping("/info")
    public ResponseEntity<String> patchUserInfo() {
        return ResponseEntity.ok("Access Granted to PATCH!");
    }

    @DeleteMapping("/info")
    public ResponseEntity<String> deleteUserInfo() {
        return ResponseEntity.ok("Access Granted to DELETE!");
    }


}
