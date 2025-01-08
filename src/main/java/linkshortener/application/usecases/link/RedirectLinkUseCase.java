package linkshortener.application.usecases.link;

import linkshortener.domain.entities.Link;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.valueobjects.ShortURL;


public class RedirectLinkUseCase {

    private final LinkRepository linkRepository;

    public RedirectLinkUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String execute(ShortURL shortUrl) throws Exception {
        // 1. Найти ссылку по короткому URL
        Link link = linkRepository.findByShortUrl(shortUrl);
        if (link == null) {
            throw new IllegalArgumentException("Ссылка не найдена.");
        }

        // 2. Проверить активность ссылки
        if (!link.isActive()) {
            throw new IllegalStateException("Ссылка недействительна или срок её действия истёк.");
        }

        // 3. Инкрементировать счётчик переходов
        link.incrementRedirectCount();
        linkRepository.update(link);

        // 4. Вернуть оригинальный URL
        return link.getOriginalUrl().toString();
    }
}
