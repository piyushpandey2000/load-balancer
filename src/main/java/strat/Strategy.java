package strat;

import resource.BackendServer;

import java.util.List;

public interface Strategy {

    public int getServer(List<BackendServer> servers);
}
