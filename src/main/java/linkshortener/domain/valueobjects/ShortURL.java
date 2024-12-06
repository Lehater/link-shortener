package linkshortener.domain.valueobjects;

//import java.util.Objects;

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
        // Простая валидация
        return shortUrl != null && shortUrl.matches("^[a-zA-Z0-9._-]{1,20}$");
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true; // Сравнение по ссылке
//        if (o == null || getClass() != o.getClass()) return false; // Проверка класса
//        ShortURL shortURL = (ShortURL) o;
//        return Objects.equals(shortUrl, shortURL.shortUrl); // Сравнение содержимого
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(shortUrl); // Генерация хэш-кода на основе содержимого
//    }
//
//    @Override
//    public String toString() {
//        return shortUrl; // Удобное представление в виде строки
//    }
}
