package linkshortener.presentation.cli;

import linkshortener.domain.entities.User;
import linkshortener.domain.valueobjects.MaxRedirectsLimit;
import linkshortener.presentation.controllers.AuthController;
import linkshortener.presentation.controllers.LinkController;
import linkshortener.presentation.dtos.LinkDTO;
import utils.ConsoleUtils;

public class CommandLineInterface {

    private final AuthController authController;
    private final LinkController linkController;
    private final ConsoleUtils cli;
    private final User user = null;

    public CommandLineInterface(AuthController authController,LinkController linkController) {

        this.authController = authController;
        this.linkController = linkController;
        this.cli = new ConsoleUtils();
    }

    public void start() {
        cli.print("Добро пожаловать в Сервис Сокращения Ссылок!");
        String userUuid = cli.getNextLine("Введите ваш UUID (оставьте пустым для создания нового): ");
        User user = authController.authenticate(userUuid);
        while (true) {
            String command = cli.getNextLine(
                    "Введите команду (create, edit, delete, redirect, list, exit): "
            ).trim().toLowerCase();
            switch (command) {
                case "create":
                    handleCreate(user);
                    break;
                case "edit":
                    handleEdit(user);
                    break;
                case "delete":
                    handleDelete(user);
                    break;
                case "redirect":
                    handleRedirect(user);
                    break;
                case "list":
                    handleList(user);
                    break;
                case "exit":
                    cli.print("Выход из приложения.");
                    return;
                default:
                    cli.print("Неизвестная команда.");
            }
        }
    }

    private void handleCreate(User user) {
        String originalUrl = cli.getNextLine("Введите оригинальный URL: ");
        try {
            LinkDTO linkDTO = linkController.createLink(user, originalUrl);
            cli.print("Короткая ссылка создана: " + linkDTO.getShortUrl());
            cli.print("Ваш UUID: " + linkDTO.getUserUuid());
        } catch (Exception e) {
            cli.print("Ошибка при создании ссылки: " + e.getMessage());
        }
    }

    private void handleEdit(User user) {
        // Реализация обработки команды редактирования ссылки
        String shortUrl = cli.getNextLine("Введите короткую ссылку для редактирования: ");
        String shortUrlLimitString = cli.getNextLine("Введите лимит переходов для этой ссылки: ");
        try {
            MaxRedirectsLimit maxRedirectsLimit = new MaxRedirectsLimit(shortUrlLimitString);
            LinkDTO linkDTO = linkController.editLink(user, shortUrl, maxRedirectsLimit);
            cli.print("Короткая ссылка обновлена: " + linkDTO.getShortUrl());
            cli.print("Ваш UUID: " + linkDTO.getUserUuid());
        } catch (Exception e) {
            cli.print("Ошибка при создании ссылки: " + e.getMessage());
        }

    }

    private void handleDelete(User user) {
        // Реализация обработки команды удаления ссылки
    }

    private void handleRedirect(User user) {
        // Реализация обработки команды перенаправления по короткой ссылке
    }

    private void handleList(User user) {
        // Реализация обработки команды отображения списка ссылок пользователя
    }
}
