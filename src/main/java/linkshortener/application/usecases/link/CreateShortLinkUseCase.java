package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;
import linkshortener.application.interfaces.*;

import java.time.LocalDateTime;


public class CreateShortLinkUseCase {

    private final LinkRepository linkRepository;
    private final UrlShortenerService urlShortenerService;
    private final ConfigService configService;

    public CreateShortLinkUseCase(
            LinkRepository linkRepository,
            UrlShortenerService urlShortenerService,
            ConfigService configService
    ) {
        this.linkRepository = linkRepository;
        this.urlShortenerService = urlShortenerService;
        this.configService = configService;
    }

    public CreateLinkResponse execute(CreateLinkRequest createLinkRequest) throws Exception {

        // 1. Определить лимит переходов (как раньше)
        MaxRedirectsLimit maxRedirects = createLinkRequest.getMaxRedirectsLimit();
        if (maxRedirects == null) {
            maxRedirects = configService.getDefaultMaxRedirects();
        }

        // 2. Получаем, сколько часов хочет пользователь (может быть 0, -1 или не задано)
        int userLifetimeHours = createLinkRequest.getRequestedLifetimeHours();
        // Допустим, если у пользователя ничего не введено, возвращается 0.

        // 3. Получаем системный срок жизни (из конфиг-файла), скажем 24 часа
        int systemLifetimeHours = configService.getDefaultLifetimeHours();

        // 4. Если пользователь не задал время (userLifetimeHours <= 0),
        //    то берём целиком systemLifetimeHours.
        if (userLifetimeHours <= 0) {
            userLifetimeHours = systemLifetimeHours;
        }

        // 5. Выбираем меньшее из пользовательского и системного
        int finalLifetimeHours = Math.min(userLifetimeHours, systemLifetimeHours);

        // 6. Рассчитываем итоговую дату истечения
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(finalLifetimeHours);

        // 7. Генерируем короткую ссылку (как раньше)
        ShortURL shortUrl = urlShortenerService.generateShortUrl(
                createLinkRequest.getOriginalUrl().toString(),
                createLinkRequest.getUserUuid().toString()
        );

        // 8. Создаём сущность Link
        Link link = new Link(
                createLinkRequest.getOriginalUrl(),
                createLinkRequest.getUserUuid(),
                maxRedirects,
                expirationDate  // тут уже готовая дата: now + min(пользователь, системная)
        );
        link.setShortUrl(shortUrl);

        // 9. Сохраняем ссылку
        linkRepository.save(link);

        // 10. Возвращаем ответ
        return new CreateLinkResponse(
                link.getUserId(),
                link.getShortUrl(),
                link.getMaxRedirects(),
                link.getExpirationDate()
        );
    }
}
