package linkshortener.application.usecases;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.application.interfaces.LinkRepository;

public class EditShortLinkUseCase {

    private final LinkRepository linkRepository;

    public EditShortLinkUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link execute(
            User user,
            ShortURL shortUrl,
            MaxRedirectsLimit newMaxRedirects
    ) throws LinkNotFoundException, UnauthorizedAccessException {

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(user, shortUrl);

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Обновление параметров ссылки
        if (newMaxRedirects != null) {
            link.setMaxRedirects(newMaxRedirects);
        }

        // Сохранение изменений
        return linkRepository.update(link);
    }
}
