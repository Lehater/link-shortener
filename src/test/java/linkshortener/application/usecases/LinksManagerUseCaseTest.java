package linkshortener.application.usecases;

import linkshortener.application.dtos.LinkDTO;
import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.InvalidURLException;
import linkshortener.domain.exceptions.LinkNotFoundException;
import linkshortener.domain.exceptions.UnauthorizedAccessException;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.ShortURL;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.UUID;
import linkshortener.application.dtos.UserDTO;
import linkshortener.application.interfaces.ConfigService;
import linkshortener.application.interfaces.LinkRepository;
import linkshortener.application.interfaces.NotificationService;
import linkshortener.application.interfaces.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LinksManagerUseCaseTest {

    private LinksManagerUseCase linksManagerUseCase;
    private LinkRepository linkRepositoryMock;
    private UrlShortenerService urlShortenerServiceMock;
    private NotificationService notificationServiceMock;
    private ConfigService configServiceMock;

    @BeforeEach
    void setUp() {
        linkRepositoryMock = mock(LinkRepository.class);
        urlShortenerServiceMock = mock(UrlShortenerService.class);
        notificationServiceMock = mock(NotificationService.class);
        configServiceMock = mock(ConfigService.class);

        linksManagerUseCase = new LinksManagerUseCase(
                linkRepositoryMock,
                urlShortenerServiceMock,
                configServiceMock,
                notificationServiceMock
        );
    }

    @Test
    void create_shouldGenerateUniqueShortUrlsForDifferentUsers() throws Exception {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        String userUuid2 = java.util.UUID.randomUUID().toString();
        UserDTO user1 = new UserDTO(new UUID(userUuid1));
        UserDTO user2 = new UserDTO(new UUID(userUuid2));
        URL originalUrl = new URL("http://example.com");

        when(urlShortenerServiceMock.generateShortUrl(anyString()))
                .thenReturn("shortUrl1", "shortUrl2");

        LinkDTO link1 = linksManagerUseCase.create(user1, originalUrl.getUrl(), null, null);
        LinkDTO link2 = linksManagerUseCase.create(user2, originalUrl.getUrl(), null, null);

        assertNotEquals(link1.getShortUrl(), link2.getShortUrl());
    }

    @Test
    void getOriginalUrl_shouldBlockAccess_whenLimitExceeded() {
        ShortURL shortUrl = new ShortURL("short123");
        Link link = mock(Link.class);
        String userUuid1 = java.util.UUID.randomUUID().toString();
        UUID userUuid = new UUID(userUuid1);

        when(link.getUserId()).thenReturn(userUuid);
        when(link.isActive()).thenReturn(false);
        when(linkRepositoryMock.findByShortUrl(shortUrl)).thenReturn(link);

        assertThrows(IllegalStateException.class, () -> linksManagerUseCase.getOriginalUrl(shortUrl));
    }

    @Test
    void deleteExpiredLinks_shouldRemoveLinksAfterExpiration() throws InvalidURLException, LinkNotFoundException, UnauthorizedAccessException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        UUID userUuid = new UUID(userUuid1);
        Link expiredLink = new Link(
                new URL("http://expired.com"),
                userUuid,
                new MaxRedirectsLimit(5),
                LocalDateTime.now().minusDays(2)
        );
        when(linkRepositoryMock.findAllExpired()).thenReturn(List.of(expiredLink));

        linksManagerUseCase.deleteExpiredLinks();

        verify(linkRepositoryMock, times(1)).delete(expiredLink);
    }

    @Test
    void notifyUser_whenLinkBecomesUnavailable_dueToLimitOrExpiration() {
        ShortURL shortUrl = new ShortURL("short123");
        Link link = mock(Link.class);
        String userUuid1 = java.util.UUID.randomUUID().toString();
        UUID userUuid = new UUID(userUuid1);

        when(link.isActive()).thenReturn(false);
        when(link.getUserId()).thenReturn(userUuid);
        when(linkRepositoryMock.findByShortUrl(shortUrl)).thenReturn(link);

        assertThrows(IllegalStateException.class, () -> linksManagerUseCase.getOriginalUrl(shortUrl));

        verify(notificationServiceMock, times(1))
                .sendNotification(eq(userUuid.toString()), anyString());
    }

    @Test
    void deleteExpiredLinks_shouldRemoveAllExpiredLinks() throws InvalidURLException, LinkNotFoundException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        Link expiredLink = new Link(
                new URL("http://expired.com"),
                new UUID(userUuid1),
                new MaxRedirectsLimit(5),
                LocalDateTime.now().minusHours(1)
        );
        String userUuid2 = java.util.UUID.randomUUID().toString();
        Link activeLink = new Link(
                new URL("http://active.com"),
                new UUID(userUuid2),
                new MaxRedirectsLimit(5),
                LocalDateTime.now().plusHours(1)
        );

        // Настраиваем мок так, чтобы возвращались только истёкшие ссылки
        when(linkRepositoryMock.findAllExpired()).thenReturn(List.of(expiredLink));

        linksManagerUseCase.deleteExpiredLinks();

        // Проверяем, что удаляется только истёкшая ссылка
        verify(linkRepositoryMock, times(1)).delete(expiredLink);
        verify(linkRepositoryMock, never()).delete(activeLink);
    }
}
