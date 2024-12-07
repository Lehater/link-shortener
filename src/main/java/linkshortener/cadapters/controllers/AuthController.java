package linkshortener.cadapters.controllers;

import linkshortener.bapplication.usecases.AuthenticateUserUseCase;
import linkshortener.bapplication.usecases.RegisterUserUseCase;
import linkshortener.adomain.exceptions.UserNotFoundException;
import linkshortener.adomain.valueobjects.UUID;
import linkshortener.bapplication.dtos.UserDTO;

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