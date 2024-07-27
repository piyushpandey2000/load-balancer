package loadbalancer;

public interface LoadBalancer {
    int PORT = 8888;

    public int getServer();

    public void forwardRequest();
}