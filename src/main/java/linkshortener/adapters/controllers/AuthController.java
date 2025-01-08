package linkshortener.presentation.controllers;

import linkshortener.application.usecases.user.AuthenticateUserUseCase;
import linkshortener.application.usecases.user.RegisterUserUseCase;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.presentation.dtos.UserDTO;

public class AuthController {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase, RegisterUserUseCase registerUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    public UserDTO authenticate(String uuidString) {
        try {
            CustomUUID uuid = uuidString != null ? new CustomUUID(uuidString) : null;
            if (uuid == null) {
                return new UserDTO(registerUserUseCase.execute().getUuid());
            }
            return authenticateUserUseCase.execute(uuid);
        } catch (UserNotFoundException | IllegalArgumentException e) {
            return new UserDTO(registerUserUseCase.execute().getUuid());
        }
    }
}