package ch.heigvd.client;

import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.models.Game;

public class ClientStorage {
    private static ClientStorage instance;
    private String userId;
    private VirtualClient virtualClient;
    private ClientUpdateEndpoint updateEndpoint;
    private  Thread endpointThread;

    private ClientStorage() {

    }

    public static synchronized ClientStorage getInstance() {
        if(instance == null)
            instance = new ClientStorage();
        return  instance;
    }

    public synchronized String getUserId() {
        return userId;
    }
    public synchronized void setUserId(String userId) {
        this.userId = userId;
    }
    public synchronized ClientUpdateEndpoint getUpdateEndpoint() {
        return updateEndpoint;
    }
    public synchronized void setUpdateEndpoint(ClientUpdateEndpoint updateEndpoint) {
        stopUpdateEndpoint();
        this.updateEndpoint = updateEndpoint;
    }
    public synchronized void startUpdateEndpoint() {
        endpointThread = new Thread(updateEndpoint);
        endpointThread.start();
    }
    public synchronized void stopUpdateEndpoint() {
        if(updateEndpoint != null) updateEndpoint.stop();
        if(updateEndpoint != null) endpointThread.interrupt();
    }

    public synchronized VirtualClient getVirtualClient() {
        return virtualClient;
    }
    public synchronized void setVirtualClient(VirtualClient virtualClient) {
        this.virtualClient = virtualClient;
    }
}
