package slave;

import shared.TaskMessage;
import java.io.*;
import java.net.Socket;

public class Slave {

    public static void main(String[] args) throws Exception {

        String masterIP = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(masterIP, port);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        JarExecutor executor = new JarExecutor();

        while (true) {

            TaskMessage msg = (TaskMessage) in.readObject();

            byte[] result = executor.execute(msg);

            msg.setResultFile(result);

            out.writeObject(msg);
        }
    }
}