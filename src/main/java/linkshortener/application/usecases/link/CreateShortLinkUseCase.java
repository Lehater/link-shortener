package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
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

        // 1. Получить maxRedirects из запроса
        MaxRedirectsLimit maxRedirects = createLinkRequest.getMaxRedirectsLimit();
        // Если не задан (null) — берём дефолт из configService
        if (maxRedirects == null) {
            maxRedirects = configService.getDefaultMaxRedirects();
        }

        // 2. Аналогично обработать expirationDate
        LocalDateTime expirationDate = createLinkRequest.getExpirationDate();
        if (expirationDate == null) {
            expirationDate = LocalDateTime.now().plusHours(configService.getDefaultLifetimeHours());
        }

        // 3. Создать доменную сущность Link, передав уже «готовые» значения
        Link link = new Link(
                createLinkRequest.getOriginalUrl(),
                createLinkRequest.getUserUuid(),  // или getUserUuid(), в зависимости от вашей структуры
                maxRedirects,
                expirationDate
        );

        // 4. Сохранить ссылку (если нужно)
        linkRepository.save(link);

        // 5. Сформировать ответ (CreateLinkResponse) и вернуть наружу
        return new CreateLinkResponse(
                link.getUserId(),
                link.getShortUrl(),
                link.getMaxRedirects(),
                link.getExpirationDate()
        );
    }
}
