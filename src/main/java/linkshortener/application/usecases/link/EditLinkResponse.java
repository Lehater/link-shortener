package linkshortener.application.usecases.link;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.ShortURL;


public class EditLinkResponse {
    private final CustomUUID userUuid;
    private final ShortURL shortURL;

    public EditLinkResponse(CustomUUID userUuid, ShortURL shortURL) {
        this.userUuid = userUuid;
        this.shortURL = shortURL;
    }

    public CustomUUID getUserUuid() {
        return userUuid;
    }

    public ShortURL getShortURL() {
        return shortURL;
    }
}
