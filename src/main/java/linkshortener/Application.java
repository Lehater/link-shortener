package linkshortener;

import linkshortener.infrastructure.config.AppConfigurator;
import linkshortener.presentation.cli.CommandLineInterface;
import linkshortener.presentation.controllers.AuthController;
import linkshortener.presentation.controllers.LinkController;

public class Application {

    public static void main(String[] args) {
        LinkController linkController = AppConfigurator.getLinkController();
        AuthController authController = AppConfigurator.getAuthController();
        CommandLineInterface cli = new CommandLineInterface(authController, linkController);
        cli.start();
    }
}
