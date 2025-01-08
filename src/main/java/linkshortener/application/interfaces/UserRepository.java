package linkshortener.application.interfaces;

import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.CustomUUID;

public interface UserRepository {

    void save(User user);

    User findByUuid(CustomUUID userUuid) throws UserNotFoundException;

    void update(User user);
}
