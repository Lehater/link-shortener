package utils;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenBrowser {
    public static void open(String url) {
        try {
            // Проверяем, поддерживает ли текущая платформа Desktop API
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // Проверяем, поддерживает ли Desktop функцию browse
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                } else {
                    System.out.println("Функция 'browse' не поддерживается.");
                }
            } else {
                System.out.println("Desktop API не поддерживается.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
