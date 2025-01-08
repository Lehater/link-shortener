package linkshortener;

import linkshortener.infrastructure.config.AppConfigurator;
import linkshortener.adapters.cli.CommandLineInterface;
import linkshortener.adapters.controllers.AuthController;
import linkshortener.adapters.controllers.LinkController;
import linkshortener.infrastructure.scheduler.LinkCleanupScheduler;
import linkshortener.infrastructure.services.ConfigServicePropsFile;

public class Application {

    public static void main(String[] args) {
        LinkController linkController = AppConfigurator.getLinkController();
        AuthController authController = AppConfigurator.getAuthController();
        CommandLineInterface cli = new CommandLineInterface(authController, linkController);

        // Запуск планировщика
        LinkCleanupScheduler scheduler = AppConfigurator.getScheduler();
        scheduler.start();

        // Запуск CLI
        cli.start();
    }
}
