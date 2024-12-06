package linkshortener.application.usecases;

import linkshortener.application.interfaces.UserRepository;
import linkshortener.domain.entities.User;

import linkshortener.domain.valueobjects.UUID;
import linkshortener.presentation.dtos.UserDTO;


public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute() {
        UUID newUuid = new UUID(java.util.UUID.randomUUID().toString());
        User newUser = new User(newUuid);
        userRepository.save(newUser);
        return toUserDTO(newUser);
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUuid());
    }
}