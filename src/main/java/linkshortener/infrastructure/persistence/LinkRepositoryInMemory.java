package linkshortener.infrastructure.persistence;

import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.domain.valueobjects.ShortURL;

import java.util.*;
import java.util.stream.Collectors;

public class LinkRepositoryInMemory implements LinkRepository {

    private final Map<ShortURL, Link> linkStorage = new HashMap<>();

    @Override
    public void save(Link link) {
        linkStorage.put(link.getShortUrl(), link);
    }

    @Override
    public Link update(Link link) throws LinkNotFoundException {
        if (linkStorage.containsKey(link.getShortUrl())) {
            linkStorage.put(link.getShortUrl(), link);
            return link;
        } else {
            throw new LinkNotFoundException("Ссылка с ID " + link.getId() + " не найдена для обновления.");
        }
    }

    @Override
    public void delete(Link link) {

        linkStorage.remove(link.getShortUrl());
    }


    @Override
    public Link findByShortUrl(ShortURL shortUrl) {
        if (linkStorage.containsKey(shortUrl)) {
            return linkStorage.get(shortUrl);
        } else {
            throw new IllegalArgumentException("Ссылка с ID " + shortUrl + " не найдена.");
        }
    }

    @Override
    public List<Link> findAllExpired() {
        return linkStorage.values()
                .stream()
                .filter(Link::isExpired)
                .collect(Collectors.toList());
    }

    @Override
    public List<Link> findAllByUser(UUID userUuid) {
        return linkStorage.values()
                .stream()
                .filter(link -> link.getUserId().equals(userUuid))
                .collect(Collectors.toList());
    }
}
