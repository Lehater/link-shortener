package linkshortener.bapplication.usecases;

import linkshortener.adomain.entities.Link;
import linkshortener.adomain.exceptions.LinkNotFoundException;
import linkshortener.adomain.exceptions.UnauthorizedAccessException;
import linkshortener.adomain.valueobjects.MaxRedirectsLimit;
import linkshortener.adomain.valueobjects.ShortURL;
import linkshortener.adomain.valueobjects.URL;
import linkshortener.adomain.valueobjects.UUID;
import linkshortener.bapplication.dtos.LinkDTO;
import linkshortener.bapplication.dtos.UserDTO;
import linkshortener.bapplication.interfaces.ConfigService;
import linkshortener.bapplication.interfaces.LinkRepository;
import linkshortener.bapplication.interfaces.NotificationService;
import linkshortener.bapplication.interfaces.UrlShortenerService;
import linkshortener.cadapters.mapper.LinkMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LinksManagerUseCase {

    private final LinkRepository linkRepository;
    private final UrlShortenerService urlShortenerService;
    private final ConfigService configService;
    private final NotificationService notificationService;

    public LinksManagerUseCase(
            LinkRepository linkRepository,
            UrlShortenerService urlShortenerService,
            ConfigService configService,
            NotificationService notificationService
    ) {
        this.linkRepository = linkRepository;
        this.urlShortenerService = urlShortenerService;
        this.configService = configService;
        this.notificationService = notificationService;
    }

    public LinkDTO create(
            UserDTO userDTO,
            String originalUrlString,
            MaxRedirectsLimit maxRedirects,
            LocalDateTime expirationDate
    ) throws Exception {
        if (userDTO == null) {
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
        Link link = new Link(originalUrl, userDTO.getUuid(), maxRedirects, expirationDate);

        // Генерация короткого URL
        String origText = userDTO.getUuid().toString() + originalUrl;
        String shortUrl = urlShortenerService.generateShortUrl(origText);
        link.setShortUrl(new ShortURL(shortUrl));

        // Сохранение ссылки
        linkRepository.save(link);

        return LinkMapper.toDto(link);
    }

    public LinkDTO update(
            UserDTO userDTO,
            ShortURL shortUrl,
            MaxRedirectsLimit newMaxRedirects
    ) throws LinkNotFoundException, UnauthorizedAccessException {

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Обновление параметров ссылки
        if (newMaxRedirects != null) {
            link.setMaxRedirects(newMaxRedirects);
        }

        // Сохранение изменений
        return LinkMapper.toDto(link);
    }

    public void delete(ShortURL shortUrl) throws LinkNotFoundException, UnauthorizedAccessException {

        if (shortUrl == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(shortUrl);

        // Удаление ссылки
        linkRepository.delete(link);

    }

    public String getOriginalUrl(ShortURL shortUrl) throws LinkNotFoundException {

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Проверка активности ссылки
        if (!link.isActive()) {
            throw new IllegalStateException("Ссылка недоступна");
        }

        // Увеличение счетчика переходов
        link.incrementRedirectCount();
        linkRepository.update(link);

        // Проверка достижений лимитов
        if (!link.isActive()) {
            notificationService.sendNotification(
                    link.getUserId().toString(),
                    "Лимит переходов по вашей ссылке исчерпан или срок действия истек."
            );
        }

        // Возврат оригинального URL
        return link.getOriginalUrl().getUrl();
    }

    public List<LinkDTO> getAllByUser(UUID userUuid) {

        List<Link> links = linkRepository.findAllByUser(userUuid);

        return links.stream().map(LinkMapper::toDto).collect(Collectors.toList());
    }
}
