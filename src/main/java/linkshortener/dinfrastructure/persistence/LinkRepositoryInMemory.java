package linkshortener.dinfrastructure.persistence;

import linkshortener.bapplication.interfaces.LinkRepository;
import linkshortener.adomain.entities.Link;
import linkshortener.adomain.valueobjects.UUID;
import linkshortener.adomain.valueobjects.ShortURL;

import java.util.*;
import java.util.stream.Collectors;

public class LinkRepositoryInMemory implements LinkRepository {

    private final Map<ShortURL, Link> linkStorage = new HashMap<>();

    @Override
    public void save(Link link) {
        linkStorage.put(link.getShortUrl(), link);
    }

    @Override
    public Link update(Link link) {
        if (linkStorage.containsKey(link.getShortUrl())) {
            linkStorage.put(link.getShortUrl(), link);
            return link;
        } else {
            throw new IllegalArgumentException("Ссылка с ID " + link.getId() + " не найдена для обновления.");
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
    public List<Link> findAllByUser(UUID userUuid) {
        return linkStorage.values()
                .stream()
                .filter(link -> link.getUserId().equals(userUuid))
                .collect(Collectors.toList());
    }
}
