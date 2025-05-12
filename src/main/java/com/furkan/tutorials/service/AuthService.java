package com.furkan.tutorials.service;

import com.furkan.tutorials.dto.LoginResponse;
import com.furkan.tutorials.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    LoginResponse login(String username, String password);
}

