package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.application.interfaces.LinkRepository;

import java.time.LocalDateTime;

public class EditShortLinkUseCase {

    private final LinkRepository linkRepository;

    public EditShortLinkUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link execute(
            EditLinkRequest editLinkRequest
    ) throws LinkNotFoundException, UnauthorizedAccessException {

        // Получение ссылки
        Link link = linkRepository.findByShortUrl(editLinkRequest.getShortUrl());

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Обновление параметров ссылки
        if (editLinkRequest.getMaxRedirectsLimit() != null) {
            link.setMaxRedirects(editLinkRequest.getMaxRedirectsLimit());
        }

        if (editLinkRequest.getExpirationDate() != null) {
            LocalDateTime.now().plusHours(24); // TODO
        }


        // Сохранение изменений
        return linkRepository.update(link);
    }
}
