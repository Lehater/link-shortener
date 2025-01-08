package linkshortener.domain.valueobjects;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Сравнение ссылок
        if (o == null || getClass() != o.getClass()) return false; // Разные классы
        ShortURL shortURL = (ShortURL) o;
        return shortUrl.equals(shortURL.shortUrl); // Сравнение строк
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl); // Генерация хэш-кода на основе строки
    }

    @Override
    public String toString() {
        return shortUrl;
    }

}
