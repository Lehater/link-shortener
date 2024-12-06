package linkshortener.application.usecases;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.application.interfaces.LinkRepository;

public class DeleteShortLinkUseCase {

    private final LinkRepository linkRepository;

    public DeleteShortLinkUseCase(
            LinkRepository linkRepository
    ) {
        this.linkRepository = linkRepository;
    }

    public void execute(Link link) throws LinkNotFoundException, UnauthorizedAccessException {

        if (link == null) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }

        // Удаление ссылки
        linkRepository.delete(link);

    }
}
