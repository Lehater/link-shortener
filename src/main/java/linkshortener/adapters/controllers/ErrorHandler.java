package linkshortener.adapters.controllers;

public class ErrorHandler {

    public static void handle(Exception e) {
        // Унифицированная обработка
        System.err.println("Ошибка: " + e.getMessage());
    }
}