package com.github.ArthurSchiavom.pwassistant.control.pwi;

import com.github.ArthurSchiavom.pwassistant.control.repository.PwiClockCachedRepository;
import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class PwiServerService {
    private static final int SERVER_PING_TIMEOUT_MS = 1000;

    @Inject
    PwiClockCachedRepository pwiClockRepo;

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

    public void registerPwiClock(final PwiClock pwiClock) {
        // A max of 10 clocks per server can be created
        final List<PwiClock> clocksInServer = pwiClockRepo.getAllItemsOfServer(pwiClock.getServerId());
        if (clocksInServer.size() > 10) {
            clocksInServer.sort(Comparator.comparingLong(PwiClock::getMessageId));
            pwiClockRepo.delete(clocksInServer.get(0));
        }

        pwiClockRepo.create(pwiClock);
    }

    public List<PwiClock> getAllPwiClocks() {
        return pwiClockRepo.getAllItems();
    }

    public void removePwiClock(final PwiClock clock) {
        pwiClockRepo.delete(clock);
    }
}
