package linkshortener.infrastructure.services;

import linkshortener.application.interfaces.UrlShortenerService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UrlShortenerServiceSHA256 implements UrlShortenerService {

    @Override
    public String generateShortUrl(String linkId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(linkId.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка генерации короткой ссылки", e);
        }
    }
}
