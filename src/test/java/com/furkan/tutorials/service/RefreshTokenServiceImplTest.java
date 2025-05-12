package com.furkan.tutorials.service;

import com.furkan.tutorials.model.RefreshToken;
import com.furkan.tutorials.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository repository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRefreshToken_returnsSavedToken() {
        // Yeni bir RefreshToken nesnesi oluşturup, userId alanını ayarlıyoruz.
        RefreshToken token = new RefreshToken();
        token.setUserId("user123");

        // Repository'nin save metodunun çağrıldığında, verilen RefreshToken nesnesini dönmesini sağlıyoruz.
        when(repository.save(any(RefreshToken.class))).thenAnswer(i -> i.getArguments()[0]);

        // Servis katmanındaki createRefreshToken metodunu çağırıp sonucu alıyoruz.
        RefreshToken result = refreshTokenService.createRefreshToken("user123");

        // Dönen token'ın kullanıcı kimliğinin doğru olduğunu ve token ile expiryDate alanlarının boş olmadığını doğruluyoruz.
        assertEquals("user123", result.getUserId());
        assertNotNull(result.getToken());
        assertNotNull(result.getExpiryDate());
    }

    @Test
    public void testIsExpired_true() {
        // Süresi geçmiş bir RefreshToken nesnesi oluşturuyoruz.
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(new Date(System.currentTimeMillis() - 1000)); // Geçmiş tarih

        // Token'ın süresinin gerçekten dolduğunu ve metodun true döndüğünü kontrol ediyoruz.
        assertTrue(refreshTokenService.isExpired(token));
    }

    @Test
    public void testIsExpired_false() {
        // Gelecekteki bir tarihe ayarlanmış (hala geçerli) RefreshToken nesnesi oluşturuyoruz.
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(new Date(System.currentTimeMillis() + 100000)); // Gelecek tarih

        // Token'ın süresinin dolmadığını ve metodun false döndüğünü kontrol ediyoruz.
        assertFalse(refreshTokenService.isExpired(token));
    }

    @Test
    public void testFindByToken_found() {
        // Örnek bir token oluşturuyoruz ve token alanını ayarlıyoruz.
        RefreshToken token = new RefreshToken();
        token.setToken("abc123");

        // Repository'den findByToken çağrıldığında ilgili token'ı döndürmesini simüle ediyoruz.
        when(repository.findByToken("abc123")).thenReturn(Optional.of(token));

        // Servis katmanındaki findByToken metodunu çağırıyoruz ve sonucu alıyoruz.
        Optional<RefreshToken> result = refreshTokenService.findByToken("abc123");

        // Sonucun var olduğunu ve dönen token'ın doğru olduğunu doğruluyoruz.
        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getToken());
    }

    @Test
    public void testFindByToken_notFound() {
        // Repository'nin aranan token'ı bulamadığında Optional.empty() döndürmesini sağlıyoruz.
        when(repository.findByToken("missing")).thenReturn(Optional.empty());

        // Servis katmanındaki findByToken metodunu çağırıyoruz.
        Optional<RefreshToken> result = refreshTokenService.findByToken("missing");

        // Sonucun boş olduğunu doğruluyoruz (token bulunamadı durumu).
        assertFalse(result.isPresent());
    }

}
