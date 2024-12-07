package linkshortener.bapplication.interfaces;

import linkshortener.adomain.entities.Link;
import linkshortener.adomain.valueobjects.UUID;
import linkshortener.adomain.valueobjects.ShortURL;

import java.util.List;

public interface LinkRepository {

    void save(Link link); // Сохранить ссылку

    Link update(Link link); // Обновить ссылку

    void delete(Link link); // Удалить ссылку

    Link findByShortUrl(ShortURL shortUrl); // Найти ссылку по сокращенному URL

    List<Link> findAllByUser(UUID userUuid); // Найти все ссылки пользователя
}
