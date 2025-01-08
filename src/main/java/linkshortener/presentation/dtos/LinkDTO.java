package linkshortener.presentation.dtos;


public class LinkDTO {

    private final String shortUrl;
    private final String userUUID;

    public LinkDTO(String userUUID,String shortUrl) {
        this.userUUID = userUUID;
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getUserUuid() {
        return userUUID;
    }

}
