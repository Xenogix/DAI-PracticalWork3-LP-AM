package ch.heigvd.server.commands;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.VirtualUpdateServer;
import ch.heigvd.data.models.Game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerUpdateListener implements GameUpdateListener {
    private final VirtualUpdateServer updateSender;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<?> updateTask;

    public ServerUpdateListener(VirtualUpdateServer updateSender) {
        this.updateSender = updateSender;
    }

    @Override
    public void gameUpdated(Game game) {
        if (updateTask == null || updateTask.isDone()) {
            updateTask = executorService.submit(() -> updateSender.send(game));
        }
    }
}
