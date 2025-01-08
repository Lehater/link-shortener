package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;

import java.util.Objects;

public class CreateLinkRequest {

    private final CustomUUID userUUID;
    private final URL originalUrl;

    // Пользователь может ввести лимит переходов сам,
    // 0 или отрицательное число = не указал => берем системное
    private final int requestedMaxRedirects;

    // Пользователь может ввести время жизни (часов),
    // 0 или отрицательное число = не указал => берем системное
    private final int requestedLifetimeHours;

    public CreateLinkRequest(CustomUUID userUUID, URL originalUrl) {
        this(userUUID, originalUrl, 0, 0);
    }

    /**
     * Основной конструктор
     *
     * @param userUUID - UUID пользователя
     * @param originalUrl - исходный URL
     * @param requestedMaxRedirects - желаемый лимит переходов
     * @param requestedLifetimeHours - желаемое время жизни (часов)
     */
    public CreateLinkRequest(
            CustomUUID userUUID,
            URL originalUrl,
            int requestedMaxRedirects,
            int requestedLifetimeHours
    ) {
        this.userUUID = Objects.requireNonNull(userUUID, "userUUID is required");
        if (originalUrl == null) {
            throw new IllegalArgumentException("originalUrl is required");
        }
        this.originalUrl = originalUrl;

        this.requestedMaxRedirects = requestedMaxRedirects;
        this.requestedLifetimeHours = requestedLifetimeHours;
    }

    public CustomUUID getUserUuid() {
        return userUUID;
    }

    public URL getOriginalUrl() {
        return originalUrl;
    }

    public int getRequestedMaxRedirects() {
        return requestedMaxRedirects;
    }

    public int getRequestedLifetimeHours() {
        return requestedLifetimeHours;
    }
}
