package linkshortener.dinfrastructure.services;

import linkshortener.bapplication.interfaces.UrlShortenerService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UrlShortenerServiceSHA256 implements UrlShortenerService {

    @Override
    public String generateShortUrl(String originalText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalText.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка генерации короткой ссылки", e);
        }
    }
}
