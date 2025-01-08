package linkshortener.application.usecases.link;

import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.ShortURL;

public class DeleteShortLinkUseCase {

    private final LinkRepository linkRepository;

    public DeleteShortLinkUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void execute(String userUuid, ShortURL shortUrl) throws Exception {
        // 1. Найти ссылку по короткому URL
        Link link = linkRepository.findByShortUrl(shortUrl);
        if (link == null) {
            throw new IllegalArgumentException("Ссылка не найдена.");
        }

        // 2. Проверить, принадлежит ли ссылка пользователю
        if (!link.getUserId().toString().equals(userUuid)) {
            throw new SecurityException("Нет доступа к данной ссылке.");
        }

        // 3. Удалить ссылку
        linkRepository.delete(link.getId());
    }
}
