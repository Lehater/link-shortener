package linkshortener.application.usecases.user;

import linkshortener.domain.entities.User;
import linkshortener.domain.exceptions.UserNotFoundException;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.application.interfaces.UserRepository;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.presentation.dtos.LinkDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ManageUUIDUseCase {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;

    public ManageUUIDUseCase(UserRepository userRepository, LinkRepository linkRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
    }

    public CustomUUID generateUUID() {
        User user = new User();
        userRepository.save(user);
        return user.getUuid();
    }

    public boolean isValidUUID(String userUuidString) throws UserNotFoundException {
        CustomUUID userUUID = new CustomUUID(userUuidString);
        return userRepository.findByUuid(userUUID) != null;
    }

//    public List<LinkDTO> getLinksForUser(CustomUUID userUUID) {
//        return linkRepository.findAllByUser(userUUID)
//                .stream()
//                .map(link -> new LinkDTO(link.getShortUrl(), link.getUserId()))
//                .collect(Collectors.toList());
//    }

}
