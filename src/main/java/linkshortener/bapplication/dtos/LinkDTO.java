package linkshortener.bapplication.dtos;

import linkshortener.adomain.valueobjects.MaxRedirectsLimit;
import linkshortener.adomain.valueobjects.ShortURL;
import linkshortener.adomain.valueobjects.UUID;

public class LinkDTO {

    private final ShortURL shortUrl;
    private final UUID userUUID;
    private final MaxRedirectsLimit maxRedirectsLimit;

    public LinkDTO(ShortURL shortUrl, UUID userUUID, MaxRedirectsLimit maxRedirectsLimit) {
        this.shortUrl = shortUrl;
        this.userUUID = userUUID;
        this.maxRedirectsLimit = maxRedirectsLimit;
    }

    public ShortURL getShortUrl() {
        return shortUrl;
    }

    public UUID getUserUuid() {
        return userUUID;
    }

    public MaxRedirectsLimit getMaxRedirectsLimit() {
        return maxRedirectsLimit;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ShortUrl: ").append(shortUrl);
        sb.append(", MaxRedirectLimit: ").append(maxRedirectsLimit);
        return sb.toString();

    }

}
