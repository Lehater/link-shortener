package linkshortener.dinfrastructure.services;

import linkshortener.bapplication.interfaces.NotificationService;

public class NotificationServiceByCLI implements NotificationService {

    @Override
    public void sendNotification(String userUuid, String message) {
        // Отправка уведомления в терминал (консоль)
        System.out.println("Уведомление для пользователя " + userUuid + ": " + message);
    }
}
