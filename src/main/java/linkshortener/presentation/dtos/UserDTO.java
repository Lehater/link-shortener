package linkshortener.presentation.dtos;

import linkshortener.domain.valueobjects.UUID;

public class UserDTO {

   private final UUID uuid;

    public UserDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}
