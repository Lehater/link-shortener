package linkshortener.presentation.dtos;

import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.CustomUUID;

public class LinkDTO {

    private final ShortURL shortUrl;
    private final CustomUUID userUUID;

    public LinkDTO(CustomUUID userUUID,ShortURL shortUrl) {
        this.userUUID = userUUID;
        this.shortUrl = shortUrl;
    }

    public ShortURL getShortUrl() {
        return shortUrl;
    }

    public CustomUUID getUserUuid() {
        return userUUID;
    }

}
