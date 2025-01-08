package linkshortener.domain.valueobjects;

public class ShortURL {

    private final String shortUrl;

    public ShortURL(String shortUrl) {
        if (!isValidShortUrl(shortUrl)) {
            throw new IllegalArgumentException("Некорректный сокращенный URL: " + shortUrl);
        }
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    private boolean isValidShortUrl(String shortUrl) {
        if (shortUrl == null) {
            return false;
        }
        // Поддерживаем варианты:
        // 1) clck.ru/XXXX
        // 2) http://clck.ru/XXXX
        // 3) https://clck.ru/XXXX
        // где XXXX – 1-20 символов (латиница и/или цифры)
        String pattern = "^(?:https?://)?clck\\.ru/[A-Za-z0-9]{1,20}$";
        return shortUrl.matches(pattern);
    }

    public String toString() {
        return shortUrl;
    }

}
