import loadbalancer.L4LoadBalancer;
import loadbalancer.LoadBalancer;
import strat.Strategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        Strategy lbStrategy;
        LoadBalancer loadBalancer = new L4LoadBalancer(lbStrategy, new ArrayList<>());

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());

            loadBalancer.forwardRequest(clientSocket);
        }
    }
}