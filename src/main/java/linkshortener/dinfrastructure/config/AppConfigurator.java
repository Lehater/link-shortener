package linkshortener.dinfrastructure.config;

import linkshortener.bapplication.usecases.*;
import linkshortener.dinfrastructure.persistence.*;
import linkshortener.dinfrastructure.services.*;
import linkshortener.cadapters.controllers.AuthController;
import linkshortener.cadapters.controllers.LinkController;

public class AppConfigurator {

    public static UserRepositoryInMemory userRepository = new UserRepositoryInMemory();
    public static ConfigServicePropsFile config = new ConfigServicePropsFile("properties");

    public static LinkController getLinkController() {
        // Инициализация зависимостей
        LinkRepositoryInMemory linkRepository = new LinkRepositoryInMemory();
        NotificationServiceByCLI notificationService = new NotificationServiceByCLI();
        UrlShortenerServiceSHA256 urlShortenerService = new UrlShortenerServiceSHA256();

        // Сценарии использования
        LinksManagerUseCase linksManagerUseCase = new LinksManagerUseCase(
                linkRepository, urlShortenerService, config, notificationService
        );

        // Возвращаем контроллер
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
