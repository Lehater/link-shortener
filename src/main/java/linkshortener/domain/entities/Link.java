package linkshortener.domain.entities;

import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.UUID;

import java.time.LocalDateTime;
import java.util.Objects;

public class Link {

    private final UUID UUID; // Уникальный идентификатор ссылки
    private final URL originalUrl; // Оригинальный URL
    private ShortURL shortUrl; // Сокращенный URL
    private final UUID userUUID; // Идентификатор пользователя, создавшего ссылку
    private int redirectCount; // Текущее количество переходов по ссылке
    private MaxRedirectsLimit maxRedirects; // Максимально допустимое количество переходов
    private final LocalDateTime creationDate; // Дата создания ссылки
    private final LocalDateTime expirationDate; // Дата истечения срока действия
    private boolean isActive; // Состояние ссылки (активна/неактивна)

    // Конструктор
    public Link(URL originalUrl, UUID userUUID, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate) {
        this.UUID = new UUID(java.util.UUID.randomUUID().toString());
        this.originalUrl = originalUrl;
        this.userUUID = userUUID;
        this.redirectCount = 0;
        this.maxRedirects = maxRedirects;
        this.creationDate = LocalDateTime.now();
        this.expirationDate = expirationDate;
        this.isActive = true;
    }

    // Геттеры
    public UUID getId() {
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

    public UUID getUserId() {
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

    public boolean isActive() {
        return isActive && !isExpired();
    }

    // Логика
    public void incrementRedirectCount() {
        this.redirectCount++;
        if (this.redirectCount >= this.maxRedirects.getLimit()) {
            this.isActive = false; // Деактивация, если превышен лимит переходов
        }
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }

    public void deactivate() {
        this.isActive = false;
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
