package linkshortener.domain.entities;

import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.CustomUUID;

import java.time.LocalDateTime;
import java.util.Objects;

public class Link {

    private final CustomUUID UUID; // Уникальный идентификатор ссылки
    private final URL originalUrl; // Оригинальный URL
    private ShortURL shortUrl; // Сокращенный URL
    private final CustomUUID userUUID; // Идентификатор пользователя, создавшего ссылку
    private int redirectCount; // Текущее количество переходов по ссылке
    private MaxRedirectsLimit maxRedirects; // Максимально допустимое количество переходов
    private final LocalDateTime creationDate; // Дата создания ссылки
    private final LocalDateTime expirationDate; // Дата истечения срока действия

    // Конструктор
    public Link(URL originalUrl, CustomUUID userUUID, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate) {
        this.UUID = new CustomUUID(java.util.UUID.randomUUID().toString());
        this.originalUrl = originalUrl;
        this.userUUID = userUUID;
        this.redirectCount = 0;
        this.maxRedirects = maxRedirects;
        this.creationDate = LocalDateTime.now();
        this.expirationDate = expirationDate;
    }

    // Геттеры
    public CustomUUID getId() {
        return UUID;
    }

    public URL getOriginalUrl() {
        return originalUrl;
    }

    public ShortURL getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(ShortURL shortUrl) {
        this.shortUrl = shortUrl;
    }

    public CustomUUID getUserId() {
        return userUUID;
    }

    public int getRedirectCount() {
        return redirectCount;
    }

    public MaxRedirectsLimit getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(MaxRedirectsLimit maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }


    // Логика

    public void incrementRedirectCount() {
        this.redirectCount++;
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }

    public boolean isActive() {
        return !isExpired() && redirectCount < maxRedirects.getLimit();
    }

    // Переопределение equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(UUID, link.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }
}
