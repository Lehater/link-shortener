package linkshortener.infrastructure.services;

import linkshortener.application.interfaces.UrlShortenerService;

import linkshortener.domain.valueobjects.ShortURL;

import java.util.UUID;

public class UrlShortenerServiceSHA256 implements UrlShortenerService {

    @Override
    public ShortURL generateShortUrl(String originalUrl, String userId) {
        String shortUrl = "http://clck.ru/" + UUID.randomUUID().toString().substring(0, 8);
        return new ShortURL(shortUrl);
    }
}
