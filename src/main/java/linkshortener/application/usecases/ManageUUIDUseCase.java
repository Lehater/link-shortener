package linkshortener.application.usecases;

import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.application.interfaces.UserRepository;

import java.util.List;

public class ManageUUIDUseCase {

    private final UserRepository userRepository;

    public ManageUUIDUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID generateUUID() {
        User user = new User();
        userRepository.save(user);
        return user.getUuid();
    }

    public boolean isValidUUID(String userUuidString) {
        UUID userUUID = new UUID(userUuidString);
        return userRepository.findByUuid(userUUID) != null;
    }

    // Получение списка ссылок для пользователя
    public List<Link> getLinksForUser(UUID userUUID) {
        return linkRepository.findAllByUser(userUUID)
                .values()
                .stream()
                .collect(Collectors.toList());
    }

}
