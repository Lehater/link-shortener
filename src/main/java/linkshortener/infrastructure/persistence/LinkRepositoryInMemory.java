package linkshortener.infrastructure.persistence;

import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.*;
import java.util.stream.Collectors;

public class LinkRepositoryInMemory implements LinkRepository {

    private final Map<UUID, Link> linkStorage = new HashMap<>();

    @Override
    public void save(Link link) {
        linkStorage.put(link.getId(), link);
    }

    @Override
    public Link findById(UUID linkUUID) {
        return linkStorage.get(linkUUID);
    }

    @Override
    public Link findByShortUrl(ShortURL shortUrl) {
        return linkStorage.values()
                .stream()
                .filter(link -> link.getShortUrl().equals(shortUrl))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Link> findAllByUserId(UUID userUUID) {
        return linkStorage.values()
                .stream()
                .filter(link -> link.getUserId().equals(userUUID))
                .collect(Collectors.toList());
    }

    @Override
    public Link update(Link link) {
        if (linkStorage.containsKey(link.getId())) {
            linkStorage.put(link.getId(), link);
            return link;
        } else {
            throw new IllegalArgumentException("Ссылка с ID " + link.getId() + " не найдена для обновления.");
        }
    }

    @Override
    public void delete(UUID linkUUID) {
        linkStorage.remove(linkUUID);
    }
}
