package linkshortener.adapters.mapper;

import linkshortener.domain.entities.Link;
import linkshortener.application.dtos.LinkDTO;

public class LinkMapper {
    public static LinkDTO toDto(Link link) {
        return new LinkDTO(link.getShortUrl(), link.getUserId(), link.getMaxRedirects());
    }
}