package loadbalancer;

import resource.BackendServer;
import strat.Strategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class L4LoadBalancer implements LoadBalancer {
    private final Strategy strategy;
    private final List<BackendServer> servers;

    public L4LoadBalancer(Strategy strategy, List<BackendServer> servers) {
        this.strategy = strategy;
        this.servers = servers;
    }

    @Override
    public BackendServer getServer() {
        int serverIdx = strategy.getServer(servers);
        if (serverIdx < 0 || serverIdx >= servers.size()) {
            throw new RuntimeException("Received illegal server to forward the request to");
        }

        return servers.get(serverIdx);
    }

    @Override
    public void forwardRequest(Socket clientSocket) {
        BackendServer backendServer = getServer();

        try (Socket backendSocket = new Socket(backendServer.getHost(), backendServer.getPort())) {
            forwardData(clientSocket, backendSocket);
            forwardData(backendSocket, clientSocket);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forwardData(Socket inputSocket, Socket outputSocket) {
        try (InputStream input = inputSocket.getInputStream();
             OutputStream output = outputSocket.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
