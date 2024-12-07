package linkshortener.application.usecases;

import linkshortener.application.interfaces.UserRepository;
import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.application.dtos.UserDTO;

public class AuthenticateUserUseCase {
    private final UserRepository userRepository;

    public AuthenticateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(UUID uuid) throws UserNotFoundException {
        User user = userRepository.findByUuid(uuid);
        return toUserDTO(user);
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUuid());
    }

}