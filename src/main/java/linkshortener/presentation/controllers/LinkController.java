package linkshortener.presentation.controllers;

import linkshortener.application.usecases.*;
import linkshortener.domain.entities.Link;
import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.presentation.dtos.LinkDTO;

import java.util.List;
import java.util.stream.Collectors;

public class LinkController {

    private final CreateShortLinkUseCase createShortLinkUseCase;
    private final EditShortLinkUseCase editShortLinkUseCase;
    private final DeleteShortLinkUseCase deleteShortLinkUseCase;
    private final RedirectLinkUseCase redirectLinkUseCase;
    private final ManageUUIDUseCase manageUUIDUseCase;

    public LinkController(
            CreateShortLinkUseCase createShortLinkUseCase,
            EditShortLinkUseCase editShortLinkUseCase,
            DeleteShortLinkUseCase deleteShortLinkUseCase,
            RedirectLinkUseCase redirectLinkUseCase,
            ManageUUIDUseCase manageUUIDUseCase
    ) {
        this.createShortLinkUseCase = createShortLinkUseCase;
        this.editShortLinkUseCase = editShortLinkUseCase;
        this.deleteShortLinkUseCase = deleteShortLinkUseCase;
        this.redirectLinkUseCase = redirectLinkUseCase;
        this.manageUUIDUseCase = manageUUIDUseCase;
    }

    public LinkDTO createLink(User user, String originalUrl) throws Exception {
        Link link = createShortLinkUseCase.execute(user, originalUrl, null, null);

        return new LinkDTO(link.getShortUrl(), link.getUserId());
    }

    public LinkDTO editLink(User user, String shortUrlString, MaxRedirectsLimit newMaxRedirects) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrlString);
        Link link = editShortLinkUseCase.execute(user, shortURL, newMaxRedirects);

        return new LinkDTO(link.getShortUrl(), link.getUserId());
    }

    public void deleteLink(Link link) throws Exception {
        deleteShortLinkUseCase.execute(link);
    }

    public String redirectLink(String shortUrl) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrl);

        return redirectLinkUseCase.execute(shortURL);
    }

    public List<LinkDTO> listLinks(User user) throws Exception {
        List<Link> links = manageUUIDUseCase.getLinksForUser(user.getUuid());
        return links.stream()
                .map(link -> new LinkDTO(link.getShortUrl(), link.getUserId()))
                .collect(Collectors.toList());
    }

}
