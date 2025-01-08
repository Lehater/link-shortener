package linkshortener.presentation.dtos;

import linkshortener.domain.valueobjects.CustomUUID;

public class UserDTO {

   private final CustomUUID uuid;

    public UserDTO(CustomUUID uuid) {
        this.uuid = uuid;
    }

    public CustomUUID getUuid() {
        return uuid;
    }

}
