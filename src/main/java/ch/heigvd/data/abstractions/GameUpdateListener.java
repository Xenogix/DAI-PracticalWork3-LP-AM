package ch.heigvd.data.abstractions;

import ch.heigvd.data.models.Game;

public interface GameUpdateListener {
    void gameUpdated(Game game);
}
