package linkshortener.bapplication.usecases;

import linkshortener.bapplication.interfaces.UserRepository;
import linkshortener.adomain.entities.User;

import linkshortener.adomain.valueobjects.UUID;
import linkshortener.bapplication.dtos.UserDTO;


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