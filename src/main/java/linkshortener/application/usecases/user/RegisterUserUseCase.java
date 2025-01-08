package linkshortener.application.usecases.user;

import linkshortener.application.interfaces.UserRepository;
import linkshortener.domain.entities.User;

import linkshortener.domain.valueobjects.CustomUUID;


public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUser execute() {
        CustomUUID newUuid = new CustomUUID(java.util.UUID.randomUUID().toString());
        User newUser = new User(newUuid);
        userRepository.save(newUser);
        return toUserDTO(newUser);
    }

    private CreateUser toUserDTO(User user) {
        return new CreateUser(user.getUuid());
    }
}