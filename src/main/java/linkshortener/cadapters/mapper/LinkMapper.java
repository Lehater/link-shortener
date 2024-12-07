package linkshortener.cadapters.mapper;

import linkshortener.adomain.entities.Link;
import linkshortener.bapplication.dtos.LinkDTO;

public class LinkMapper {
    public static LinkDTO toDto(Link link) {
        return new LinkDTO(link.getShortUrl(), link.getUserId(), link.getMaxRedirects());
    }
}