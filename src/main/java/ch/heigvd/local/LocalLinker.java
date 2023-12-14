package ch.heigvd.local;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.ResponseCommandHandler;

public class LocalLinker {
    private LocalServer server;
    private LocalClient client;

    public LocalLinker(LocalServer server, LocalClient client) {
        this.server = server;
        this.client = client;
    }

    public LocalClient getClient() {
        return client;
    }

    public LocalServer getServer() {
        return server;
    }

    public void bind() {
        server.bind(this);
        client.bind(this);
    }
}
