package linkshortener.presentation.cli;

import linkshortener.presentation.controllers.AuthController;
import linkshortener.presentation.controllers.LinkController;
import linkshortener.presentation.dtos.LinkDTO;
import linkshortener.presentation.dtos.UserDTO;
import utils.ConsoleUtils;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class CommandLineInterface {

    private final AuthController authController;
    private final LinkController linkController;
    private final ConsoleUtils cli;

    public CommandLineInterface(AuthController authController, LinkController linkController) {
        this.authController = authController;
        this.linkController = linkController;
        this.cli = new ConsoleUtils();
    }

    public void start() {
        cli.print("Добро пожаловать в Сервис Сокращения Ссылок!");
        while (true) {
            cli.print("\nДля начала работы выполните вход.");
            String userUuid = cli.getNextLine("Введите ваш UUID (оставьте пустым для создания нового): ");
            UserDTO userDTO = authController.authenticate(userUuid);
            cli.print("Добро пожаловать, пользователь с UUID: " + userDTO.getUuid());

            // Начало пользовательской сессии
            session(userDTO);
        }
    }

    private void session(UserDTO userDTO) {
        while (true) {
            String command = cli.getNextLine(
                    "Введите команду (create, edit, delete, redirect, list, logout, exit): "
            ).trim().toLowerCase();

            switch (command) {
                case "create":
                    handleCreate(userDTO);
                    break;
                case "edit":
                    handleEdit(userDTO);
                    break;
                case "delete":
                    handleDelete(userDTO);
                    break;
                case "redirect":
                    handleRedirect(userDTO);
                    break;
                case "list":
                    handleList(userDTO);
                    break;
                case "logout":
                    cli.print("Выход из текущей сессии.");
                    return; // Выход из сессии, возвращаемся к аутентификации
                case "exit":
                    cli.print("Выход из приложения.");
                    System.exit(0); // Полный выход из приложения
                default:
                    cli.print("Неизвестная команда.");
            }
        }
    }

    private void handleCreate(UserDTO userDTO) {
        String originalUrl = cli.getNextLine("Введите оригинальный URL (например: http://ya.ru): ");
        try {
            LinkDTO linkDTO = linkController.createLink(userDTO, originalUrl);
            cli.print("Короткая ссылка создана: " + linkDTO.getShortUrl());
        } catch (Exception e) {
            cli.print("Ошибка при создании ссылки: " + e.getMessage());
        }
    }

    private void handleEdit(UserDTO userDTO) {
        String shortUrl = cli.getNextLine("Введите короткую ссылку для редактирования: ");
        String shortUrlLimitString = cli.getNextLine("Введите лимит переходов для этой ссылки: ");
        try {
            LinkDTO linkDTO = linkController.editLink(userDTO, shortUrl, shortUrlLimitString);
            cli.print("Короткая ссылка обновлена: " + linkDTO.getShortUrl());
        } catch (Exception e) {
            cli.print("Ошибка при редактировании ссылки: " + e.getMessage());
        }
    }

    private void handleDelete(UserDTO userDTO) {
        String shortUrl = cli.getNextLine("Введите короткую ссылку для удаления: ");
        try {
            linkController.deleteLink(userDTO, shortUrl);
            cli.print("Короткая ссылка успешно удалена: " + shortUrl);
        } catch (Exception e) {
            cli.print("Ошибка при удалении ссылки: " + e.getMessage());
        }
    }

    private void handleRedirect(UserDTO userDTO) {
        String shortUrl = cli.getNextLine("Введите короткую ссылку для перенаправления: ");
        try {
            String originalUrl = linkController.redirectLink(shortUrl);
            cli.print("Открываю в браузере: " + originalUrl);

            // Открытие браузера с использованием Desktop API
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(originalUrl));
        } catch (Exception e) {
            cli.print("Ошибка при перенаправлении: " + e.getMessage());
        }
    }

    private void handleList(UserDTO userDTO) {
        try {
            List<LinkDTO> links = linkController.listLinks(userDTO);
            if (links.isEmpty()) {
                cli.print("У вас нет коротких ссылок.");
            } else {
                cli.print("Ваши короткие ссылки:");
                for (LinkDTO link : links) {
                    cli.print("- " + link.getShortUrl());
                }
            }
        } catch (Exception e) {
            cli.print("Ошибка при получении списка ссылок: " + e.getMessage());
        }
    }
}
