package linkshortener;

import linkshortener.dinfrastructure.config.AppConfigurator;
import linkshortener.cadapters.cli.CommandLineInterface;
import linkshortener.cadapters.controllers.AuthController;
import linkshortener.cadapters.controllers.LinkController;

public class Application {

    public static void main(String[] args) {
        LinkController linkController = AppConfigurator.getLinkController();
        AuthController authController = AppConfigurator.getAuthController();
        CommandLineInterface cli = new CommandLineInterface(authController, linkController);
        cli.start();
    }
}
