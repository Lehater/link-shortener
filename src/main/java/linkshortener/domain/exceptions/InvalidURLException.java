package linkshortener.domain.exceptions;

public class InvalidURLException extends Exception {

    public InvalidURLException(String message) {
        super(message);
    }

}
