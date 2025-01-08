package linkshortener.application.usecases.link;

import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.CustomUUID;

import java.util.List;

public class ListUserLinksUseCase {
    private final LinkRepository linkRepository;

    public ListUserLinksUseCase(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> execute(CustomUUID userUuid) throws Exception {
        return linkRepository.findAllByUserUUID(userUuid);
    }
}
