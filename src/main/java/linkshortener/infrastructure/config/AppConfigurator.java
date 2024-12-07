package linkshortener.infrastructure.config;

import linkshortener.application.usecases.*;
import linkshortener.infrastructure.persistence.*;
import linkshortener.infrastructure.scheduler.LinkCleanupScheduler;
import linkshortener.infrastructure.services.*;
import linkshortener.adapters.controllers.AuthController;
import linkshortener.adapters.controllers.LinkController;

public class AppConfigurator {

    private static final UserRepositoryInMemory userRepository = new UserRepositoryInMemory();
    private static final ConfigServicePropsFile config = new ConfigServicePropsFile("properties");

    // Инициализация зависимостей
    private static final LinkRepositoryInMemory linkRepository = new LinkRepositoryInMemory();
    private static final NotificationServiceByCLI notificationService = new NotificationServiceByCLI();
    private static final UrlShortenerServiceSHA256 urlShortenerService = new UrlShortenerServiceSHA256();

    // Сценарии использования
    private static final LinksManagerUseCase linksManagerUseCase = new LinksManagerUseCase(
            linkRepository, urlShortenerService, config, notificationService
    );

    public static LinkCleanupScheduler getScheduler() {
        return new LinkCleanupScheduler(linksManagerUseCase);
    }

    public static LinkController getLinkController() {
        return new LinkController(linksManagerUseCase);
    }

    public static AuthController getAuthController() {
        // Аутентификация
        AuthenticateUserUseCase authenticateUserUseCase = new AuthenticateUserUseCase(userRepository);
        RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(userRepository);

        // Возвращаем контроллер
        return new AuthController(authenticateUserUseCase, registerUserUseCase);
    }


}
