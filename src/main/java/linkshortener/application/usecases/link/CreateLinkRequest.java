package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;

import java.util.Objects;

public class CreateLinkRequest {

    private final CustomUUID userUUID;
    private final URL originalUrl;
    private final MaxRedirectsLimit maxRedirects;

    // Пользователь может указать желаемое время жизни ссылки (в часах).
    // Если 0 (или отрицательно), значит пользователь ничего не указал —
    // тогда будем использовать значение из конфигурации.
    private final int requestedLifetimeHours;

    /**
     * Конструктор, когда пользователь не указывает ни лимит переходов, ни время жизни.
     */
    public CreateLinkRequest(CustomUUID userUUID, URL originalUrl) {
        this(userUUID, originalUrl, null, 0);
    }

    /**
     * Основной конструктор
     *
     * @param userUUID             UUID пользователя
     * @param originalUrl          Исходный (длинный) URL
     * @param maxRedirects         Обёртка для лимита переходов (может быть null, если не указано)
     * @param requestedLifetimeHours Сколько часов хочет пользователь (0 или < 0, если не указал)
     */
    public CreateLinkRequest(
            CustomUUID userUUID,
            URL originalUrl,
            MaxRedirectsLimit maxRedirects,
            int requestedLifetimeHours
    ) {
        // Проверка обязательных полей
        this.userUUID = Objects.requireNonNull(userUUID, "userUUID is required");
        if (originalUrl == null) {
            throw new IllegalArgumentException("originalUrl is required");
        }
        this.originalUrl = originalUrl;

        // Остальные поля могут быть не заданы
        this.maxRedirects = maxRedirects;
        this.requestedLifetimeHours = requestedLifetimeHours;
    }

    public CustomUUID getUserUuid() {
        return userUUID;
    }

    public URL getOriginalUrl() {
        return originalUrl;
    }

    public MaxRedirectsLimit getMaxRedirectsLimit() {
        return maxRedirects;
    }

    /**
     * Возвращает желаемое время жизни ссылки в часах (может быть 0 или отрицательно,
     * если пользователь ничего не указал).
     */
    public int getRequestedLifetimeHours() {
        return requestedLifetimeHours;
    }
}
