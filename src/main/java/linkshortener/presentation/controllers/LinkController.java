package linkshortener.presentation.controllers;

import linkshortener.application.usecases.link.*;
import linkshortener.application.usecases.user.ManageUUIDUseCase;
import linkshortener.domain.entities.Link;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;
import linkshortener.presentation.dtos.LinkDTO;
import linkshortener.presentation.dtos.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class LinkController {

    private final CreateShortLinkUseCase createShortLinkUseCase;
    private final EditShortLinkUseCase editShortLinkUseCase;
    private final DeleteShortLinkUseCase deleteShortLinkUseCase;
    private final RedirectLinkUseCase redirectLinkUseCase;
    private final ManageUUIDUseCase manageUUIDUseCase;
    private final ListUserLinksUseCase listUserLinksUseCase;

    public LinkController(
            CreateShortLinkUseCase createShortLinkUseCase,
            EditShortLinkUseCase editShortLinkUseCase,
            DeleteShortLinkUseCase deleteShortLinkUseCase,
            RedirectLinkUseCase redirectLinkUseCase,
            ManageUUIDUseCase manageUUIDUseCase,
            ListUserLinksUseCase listUserLinksUseCase
    ) {
        this.createShortLinkUseCase = createShortLinkUseCase;
        this.editShortLinkUseCase = editShortLinkUseCase;
        this.deleteShortLinkUseCase = deleteShortLinkUseCase;
        this.redirectLinkUseCase = redirectLinkUseCase;
        this.manageUUIDUseCase = manageUUIDUseCase;
        this.listUserLinksUseCase = listUserLinksUseCase;
    }

    public LinkDTO createLink(
            UserDTO userDTO, String originalUrl, int userLifetimeHours, int userMaxRedirects
    ) throws Exception {

        // Собираем CreateLinkRequest
        CreateLinkRequest request = new CreateLinkRequest(
                userDTO.getUuid(),
                new URL(originalUrl),
                userMaxRedirects,   // <-- новое
                userLifetimeHours   // <-- то, что уже было
        );

        // Вызываем Use Case
        CreateLinkResponse response = createShortLinkUseCase.execute(request);

        // Возвращаем DTO
        return new LinkDTO(
                response.getUserUuid().toString(),
                response.getShortURL().toString()
        );
    }

    public LinkDTO editLink(UserDTO userDTO, String shortUrlString, String maxRedirectsString) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrlString);
        MaxRedirectsLimit maxRedirectsLimit = new MaxRedirectsLimit(maxRedirectsString);
        EditLinkResponse response = editShortLinkUseCase.execute(
                userDTO.getUuid().toString(), shortURL, maxRedirectsLimit
        );

        return new LinkDTO(response.getUserUuid().toString(), response.getShortURL().toString());
    }

    public void deleteLink(UserDTO userDTO, String shortUrl) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrl);
        deleteShortLinkUseCase.execute(userDTO.getUuid().toString(), shortURL);
    }


    public String redirectLink(String shortUrl) throws Exception {
        ShortURL shortURL = new ShortURL(shortUrl);

        return redirectLinkUseCase.execute(shortURL);
    }

    public List<LinkDTO> listLinks(UserDTO userDTO) throws Exception {
        List<Link> links = listUserLinksUseCase.execute(userDTO.getUuid());
        return links.stream()
                .map(link -> new LinkDTO(link.getUserId().toString(), link.getShortUrl().toString()))
                .collect(Collectors.toList());
    }

}
