package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;

public class EditShortLinkUseCase {

    private final LinkRepository linkRepository;

    public EditShortLinkUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public EditLinkResponse execute(String userUuid, ShortURL shortUrl, MaxRedirectsLimit maxRedirects) throws Exception {
        // 1. Найти ссылку по короткому URL
        Link link = linkRepository.findByShortUrl(shortUrl);
        if (link == null) {
            throw new IllegalArgumentException("Ссылка не найдена.");
        }

        // 2. Проверить, принадлежит ли ссылка пользователю
        if (!link.getUserId().toString().equals(userUuid)) {
            throw new SecurityException("Нет доступа к данной ссылке.");
        }

        // 3. Обновить лимит переходов
        link.setMaxRedirects(maxRedirects);

        // 4. Сохранить изменения
        linkRepository.update(link);

        // 5. Сформировать ответ
        return new EditLinkResponse(link.getUserId(), link.getShortUrl());
    }
}