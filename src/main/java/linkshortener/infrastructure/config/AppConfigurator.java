package linkshortener.infrastructure.config;

import linkshortener.application.usecases.link.*;
import linkshortener.application.usecases.user.AuthenticateUserUseCase;
import linkshortener.application.usecases.user.ManageUUIDUseCase;
import linkshortener.application.usecases.user.RegisterUserUseCase;
import linkshortener.infrastructure.persistence.*;
import linkshortener.infrastructure.services.*;
import linkshortener.presentation.controllers.AuthController;
import linkshortener.presentation.controllers.LinkController;

public class AppConfigurator {

    public static UserRepositoryInMemory userRepository = new UserRepositoryInMemory();

    public static LinkController getLinkController() {
        // Инициализация зависимостей
        LinkRepositoryInMemory linkRepository = new LinkRepositoryInMemory();
        NotificationServiceByCLI notificationService = new NotificationServiceByCLI();

        // файл "properties" в ресурсах
        ConfigServicePropsFile configService = new ConfigServicePropsFile("properties");
        UrlShortenerServiceSHA256 urlShortenerService = new UrlShortenerServiceSHA256();

        // Сценарии использования
        CreateShortLinkUseCase createShortLinkUseCase = new CreateShortLinkUseCase(linkRepository,
                urlShortenerService, configService);
        EditShortLinkUseCase editShortLinkUseCase = new EditShortLinkUseCase(linkRepository);
        DeleteShortLinkUseCase deleteShortLinkUseCase = new DeleteShortLinkUseCase(linkRepository);
        RedirectLinkUseCase redirectLinkUseCase = new RedirectLinkUseCase(linkRepository, notificationService);
        ManageUUIDUseCase manageUUIDUseCase = new ManageUUIDUseCase(userRepository, linkRepository);
        ListUserLinksUseCase listUserLinksUseCase = new ListUserLinksUseCase(linkRepository);

        // Возвращаем контроллер
        return new LinkController(
                createShortLinkUseCase,
                editShortLinkUseCase,
                deleteShortLinkUseCase,
                redirectLinkUseCase,
                manageUUIDUseCase,
                listUserLinksUseCase
        );
    }

    public static AuthController getAuthController() {
        // Аутентификация
        AuthenticateUserUseCase authenticateUserUseCase = new AuthenticateUserUseCase(userRepository);
        RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(userRepository);

        // Возвращаем контроллер
        return new AuthController(authenticateUserUseCase,registerUserUseCase);
    }


}
