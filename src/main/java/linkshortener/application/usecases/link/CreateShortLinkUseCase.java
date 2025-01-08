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

    public CreateLinkResponse execute(CreateLinkRequest request) throws Exception {

        // ====== Время жизни ======
        int userHours = request.getRequestedLifetimeHours();
        int systemHours = configService.getDefaultLifetimeHours(); // например, 24

        // Если пользователь ввёл 0 или меньше => значит не указал
        if (userHours <= 0) {
            userHours = systemHours;
        }
        // Берём меньшее
        int finalLifetime = Math.min(userHours, systemHours);

        // Итоговая дата истечения
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(finalLifetime);

        // ====== Лимит переходов ======
        int userMax = request.getRequestedMaxRedirects();
        int systemMax = configService.getDefaultMaxRedirects().getLimit(); // допустим, 5

        // Если пользователь не указал => userMax <= 0 => берём systemMax
        if (userMax <= 0) {
            userMax = systemMax;
        }
        // Берём большее
        int finalMaxRedirects = Math.max(userMax, systemMax);

        // Создаём объект MaxRedirectsLimit из числа
        MaxRedirectsLimit maxRedirectsLimit = new MaxRedirectsLimit(String.valueOf(finalMaxRedirects));

        // ====== (C) Генерация короткой ссылки ======
        ShortURL shortUrl = urlShortenerService.generateShortUrl(
                request.getOriginalUrl().toString(),
                request.getUserUuid().toString()
        );

        // ====== (D) Создание доменной сущности Link ======
        Link link = new Link(
                request.getOriginalUrl(),
                request.getUserUuid(),
                maxRedirectsLimit,  // передаём только что рассчитанный лимит
                expirationDate      // и дату окончания (из пункта A)
        );
        link.setShortUrl(shortUrl);

        // Сохранить в репозиторий
        linkRepository.save(link);

        // ====== (E) Возврат результата
        return new CreateLinkResponse(
                link.getUserId(),
                link.getShortUrl(),
                link.getMaxRedirects(),
                link.getExpirationDate()
        );
    }
}
