package linkshortener.infrastructure.services;

import linkshortener.application.interfaces.ConfigService;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class ConfigServicePropsFile implements ConfigService {

    private final Properties properties;

    public ConfigServicePropsFile(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Файл конфигурации " + fileName + " не найден!");
            }
            properties.load(input);
        } catch (IOException ex) {
            // Обработка ошибки загрузки конфигурации
            ex.printStackTrace();
        }
    }

    @Override
    public MaxRedirectsLimit getDefaultMaxRedirects() {
        return new MaxRedirectsLimit(
                Integer.parseInt(properties.getProperty("default.max.redirects", "3"))
        );
    }

    @Override
    public int getDefaultLifetimeHours() {
        return Integer.parseInt(properties.getProperty("default.lifetime.hours", "24"));
    }
}
