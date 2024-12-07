package linkshortener.cadapters.mapper;

import linkshortener.adomain.entities.User;
import linkshortener.bapplication.dtos.UserDTO;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(user.getUuid());
    }
}