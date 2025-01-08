package linkshortener.application.usecases.notification;

import linkshortener.domain.valueobjects.CustomUUID;
import linkshortener.application.interfaces.NotificationService;

public class SendNotificationUseCase {

    private final NotificationService notificationService;

    public SendNotificationUseCase(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void execute(String userUuidString, String message) {

        CustomUUID userUUID = new CustomUUID(userUuidString);

        notificationService.sendNotification(userUUID.toString(), message);
    }
}
