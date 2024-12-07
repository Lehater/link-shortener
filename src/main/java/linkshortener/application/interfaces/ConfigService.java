package linkshortener.application.interfaces;

import linkshortener.domain.valueobjects.MaxRedirectsLimit;

public interface ConfigService {

    MaxRedirectsLimit getDefaultMaxRedirects();

    int getDefaultLifetimeHours();
}
