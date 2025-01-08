package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.CustomUUID;

import java.time.LocalDateTime;

public class CreateLinkResponse {

    private final CustomUUID userUUID;
    private final ShortURL shortURL;
    private final MaxRedirectsLimit maxRedirects;
    private final LocalDateTime expirationDate;

    public CreateLinkResponse(
            CustomUUID userUUID, ShortURL shortURL, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate
    ) {
        this.userUUID = userUUID;
        this.shortURL = shortURL;
        this.maxRedirects = maxRedirects;
        this.expirationDate = expirationDate;
    }

    public CustomUUID getUserUuid() {
        return userUUID;
    }

    public ShortURL getShortURL() {
        return shortURL;
    }

    public MaxRedirectsLimit getMaxRedirectsLimit() {
        return maxRedirects;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
