package com.furkan.tutorials.service;

import com.furkan.tutorials.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userId);
    boolean isExpired(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);

}
