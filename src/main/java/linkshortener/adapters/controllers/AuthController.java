package linkshortener.adapters.controllers;

import linkshortener.application.usecases.AuthenticateUserUseCase;
import linkshortener.application.usecases.RegisterUserUseCase;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.application.dtos.UserDTO;

public class AuthController {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase, RegisterUserUseCase registerUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    public UserDTO authenticate(String uuidString) {
        try {
            UUID uuid = uuidString != null ? new UUID(uuidString) : null;
            if (uuid == null) {
                return registerUserUseCase.execute();
            }
            return authenticateUserUseCase.execute(uuid);
        } catch (UserNotFoundException | IllegalArgumentException e) {
            return registerUserUseCase.execute();
        }
    }
}