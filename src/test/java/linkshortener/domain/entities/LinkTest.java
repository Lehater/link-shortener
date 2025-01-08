package linkshortener.domain.entities;

import linkshortener.domain.exceptions.InvalidURLException;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.domain.valueobjects.URL;
import linkshortener.domain.valueobjects.UUID;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Test
    void isActive_shouldReturnTrue_whenLinkIsValid() throws InvalidURLException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        Link link = new Link(
                new URL("http://example.com"),
                new UUID(userUuid1),
                new MaxRedirectsLimit(5),
                LocalDateTime.now().plusDays(1)
        );
        assertTrue(link.isActive());
    }

    @Test
    void isActive_shouldReturnFalse_whenLinkIsExpired() throws InvalidURLException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        Link link = new Link(
                new URL("http://example.com"),
                new UUID(userUuid1),
                new MaxRedirectsLimit(5),
                LocalDateTime.now().minusDays(1)
        );
        assertFalse(link.isActive());
    }

    @Test
    void isActive_shouldReturnFalse_whenRedirectLimitIsExceeded() throws InvalidURLException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        Link link = new Link(
                new URL("http://example.com"),
                new UUID(userUuid1),
                new MaxRedirectsLimit(3),
                LocalDateTime.now().plusDays(1)
        );
        link.incrementRedirectCount();
        link.incrementRedirectCount();
        link.incrementRedirectCount();
        assertFalse(link.isActive());
    }

    @Test
    void incrementRedirectCount_shouldIncreaseRedirectCount() throws InvalidURLException {
        String userUuid1 = java.util.UUID.randomUUID().toString();
        Link link = new Link(
                new URL("http://example.com"),
                new UUID(userUuid1),
                new MaxRedirectsLimit(5),
                LocalDateTime.now().plusDays(1)
        );
        link.incrementRedirectCount();
        assertEquals(1, link.getRedirectCount());
    }
}
