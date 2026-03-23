package master;

import shared.TaskMessage;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private Socket socket;
    private List<SlaveHandler> slaves;

    public ClientHandler(Socket socket, List<SlaveHandler> slaves) {
        this.socket = socket;
        this.slaves = slaves;
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            TaskMessage msg = (TaskMessage) in.readObject();

            SplitMergeExecutor executor = new SplitMergeExecutor(slaves);

            byte[] finalResult = executor.execute(msg);

            msg.setResultFile(finalResult);

            out.writeObject(msg);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}