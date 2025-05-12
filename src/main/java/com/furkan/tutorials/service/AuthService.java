package com.furkan.tutorials.service;

import com.furkan.tutorials.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(String username, String password);
}

