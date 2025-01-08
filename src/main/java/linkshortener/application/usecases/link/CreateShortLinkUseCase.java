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

        // 1. Получить maxRedirects из запроса
        MaxRedirectsLimit maxRedirects = createLinkRequest.getMaxRedirectsLimit();
        if (maxRedirects == null) {
            maxRedirects = configService.getDefaultMaxRedirects();
        }

        // 2. Установить expirationDate
        LocalDateTime expirationDate = createLinkRequest.getExpirationDate();
        if (expirationDate == null) {
            expirationDate = LocalDateTime.now().plusHours(configService.getDefaultLifetimeHours());
        }

        // 3. Сгенерировать короткую ссылку
        ShortURL shortUrl = urlShortenerService.generateShortUrl(
                createLinkRequest.getOriginalUrl().toString(),
                createLinkRequest.getUserUuid().toString()
        );

        // 4. Создать доменную сущность Link
        Link link = new Link(
                createLinkRequest.getOriginalUrl(),
                createLinkRequest.getUserUuid(),
                maxRedirects,
                expirationDate
        );
        link.setShortUrl(shortUrl); // Устанавливаем сгенерированную короткую ссылку

        // 5. Сохранить ссылку
        linkRepository.save(link);

        // 6. Сформировать ответ
        return new CreateLinkResponse(
                link.getUserId(),
                link.getShortUrl(),
                link.getMaxRedirects(),
                link.getExpirationDate()
        );
    }
}
