package linkshortener.infrastructure.scheduler;

import linkshortener.application.usecases.LinksManagerUseCase;
import linkshortener.domain.exceptions.LinkNotFoundException;

import java.util.Timer;
import java.util.TimerTask;

public class LinkCleanupScheduler {

    private final LinksManagerUseCase linksManagerUseCase;

    public LinkCleanupScheduler(LinksManagerUseCase linksManagerUseCase) {
        this.linksManagerUseCase = linksManagerUseCase;
    }

    public void start() {
        Timer timer = new Timer(true); // Фоновый поток
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    linksManagerUseCase.deleteExpiredLinks();
                } catch (LinkNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 10000); // Запуск каждые 10 сек
    }
}
