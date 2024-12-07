package linkshortener.bapplication.interfaces;

import linkshortener.adomain.entities.User;
import linkshortener.adomain.exceptions.UserNotFoundException;
import linkshortener.adomain.valueobjects.UUID;

public interface UserRepository {

    void save(User user);

    User findByUuid(UUID UUID) throws UserNotFoundException;

    void update(User user);
}
