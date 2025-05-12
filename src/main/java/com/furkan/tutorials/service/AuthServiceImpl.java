package com.furkan.tutorials.service;

import com.furkan.tutorials.dto.LoginResponse;
import com.furkan.tutorials.dto.RegisterRequest;
import com.furkan.tutorials.model.RefreshToken;
import com.furkan.tutorials.model.User;
import com.furkan.tutorials.repository.UserRepository;
import com.furkan.tutorials.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RefreshTokenService refreshTokenService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getEmail()); // email'i username olarak da ayarla
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return "Kullanıcı başarıyla kaydedildi!";
    }



    @Override
    public LoginResponse login(String username, String password) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Geçersiz şifre");
        }

        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponse(accessToken, refreshToken.getToken());
    }


}
