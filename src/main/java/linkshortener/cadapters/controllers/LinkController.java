package linkshortener.cadapters.controllers;

import linkshortener.bapplication.dtos.UserDTO;
import linkshortener.bapplication.usecases.*;
import linkshortener.adomain.valueobjects.ShortURL;
import linkshortener.adomain.valueobjects.MaxRedirectsLimit;
import linkshortener.bapplication.dtos.LinkDTO;

import java.util.List;


public class LinkController {

    private final LinksManagerUseCase linksManagerUseCase;

    public LinkController(LinksManagerUseCase linksManagerUseCase) {
        this.linksManagerUseCase = linksManagerUseCase;
    }

    public LinkDTO createLink(UserDTO userDTO, String originalUrl) throws Exception {
        return linksManagerUseCase.create(userDTO, originalUrl, null, null);
    }

    public LinkDTO updateLink(
            UserDTO userDTO, String shortUrlString, MaxRedirectsLimit newMaxRedirects
    ) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrlString);
        return linksManagerUseCase.update(userDTO, shortURL, newMaxRedirects);
    }

    public void deleteLink(ShortURL shortUrl) throws Exception {
        linksManagerUseCase.delete(shortUrl);
    }

    public String getOriginalURL(ShortURL shortUrl) throws Exception {
        return linksManagerUseCase.getOriginalUrl(shortUrl);
    }

    public List<LinkDTO> listLinks(UserDTO userDTO) throws Exception {
        return linksManagerUseCase.getAllByUser(userDTO.getUuid());
    }

}
