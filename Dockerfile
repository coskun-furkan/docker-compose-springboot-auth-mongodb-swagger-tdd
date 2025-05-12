# Base image
FROM eclipse-temurin:23-jdk-alpine

# Uygulama klasörünü oluştur
WORKDIR /app

# Bağımlılıkları ve kaynakları kopyala
COPY target/*.jar app.jar

# Port tanımı
EXPOSE 8080

# Çalıştırma komutu
ENTRYPOINT ["java", "-jar", "app.jar"]



