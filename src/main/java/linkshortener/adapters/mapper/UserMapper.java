package linkshortener.adapters.mapper;

import linkshortener.domain.entities.User;
import linkshortener.application.dtos.UserDTO;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(user.getUuid());
    }
}