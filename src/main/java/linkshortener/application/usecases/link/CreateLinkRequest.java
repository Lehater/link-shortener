package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;

import java.time.LocalDateTime;
import java.util.Objects;


public class CreateLinkRequest {

    private final CustomUUID userUUID;
    private final URL originalUrl;
    private MaxRedirectsLimit maxRedirects;
    private LocalDateTime expirationDate;

    public CreateLinkRequest(CustomUUID userUUID, URL originalUrl) {
        this(userUUID, originalUrl, null, null);
    }

    public CreateLinkRequest(
            CustomUUID userUUID, URL originalUrl, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate
    ) {
        this.userUUID = Objects.requireNonNull(userUUID, "userUUID is required");

        if (originalUrl == null) {
            throw new IllegalArgumentException("originalUrl is required");
        }
        this.originalUrl = originalUrl;

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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
