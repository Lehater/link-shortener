package linkshortener.bapplication.interfaces;

import linkshortener.adomain.valueobjects.MaxRedirectsLimit;

public interface ConfigService {

    MaxRedirectsLimit getDefaultMaxRedirects();

    int getDefaultLifetimeHours();
}
