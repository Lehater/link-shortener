package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.application.interfaces.NotificationService;
import linkshortener.domain.valueobjects.ShortURL;

public class RedirectLinkUseCase {

    private final LinkRepository linkRepository;
    private final NotificationService notificationService;

    public RedirectLinkUseCase(
            LinkRepository linkRepository,
            NotificationService notificationService
    ) {
        this.linkRepository = linkRepository;
        this.notificationService = notificationService;
    }

    public String execute(ShortURL shortUrl) throws LinkNotFoundException {

        // Получение ссылки
//        Link link = linkRepository.findByShortUrl(shortUrl);
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

        // Возврат оригинального URL для перенаправления
        return link.getOriginalUrl().getUrl();
    }
}
