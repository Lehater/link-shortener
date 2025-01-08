package linkshortener.application.usecases.user;

import linkshortener.application.interfaces.UserRepository;
import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.presentation.dtos.UserDTO;

public class AuthenticateUserUseCase {
    private final UserRepository userRepository;

    public AuthenticateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(CustomUUID uuid) throws UserNotFoundException {
        User user = userRepository.findByUuid(uuid);
        return toUserDTO(user);
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUuid());
    }

}