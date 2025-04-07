import java.util.ArrayList;

public class Network {
    private ArrayList<Server> servers;

    public Network() {
        this.servers = new ArrayList<>();
    }

    public void addServer(Server server) {
        servers.add(server);
    }

    // So that we can forbid any modifications to the servers list
    public Server[] getServers() {
        Server[] serverArray = new Server[servers.size()];
        return servers.toArray(serverArray);
    }

    public String toString() {
        return String.join("\n", servers.stream().map((s) -> s.toString()).toArray(String[]::new));
    }
}