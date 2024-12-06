package linkshortener.application.interfaces;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.List;

public interface LinkRepository {

    void save(Link link); // Сохранить ссылку

    Link findByShortUrl(User user, ShortURL shortUrl); // Найти ссылку по сокращенному URL

    Link update(Link link); // Обновить ссылку

    void delete(Link link); // Удалить ссылку

    Link findById(UUID linkUuid); // Найти ссылку по уникальному идентификатору

    List<Link> findAllByUser(User user); // Найти все ссылки пользователя
}
