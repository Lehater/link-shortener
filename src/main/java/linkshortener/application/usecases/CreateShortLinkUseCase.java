package linkshortener.application.usecases;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
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

    public Link execute(
            User user,
            String originalUrlString,
            MaxRedirectsLimit maxRedirects,
            LocalDateTime expirationDate
    ) throws Exception {
        if (user == null) {
            throw new UnauthorizedAccessException("Нет прав для создания этой ссылки");
        }
        // Валидация входных данных
        URL originalUrl = new URL(originalUrlString);

        // Установка значений по умолчанию
        if (maxRedirects == null) {
            maxRedirects = configService.getDefaultMaxRedirects();
        }
        if (expirationDate == null) {
            expirationDate = LocalDateTime.now().plusHours(configService.getDefaultLifetimeHours());
        }

        // Создание новой ссылки
        Link link = new Link(originalUrl, user.getUuid(), maxRedirects, expirationDate);

        // Генерация короткого URL
        String shortUrl = urlShortenerService.generateShortUrl(link.getId().toString());
        link.setShortUrl(new ShortURL(shortUrl));

        // Сохранение ссылки
        linkRepository.save(link);

        return link;
    }
}
