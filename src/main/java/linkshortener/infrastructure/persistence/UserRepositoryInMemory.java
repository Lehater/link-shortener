package linkshortener.infrastructure.persistence;

import linkshortener.application.interfaces.UserRepository;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.CustomUUID;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private final Map<String, User> userStorage = new HashMap<>();

    @Override
    public void save(User user) {
        userStorage.put(user.getUuid().toString(), user);
    }

    @Override
    public User findByUuid(CustomUUID UUID) {
        return userStorage.get(UUID.toString());
    }

    @Override
    public void update(User user) {
        userStorage.put(user.getUuid().toString(), user);
    }
}
