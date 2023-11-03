package com.github.ArthurSchiavom.pwassistant.control.pwi;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PwiServerService {
    private static final int SERVER_PING_TIMEOUT_MS = 1000;

    /**
     * Checks if a server is reachable or not and returns a string accordingly.
     *
     * @param server PWI server to check availability of
     * @return true or false according to whether the program .
     */
    public boolean isServerUp(final PwiServer server) {
        return isServerUp(server.getUrl(), server.getPort());
    }

    /**
     * Checks if a server is reachable or not and returns a string accordingly.
     *
     * @param serverAddress the server's address to use for the connection.
     * @param serverPort    the port to use for the connection.
     * @return "down" or "up" according to whether the program fails to connect or no and "not responding. Probably booting up." if the server does not respond.
     */
    private boolean isServerUp(final String serverAddress, final int serverPort) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(serverAddress, serverPort), SERVER_PING_TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (!s.isClosed())
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
