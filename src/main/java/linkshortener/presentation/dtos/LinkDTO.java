package linkshortener.presentation.dtos;

import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.UUID;

public class LinkDTO {

    private final ShortURL shortUrl;
    private final UUID userUUID;

    public LinkDTO(ShortURL shortUrl, UUID userUUID) {
        this.shortUrl = shortUrl;
        this.userUUID = userUUID;
    }

    public ShortURL getShortUrl() {
        return shortUrl;
    }

    public UUID getUserUuid() {
        return userUUID;
    }

}
