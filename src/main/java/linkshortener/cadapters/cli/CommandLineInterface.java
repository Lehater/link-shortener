package linkshortener.cadapters.cli;

import linkshortener.adomain.valueobjects.MaxRedirectsLimit;
import linkshortener.adomain.valueobjects.ShortURL;
import linkshortener.bapplication.dtos.UserDTO;
import linkshortener.cadapters.controllers.AuthController;
import linkshortener.cadapters.controllers.LinkController;
import linkshortener.bapplication.dtos.LinkDTO;
import utils.ConsoleUtils;
import utils.OpenBrowser;

import java.util.List;
import java.util.Objects;

public class CommandLineInterface {

    private final AuthController authController;
    private final LinkController linkController;
    private final ConsoleUtils cli;
    private final OpenBrowser browser;

    public CommandLineInterface(AuthController authController, LinkController linkController) {

        this.authController = authController;
        this.linkController = linkController;
        this.cli = new ConsoleUtils();
        this.browser = new OpenBrowser();

    }

    public void start() {
        cli.print("Добро пожаловать в Сервис Сокращения Ссылок!");
        String userUuid = cli.getNextLine("Введите ваш UUID (оставьте пустым для создания нового): ");
        UserDTO userDTO = authController.authenticate(userUuid);
        if (Objects.equals(userUuid, "")) {
            cli.print("Ваш UUID: " + userDTO.getUuid());
        }
        while (true) {
            String command = cli.getNextLine(
                    "Введите команду (create, update, delete, redirect, list, exit): "
            ).trim().toLowerCase();
            switch (command) {
                case "create":
                    handleCreate(userDTO);
                    break;
                case "update":
                    handleUpdate(userDTO);
                    break;
                case "delete":
                    handleDelete(userDTO);
                    break;
                case "list":
                    handleList(userDTO);
                    break;
                case "redirect":
                    handleRedirect(userDTO);
                    break;
                case "exit":
                    cli.print("Выход из приложения.");
                    return;
                default:
                    cli.print("Неизвестная команда.");
            }
        }
    }

    private void handleCreate(UserDTO userDTO) {
        String defUrl = "https://example.com";
        String originalUrl = cli.getNextLine("Введите оригинальный URL [" + defUrl + "]: ", defUrl);
        try {
            LinkDTO linkDTO = linkController.createLink(userDTO, originalUrl);
            cli.print(
                    "Короткая ссылка для создана: " + originalUrl + " -> " + linkDTO.getShortUrl().toString()
            );
        } catch (Exception e) {
            cli.print("Ошибка при создании ссылки: " + e.getMessage());
        }
    }

    private void handleUpdate(UserDTO userDTO) {
        // Реализация обработки команды редактирования ссылки
        String shortUrl = cli.getNextLine("Введите короткую ссылку для редактирования: ");
        String shortUrlLimitString = cli.getNextLine("Введите лимит переходов для этой ссылки: ");
        try {
            MaxRedirectsLimit maxRedirectsLimit = new MaxRedirectsLimit(shortUrlLimitString);
            LinkDTO linkDTO = linkController.updateLink(userDTO, shortUrl, maxRedirectsLimit);
            cli.print("Короткая ссылка обновлена: " + linkDTO.toString());
        } catch (Exception e) {
            cli.print("Ошибка при обновлении ссылки: " + e.getMessage());
        }

    }

    private void handleDelete(UserDTO userDTO) {
        // Реализация обработки команды удаления ссылки
        String shortUrlString = cli.getNextLine("Введите короткую ссылку для удаления: ");
        try {
            ShortURL shortUrl = new ShortURL(shortUrlString);
            linkController.deleteLink(shortUrl);
            cli.print("Короткая ссылка удалена: " + shortUrl);
        } catch (Exception e) {
            cli.print("Ошибка при удалении ссылки: " + e.getMessage());
        }

    }

    private void handleList(UserDTO userDTO) {
        // Реализация обработки команды отображения списка ссылок пользователя
        try {
            StringBuilder res = new StringBuilder();
            List<LinkDTO> list = linkController.listLinks(userDTO);
            int count = 0;
            for (LinkDTO element : list) {
                count += 1;
                res.append(count + ") " + element.toString()).append("\n");
            }
            cli.print(res.toString());
        } catch (Exception e) {
            cli.print("Ошибка при чтении списка линков: " + e.getMessage());
        }

    }

    private void handleRedirect(UserDTO userDTO) {
        // Реализация обработки команды перенаправления по короткой ссылке
        String shortUrlString = cli.getNextLine("Введите короткую ссылку для перенаправления: ");
        try {
            ShortURL shortUrl = new ShortURL(shortUrlString);
//            LinkDTO linkDTO = new LinkDTO(shortUrl, userDTO.getUuid());
            String originalURL = linkController.getOriginalURL(shortUrl);
            browser.open(originalURL);
            cli.print("URL отправлен в браузер: " + originalURL);
        } catch (Exception e) {
            cli.print("Ошибка открытия в браузере: " + e.getMessage());
        }

    }
}
