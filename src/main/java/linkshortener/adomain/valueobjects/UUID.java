package linkshortener.adomain.valueobjects;

public class UUID {

    private final String uuid;


    public UUID(String uuid) {
        if (!isValidID(uuid)) {
            throw new IllegalArgumentException("Некорректный UUID: " + uuid);
        }
        this.uuid = uuid;
    }

    public String toString() {
        return uuid;
    }


    private boolean isValidID(String uuid) {
        try {
            java.util.UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}