package linkshortener.application.interfaces;

import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.List;

public interface LinkRepository {

    void save(Link link); // Сохранить ссылку

    Link update(Link link) throws LinkNotFoundException; // Обновить ссылку

    void delete(Link link); // Удалить ссылку

    Link findByShortUrl(ShortURL shortUrl); // Найти ссылку по сокращенному URL

    List<Link> findAllExpired(); // Найти все просроченные ссылки

    List<Link> findAllByUser(UUID userUuid); // Найти все ссылки пользователя
}
