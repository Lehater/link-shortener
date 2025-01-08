package linkshortener.presentation.controllers;

import linkshortener.application.usecases.link.*;
import linkshortener.application.usecases.user.ManageUUIDUseCase;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;
import linkshortener.presentation.dtos.LinkDTO;
import linkshortener.presentation.dtos.UserDTO;

import java.util.List;

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

    public LinkDTO createLink(UserDTO userDTO, String originalUrl) throws Exception {

        CreateLinkResponse link = createShortLinkUseCase.execute(
                new CreateLinkRequest(userDTO.getUuid(), new URL(originalUrl))
        );

        return new LinkDTO(link.getUserUuid(),link.getShortURL());
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
//    public List<LinkDTO> listLinks(User user) throws Exception {
//        return manageUUIDUseCase.getLinksForUser(user.getUuid());
//    }

}
