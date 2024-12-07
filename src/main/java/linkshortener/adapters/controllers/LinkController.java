package linkshortener.adapters.controllers;

import linkshortener.application.dtos.UserDTO;
import linkshortener.application.usecases.*;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.application.dtos.LinkDTO;

import java.util.List;


public class LinkController {

    private final LinksManagerUseCase linksManagerUseCase;

    public LinkController(LinksManagerUseCase linksManagerUseCase) {
        this.linksManagerUseCase = linksManagerUseCase;
    }

    public LinkDTO createLink(UserDTO userDTO, String originalUrl) throws Exception {
        try {
            return linksManagerUseCase.create(userDTO, originalUrl, null, null);
        } catch (Exception e) {
            ErrorHandler.handle(e);
            return null;
        }
    }

    public LinkDTO updateLink(
            UserDTO userDTO, String shortUrlString, MaxRedirectsLimit newMaxRedirects
    ) throws Exception {
        try {
            ShortURL shortURL = new ShortURL(shortUrlString);
            return linksManagerUseCase.update(userDTO, shortURL, newMaxRedirects);
        } catch (Exception e) {
            ErrorHandler.handle(e);
            return null;
        }

    }

    public void deleteLink(ShortURL shortUrl) throws Exception {
        try {
            linksManagerUseCase.delete(shortUrl);
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    public String getOriginalURL(ShortURL shortUrl) throws Exception {
        try {
            return linksManagerUseCase.getOriginalUrl(shortUrl);
        } catch (Exception e) {
            ErrorHandler.handle(e);
            return null;
        }
    }

    public List<LinkDTO> listLinks(UserDTO userDTO) throws Exception {
        try {
            return linksManagerUseCase.getAllByUser(userDTO.getUuid());
        } catch (Exception e) {
            ErrorHandler.handle(e);
            return List.of();
        }
    }

}
