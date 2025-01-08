package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;

import java.time.LocalDateTime;
import java.util.Objects;


public class EditLinkRequest {

    private final CustomUUID userUUID;
    private final ShortURL shortUrl;
    private MaxRedirectsLimit maxRedirects;
    private LocalDateTime expirationDate;

    public EditLinkRequest(
            CustomUUID userUUID, ShortURL shortUrl, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate
    ) {
        this.userUUID = Objects.requireNonNull(userUUID, "userUUID is required");

        if (shortUrl == null) {
            throw new IllegalArgumentException("shortUrl is required");
        }
        this.shortUrl = shortUrl;

    }

    public CustomUUID getUserUuid() {
        return userUUID;
    }

    public ShortURL getShortUrl() {
        return shortUrl;
    }

    public MaxRedirectsLimit getMaxRedirectsLimit() {
        return maxRedirects;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
