package ch.heigvd.data.abstractions;

import ch.heigvd.data.models.Game;

public interface VirtualUpdateServer {
    void send(Game game);
}
