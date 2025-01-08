package linkshortener.infrastructure.persistence;

import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.*;
import java.util.stream.Collectors;

public class LinkRepositoryInMemory implements LinkRepository {

    private final Map<CustomUUID, Link> linkStorage = new HashMap<>();

    @Override
    public void save(Link link) {
        linkStorage.put(link.getId(), link);
    }

    @Override
    public Link findById(CustomUUID linkUUID) {
        return linkStorage.get(linkUUID);
    }

    @Override
    public Link findByShortUrl(ShortURL shortUrl) {
        return linkStorage.values()
                .stream()
                .filter(link -> link.getShortUrl().toString().equals(shortUrl.toString()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Link> findAllByUserUUID(CustomUUID userUUID) {
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
    public void delete(CustomUUID linkUUID) {
        linkStorage.remove(linkUUID);
    }
}
