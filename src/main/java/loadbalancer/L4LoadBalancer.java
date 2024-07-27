package loadbalancer;

public class L4LoadBalancer implements LoadBalancer {

    @Override
    public int getServer() {
        return 0;
    }

    @Override
    public void forwardRequest() {

    }
}
