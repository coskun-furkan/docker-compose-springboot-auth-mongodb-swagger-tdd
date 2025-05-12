# Güvenli Spring Boot API

Merhaba, ben Furkan. Bu proje, Java ve Spring Boot kullanarak kimlik doğrulamanın nasıl çalıştığını daha iyi anlamak için geliştirdiğim bir backend uygulamasıdır.

Bu uygulamanın temel amacı; kullanıcı kaydı, giriş işlemleri, JWT tabanlı kimlik doğrulama, token yenileme ve kara liste (blacklist) yönetimini içeren, güvenli ve test edilebilir bir REST API oluşturmaktı. Gerçek hayattaki kullanım senaryolarını daha iyi canlandırmak için basit bir “tutorial” yönetimi de ekledim.

# İçerik

Bu projede şunlar yer alıyor:

- Kullanıcı kaydı ve giriş
- JWT ile kimlik doğrulama (access & refresh token desteği)
- Çıkış işlemleri için kara liste (token blacklist) sistemi
- MongoDB ile kullanıcı ve içerik verisi yönetimi
- Tutorial objesi üzerinden CRUD işlemleri
- Swagger UI ile API dokümantasyonu ve test arayüzü
- Docker Compose ile konteyner tabanlı çalışma ortamı
- `.env` dosyasıyla dış yapılandırma desteği
- JUnit ve Mockito ile servis katmanında birim testler

# Test Süreci

Servis katmanında yer alan her işlev, JUnit ve Mockito kullanılarak birim testlerle kapsam altına alındı.  
Özellikle kimlik doğrulama (auth) sürecinde test odaklı geliştirme (TDD) yaklaşımına uymaya çalıştım.  
Önce test yazmak, daha sonra kodu buna göre şekillendirmek, uygulamanın hem daha net hem daha hatasız olmasını sağladı.

# Klasör Yapısı

```
src
├── main
│   ├── java
│   │   └── com.furkan.tutorials
│   │       ├── controller
│   │       │   ├── AuthController.java
│   │       │   └── TutorialController.java
│   │       ├── dto
│   │       │   ├── ApiResponse.java
│   │       │   ├── LoginResponse.java
│   │       │   └── RegisterRequest.java
│   │       ├── exception
│   │       │   └── GlobalExceptionHandler.java
│   │       ├── model
│   │       │   ├── User.java
│   │       │   ├── Tutorial.java
│   │       │   └── RefreshToken.java
│   │       ├── repository
│   │       │   ├── UserRepository.java
│   │       │   ├── TutorialRepository.java
│   │       │   └── RefreshTokenRepository.java
│   │       ├── security
│   │       │   ├── JwtFilter.java
│   │       │   ├── JwtUtil.java
│   │       │   └── SecurityConfig.java
│   │       ├── service
│   │       │   ├── AuthService.java
│   │       │   ├── AuthServiceImpl.java
│   │       │   ├── RefreshTokenService.java
│   │       │   ├── RefreshTokenServiceImpl.java
│   │       │   ├── TokenBlacklistService.java
│   │       │   ├── TokenBlacklistServiceImpl.java
│   │       │   ├── TutorialService.java
│   │       │   └── TutorialServiceImpl.java
│   │       └── TutorialsApplication.java
│   └── resources
│       ├── application.properties
│       └── static (if needed)
├── test
│   └── java
│       └── com.furkan.tutorials
│           └── service
│               ├── AuthServiceImplTest.java
│               ├── RefreshTokenServiceTest.java
│               └── TutorialServiceTest.java
.env
docker-compose.yml
README.md
.gitignore
```

# Uygulamayı Çalıştırma

Uygulamayı Docker üzerinden çalıştırmak için şu komutu kullanabilirsin:

```
docker-compose up --build
```

Başlamadan önce, proje dizinine bir `.env` dosyası oluşturman gerekiyor:

```
MONGO_URI=mongodb bağlantı adresin
JWT_SECRET=jwt için gizli anahtarın
```

# API Dokümantasyonu

Uygulama başladıktan sonra Swagger arayüzüne şu adresten ulaşabilirsin:

http://localhost:8080/swagger-ui/index.html

# Son Notlar

Bu proje, hem teknik bilgimi geliştirmek hem de gerçek dünyadaki backend ihtiyaçlarını daha iyi anlamak için oluşturduğum bir çalışmadır.  
Geliştirmeye ve öğrenmeye devam ediyorum. Geri bildirimlere açığım.

– Furkan