package linkshortener.application.interfaces;

import linkshortener.domain.valueobjects.ShortURL;

public interface UrlShortenerService {
    ShortURL generateShortUrl(String originalUrl, String userId);
}
