package linkshortener.application.interfaces;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.List;

public interface LinkRepository {

    void save(Link link); // Сохранить ссылку

    Link findByShortUrl(ShortURL shortUrl); // Найти ссылку по сокращенному URL

    Link update(Link link); // Обновить ссылку

//    void delete(CustomUUID linkUUID); // Удалить ссылку

    Link findById(CustomUUID linkUuid); // Найти ссылку по уникальному идентификатору

    List<Link> findAllByUserUUID(CustomUUID userUUID); // Найти все ссылки пользователя
}
