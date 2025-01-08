package linkshortener.application.usecases.user;

import linkshortener.domain.valueobjects.CustomUUID;

public class CreateUser {
    private final CustomUUID uuid;

    public CreateUser(CustomUUID uuid) {
        this.uuid = uuid;
    }

    public CustomUUID getUuid() {
        return uuid;
    }

}
