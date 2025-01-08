package linkshortener.presentation.controllers;

import linkshortener.application.usecases.link.*;
import linkshortener.application.usecases.user.ManageUUIDUseCase;
import linkshortener.domain.entities.Link;
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

    public LinkDTO createLink(UserDTO userDTO, String originalUrl) throws Exception {

        CreateLinkResponse response = createShortLinkUseCase.execute(
                new CreateLinkRequest(userDTO.getUuid(), new URL(originalUrl))
        );

        return new LinkDTO(response.getUserUuid().toString(),response.getShortURL().toString());
    }

//    public LinkDTO editLink(UserDTO userDTO, String shortUrlString, String MaxRedirectsString) throws Exception {
//        ShortURL shortURL = new ShortURL(shortUrlString);
//        MaxRedirectsLimit maxRedirectsLimit = new MaxRedirectsLimit(MaxRedirectsString);
//        EditLinkResponse link = editShortLinkUseCase.execute(
//                userDTO.getUuid(), shortURL, maxRedirectsLimit
//        );
//
//        return new LinkDTO(link.getShortUrl(), link.getUserId());
//    }

//    public void deleteLink(Link link) throws Exception {
//        deleteShortLinkUseCase.execute(link);
//    }
//
//    public String redirectLink(String shortUrl) throws Exception {
//        ShortURL shortURL = new ShortURL(shortUrl);
//
//        return redirectLinkUseCase.execute(shortURL);
//    }
//
    public List<LinkDTO> listLinks(UserDTO userDTO) throws Exception {
        List<Link> links = listUserLinksUseCase.execute(userDTO.getUuid());
        return links.stream()
                .map(link -> new LinkDTO(link.getUserId().toString(), link.getShortUrl().toString()))
                .collect(Collectors.toList());
    }

}
