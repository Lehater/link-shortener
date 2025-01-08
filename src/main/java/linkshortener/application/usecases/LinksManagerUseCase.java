package linkshortener.application.usecases;

import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.application.dtos.LinkDTO;
import linkshortener.application.dtos.UserDTO;
import linkshortener.application.interfaces.ConfigService;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.application.interfaces.NotificationService;
import linkshortener.application.interfaces.UrlShortenerService;
import linkshortener.adapters.mapper.LinkMapper;

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

    private void validateUser(UserDTO userDTO) throws UnauthorizedAccessException {
        if (userDTO == null) {
            throw new UnauthorizedAccessException("Нет прав для создания этой ссылки");
        }
    }

    private MaxRedirectsLimit setDefaultMaxRedirectsIfNull(MaxRedirectsLimit maxRedirects) {
        return maxRedirects != null ? maxRedirects : configService.getDefaultMaxRedirects();
    }

    private LocalDateTime setDefaultExpirationDateIfNull(LocalDateTime expirationDate) {
        return expirationDate != null ? expirationDate : LocalDateTime.now().plusHours(
                configService.getDefaultLifetimeHours());
    }

    private Link generateNewLink(
            UserDTO userDTO, URL originalUrl, MaxRedirectsLimit maxRedirects, LocalDateTime expirationDate
    ) throws Exception {
        Link link = new Link(originalUrl, userDTO.getUuid(), maxRedirects, expirationDate);
        String shortUrl = urlShortenerService.generateShortUrl(userDTO.getUuid() + originalUrl.getUrl());
        link.setShortUrl(new ShortURL(shortUrl));
        return link;
    }

    public LinkDTO create(
            UserDTO userDTO,
            String originalUrlString,
            MaxRedirectsLimit maxRedirects,
            LocalDateTime expirationDate
    ) throws Exception {

        // Валидация входных данных
        validateUser(userDTO);

        URL originalUrl = new URL(originalUrlString);

        // Установка значений по умолчанию
        maxRedirects = setDefaultMaxRedirectsIfNull(maxRedirects);
        expirationDate = setDefaultExpirationDateIfNull(expirationDate);

        // Создание новой ссылки
        Link link = generateNewLink(userDTO, originalUrl, maxRedirects, expirationDate);

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

    public void deleteExpiredLinks() throws LinkNotFoundException {

        // Получение списка просроченных линков
        List<Link> expiredLinks = linkRepository.findAllExpired();

        // Удаление просроченных линков
        expiredLinks.forEach(linkRepository::delete);
    }


    public String getOriginalUrl(ShortURL shortUrl) throws LinkNotFoundException {

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Проверка активности ссылки
        if (!link.isActive()) {
            try {
                notificationService.sendNotification(
                        link.getUserId().toString(),
                        "Лимит переходов по вашей ссылке исчерпан или срок действия истек."
                );
            } catch (Exception e) {
                System.err.println("Ошибка отправки уведомления: " + e.getMessage());
            }
            throw new IllegalStateException("Ссылка недоступна");
        }

        // Увеличение счетчика переходов
        link.incrementRedirectCount();

        linkRepository.update(link);


        // Возврат оригинального URL
        return link.getOriginalUrl().getUrl();
    }

    public List<LinkDTO> getAllByUser(UUID userUuid) {

        List<Link> links = linkRepository.findAllByUser(userUuid);

        return links.stream().map(LinkMapper::toDto).collect(Collectors.toList());
    }
}
