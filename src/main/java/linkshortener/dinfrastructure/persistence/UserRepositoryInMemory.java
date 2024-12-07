package linkshortener.dinfrastructure.persistence;

import linkshortener.bapplication.interfaces.UserRepository;
import linkshortener.adomain.entities.User;
import linkshortener.adomain.valueobjects.UUID;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private final Map<String, User> userStorage = new HashMap<>();

    @Override
    public void save(User user) {
        userStorage.put(user.getUuid().toString(), user);
    }

    @Override
    public User findByUuid(UUID UUID) {
        return userStorage.get(UUID.toString());
    }

    @Override
    public void update(User user) {
        userStorage.put(user.getUuid().toString(), user);
    }
}
