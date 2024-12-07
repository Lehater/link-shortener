package linkshortener.application.interfaces;

import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.UUID;

public interface UserRepository {

    void save(User user);

    User findByUuid(UUID UUID) throws UserNotFoundException;

    void update(User user);
}
