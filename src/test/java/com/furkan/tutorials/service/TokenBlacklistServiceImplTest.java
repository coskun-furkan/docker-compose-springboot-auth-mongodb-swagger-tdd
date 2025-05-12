package com.furkan.tutorials.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class TokenBlacklistServiceImplTest {

    private TokenBlacklistServiceImpl blacklistService;

    @BeforeEach
    void setUp() {
        blacklistService = new TokenBlacklistServiceImpl();
    }

    @Test
    public void testBlacklist_addsToken() {
        // Sisteme kara listeye eklenecek örnek bir token oluşturuyoruz.
        String token = "token123";

        // blacklist metoduyla oluşturduğumuz token'ı kara listeye ekliyoruz.
        blacklistService.blacklist(token);

        // Token'ın gerçekten kara listeye eklenip eklenmediğini kontrol ediyoruz.
        assertTrue(blacklistService.isBlacklisted(token));
    }

    @Test
    public void testIsBlacklisted_falseWhenNotAdded() {
        // Daha önce sisteme eklenmemiş bir örnek token oluşturuyoruz.
        String token = "notBlacklistedToken";

        // Sisteme eklenmemiş olan bu token'ın kara listede olmadığını doğruluyoruz.
        assertFalse(blacklistService.isBlacklisted(token));
    }

}
