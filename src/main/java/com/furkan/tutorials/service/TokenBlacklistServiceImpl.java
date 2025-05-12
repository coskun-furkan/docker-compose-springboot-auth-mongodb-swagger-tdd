package com.furkan.tutorials.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private Set<String> blacklist = new HashSet<>();

    @Override
    public void blacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

