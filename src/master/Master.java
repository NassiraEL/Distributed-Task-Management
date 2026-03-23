package master;

import java.net.*;
import java.util.*;

public class Master {

    private static Master instance;

    private int userPort;
    private int slavePort;

    private ServerSocket userSocket;
    private ServerSocket slaveSocket;

    private final List<SlaveHandler> slaves = Collections.synchronizedList(new ArrayList<>());

    private Master(int userPort, int slavePort) {
        this.userPort = userPort;
        this.slavePort = slavePort;
    }

    public static Master getInstance(int userPort, int slavePort) {
        if (instance == null)
            instance = new Master(userPort, slavePort);
        return instance;
    }

    public void start() throws Exception {

        userSocket = new ServerSocket(userPort);
        slaveSocket = new ServerSocket(slavePort);

        // Thread accepting slaves
        new Thread(() -> {
            while (true) {
                try {
                    Socket s = slaveSocket.accept();
                    SlaveHandler sh = new SlaveHandler(s);
                    slaves.add(sh);
                    sh.start();
                    System.out.println("Slave connected.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Accept clients
        while (true) {
            Socket client = userSocket.accept();
            new ClientHandler(client, slaves).start();
        }
    }

    public static void main(String[] args) throws Exception {
        Master.getInstance(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])).start();
    }
}