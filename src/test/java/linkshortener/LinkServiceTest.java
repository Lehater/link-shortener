package linkshortener;

import linkshortener.application.usecases.link.CreateShortLinkUseCase;
import linkshortener.application.usecases.link.RedirectLinkUseCase;
import linkshortener.domain.entities.Link;
import linkshortener.domain.exceptions.InvalidURLException;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.infrastructure.persistence.LinkRepositoryInMemory;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LinkServiceTest {

    @Test
    void UniqueShortUrlsForDifferentUsersTest() throws InvalidURLException {
        LinkRepositoryInMemory repository = new LinkRepositoryInMemory();
        CreateShortLinkUseCase createShortLinkUseCase = new CreateShortLinkUseCase(
                repository, null, null
        );

        CustomUUID user1 = new CustomUUID(UUID.randomUUID().toString());
        CustomUUID user2 = new CustomUUID(UUID.randomUUID().toString());

        Link link1 = new Link(new URL("https://example.com"), user1, null, LocalDateTime.now().plusDays(1));
        Link link2 = new Link(new URL("https://example.com"), user2, null, LocalDateTime.now().plusDays(1));

        repository.save(link1);
        repository.save(link2);

        assertNotEquals(link1.getShortUrl(), link2.getShortUrl());
    }

    @Test
    void testBlockingLinkAfterExceedingLimit() throws InvalidURLException {
        LinkRepositoryInMemory repository = new LinkRepositoryInMemory();
        RedirectLinkUseCase redirectLinkUseCase = new RedirectLinkUseCase(repository);

        CustomUUID user = new CustomUUID(UUID.randomUUID().toString());

        Link link = new Link(
                new URL("https://example.com"),
                user,
                new MaxRedirectsLimit(3),
                LocalDateTime.now().plusDays(1)
        );
        repository.save(link);

        for (int i = 0; i < 3; i++) {
            assertDoesNotThrow(() -> redirectLinkUseCase.execute(link.getShortUrl()));
        }

        Exception exception = assertThrows(Exception.class, () -> redirectLinkUseCase.execute(link.getShortUrl()));
        assertEquals("Ссылка недействительна или срок её действия истёк.", exception.getMessage());
    }

    @Test
    void testLinkExpiry() throws InvalidURLException {
        LinkRepositoryInMemory repository = new LinkRepositoryInMemory();

        CustomUUID user = new CustomUUID(UUID.randomUUID().toString());

        Link link = new Link(
                new URL("https://example.com"),
                user,
                new MaxRedirectsLimit(3),
                LocalDateTime.now().minusDays(1)
        );
        repository.save(link);

        assertFalse(link.isActive());
    }

    @Test
    void testUserNotificationOnInvalidLink() throws InvalidURLException {
        LinkRepositoryInMemory repository = new LinkRepositoryInMemory();
        RedirectLinkUseCase redirectLinkUseCase = new RedirectLinkUseCase(repository);

        CustomUUID user = new CustomUUID(UUID.randomUUID().toString());

        Link link = new Link(
                new URL("https://example.com"),
                user,
                new MaxRedirectsLimit(3),
                LocalDateTime.now().minusDays(1));
        repository.save(link);

        Exception exception = assertThrows(Exception.class, () -> redirectLinkUseCase.execute(link.getShortUrl()));
        assertEquals("Ссылка недействительна или срок её действия истёк.", exception.getMessage());
    }
}
