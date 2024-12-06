package linkshortener.infrastructure.services;

import linkshortener.application.interfaces.NotificationService;

public class NotificationServiceByCLI implements NotificationService {

    @Override
    public void sendNotification(String userUuid, String message) {
        // Отправка уведомления в терминал (консоль)
        System.out.println("Уведомление для пользователя " + userUuid + ": " + message);
    }
}
