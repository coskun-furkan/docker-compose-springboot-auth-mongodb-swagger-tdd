package com.furkan.tutorials.service;

import com.furkan.tutorials.dto.RegisterRequest;
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

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return JwtUtil.generateToken(user);
        } else {
            throw new RuntimeException("Hatalı şifre");
        }
    }
}
