package com.furkan.tutorials.service;

import com.furkan.tutorials.model.RefreshToken;
import com.furkan.tutorials.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private RefreshTokenRepository repository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(new Date(System.currentTimeMillis()+86400000));  // 1 gün
        return repository.save(token);
    }

    @Override
    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

}
