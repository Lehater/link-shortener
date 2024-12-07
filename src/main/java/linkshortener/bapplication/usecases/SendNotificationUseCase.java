package linkshortener.bapplication.usecases;

import linkshortener.adomain.valueobjects.UUID;
import linkshortener.bapplication.interfaces.NotificationService;

public class SendNotificationUseCase {

    private final NotificationService notificationService;

    public SendNotificationUseCase(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void execute(String userUuidString, String message) {

        UUID userUUID = new UUID(userUuidString);

        notificationService.sendNotification(userUUID.toString(), message);
    }
}
