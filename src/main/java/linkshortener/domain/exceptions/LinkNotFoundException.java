package linkshortener.domain.exceptions;

public class LinkNotFoundException extends Exception {

    public LinkNotFoundException(String message) {
        super(message);
    }

}
