package com.furkan.tutorials.service;

public interface TokenBlacklistService {
    void blacklist(String token);
    boolean isBlacklisted(String token);
}

