package linkshortener.bapplication.usecases;

import linkshortener.bapplication.interfaces.UserRepository;
import linkshortener.adomain.entities.User;
import linkshortener.adomain.exceptions.UserNotFoundException;
import linkshortener.adomain.valueobjects.UUID;
import linkshortener.bapplication.dtos.UserDTO;

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