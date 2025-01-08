package linkshortener.domain.valueobjects;

import linkshortener.domain.exceptions.InvalidURLException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class URL {

    private final String url;

    public URL(String url) throws InvalidURLException {
        if (!isValidUrl(url)) {
            throw new InvalidURLException("Некорректный URL: " + url);
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    private boolean isValidUrl(String url) {
        try {
            java.net.URL u = new java.net.URL(url);
            u.toURI();
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public String toString(){

        return this.url;
    }

}
