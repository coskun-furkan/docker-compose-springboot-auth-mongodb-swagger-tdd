package com.furkan.tutorials.service;

import com.furkan.tutorials.dto.LoginResponse;
import com.furkan.tutorials.dto.RegisterRequest;
import com.furkan.tutorials.model.RefreshToken;
import com.furkan.tutorials.model.User;
import com.furkan.tutorials.repository.UserRepository;
import com.furkan.tutorials.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_success() {
        // Yeni kullanıcı kaydı için bir RegisterRequest nesnesi oluşturup gerekli alanları dolduruyoruz.
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole("USER");

        // Kullanıcıyı kaydetme işleminin repository üzerinden çağrıldığında kullanıcı nesnesini dönmesini simüle ediyoruz.
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // AuthService'teki register metodunu çağırarak sonucu alıyoruz.
        String result = authService.register(request);

        // Dönen mesajın beklenen mesaj olup olmadığını doğruluyoruz.
        assertEquals("Kullanıcı başarıyla kaydedildi!", result);
        // save metodunun bir kere çağrıldığını doğruluyoruz.
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin_success() {
        // Sistemde kayıtlı olan bir kullanıcı oluşturup, şifresini BCrypt ile hashleyerek ayarlıyoruz.
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password123"));

        // Repository'den kullanıcı sorgusu yapıldığında ilgili kullanıcıyı dönmesini simüle ediyoruz.
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        // JWT token oluşturma işlemini simüle ediyoruz.
        when(jwtUtil.generateToken(user)).thenReturn("fake-jwt-token");

        // Refresh token oluşturma işlemini simüle ediyoruz.
        RefreshToken mockToken = new RefreshToken();
        mockToken.setToken("fake-refresh-token");
        when(refreshTokenService.createRefreshToken("123")).thenReturn(mockToken);

        // AuthService'teki login metodunu çağırıp sonucu alıyoruz.
        LoginResponse response = authService.login("test@example.com", "password123");

        // Dönen JWT ve refresh token'ların beklenen değerler olup olmadığını kontrol ediyoruz.
        assertEquals("fake-jwt-token", response.getAccessToken());
        assertEquals("fake-refresh-token", response.getRefreshToken());
    }

    @Test
    public void testLogin_invalidPassword_throwsException() {
        // Kayıtlı bir kullanıcı oluşturup şifresini doğru bir biçimde ayarlıyoruz.
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password123"));

        // Repository'nin kullanıcı sorgusunda kullanıcıyı döndürmesini sağlıyoruz.
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Hatalı şifre ile giriş yapılmaya çalışıldığında RuntimeException atıldığını kontrol ediyoruz.
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login("test@example.com", "wrongpassword");
        });

        // Atılan exception mesajının doğru olduğundan emin oluyoruz.
        assertEquals("Geçersiz şifre", ex.getMessage());
    }

    @Test
    public void testLogin_userNotFound_throwsException() {
        // Repository'den yapılan kullanıcı sorgusunda kullanıcı bulunamadığında boş sonuç dönmesini simüle ediyoruz.
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Var olmayan bir kullanıcı ile giriş yapılmaya çalışıldığında RuntimeException atıldığını doğruluyoruz.
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login("notfound@example.com", "anyPassword");
        });

        // Atılan exception mesajının doğru olduğunu doğruluyoruz.
        assertEquals("Kullanıcı bulunamadı", ex.getMessage());
    }

}
