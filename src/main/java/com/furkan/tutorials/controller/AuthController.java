package com.furkan.tutorials.controller;

import com.furkan.tutorials.dto.ApiResponse;
import com.furkan.tutorials.dto.LoginResponse;
import com.furkan.tutorials.dto.RegisterRequest;
import com.furkan.tutorials.model.RefreshToken;
import com.furkan.tutorials.model.User;
import com.furkan.tutorials.repository.UserRepository;
import com.furkan.tutorials.security.JwtUtil;
import com.furkan.tutorials.service.AuthService;
import com.furkan.tutorials.service.RefreshTokenService;
import com.furkan.tutorials.service.TokenBlacklistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    private RefreshTokenService refreshTokenService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil; // JWT üretmek için
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, UserRepository userRepository, JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Valid RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Kayıt başarılı!", result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody Map<String, String> creds) {
        LoginResponse tokens = authService.login(creds.get("username"), creds.get("password"));
        return ResponseEntity.ok(ApiResponse.success("Giriş başarılı!", tokens));
    }



    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(@RequestBody Map<String, String> req) {
        String refreshToken = req.get("refreshToken");

        RefreshToken token = refreshTokenService
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token geçersiz"));

        if (refreshTokenService.isExpired(token)) {
            throw new RuntimeException("Refresh token süresi dolmuş");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        String newAccessToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(ApiResponse.success("Yeni token üretildi", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklist(token);
        }

        return ResponseEntity.ok(ApiResponse.success("Çıkış başarılı. Token geçersiz kılındı.", null));
    }




}
