package loadbalancer;

import resource.BackendServer;

import java.net.Socket;

public interface LoadBalancer {
    int PORT = 8888;

    public BackendServer getServer();

    public void forwardRequest(Socket clientSocket);
}