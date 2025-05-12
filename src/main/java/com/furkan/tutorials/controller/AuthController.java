package com.furkan.tutorials.controller;

import com.furkan.tutorials.dto.ApiResponse;
import com.furkan.tutorials.dto.RegisterRequest;
import com.furkan.tutorials.model.User;
import com.furkan.tutorials.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Kayıt başarılı!", result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody Map<String, String> creds) {
        String token = authService.login(creds.get("username"), creds.get("password"));
        return ResponseEntity.ok(ApiResponse.success("Giriş başarılı!", token));
    }



}
