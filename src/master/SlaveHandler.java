package master;

import shared.TaskMessage;
import java.io.*;
import java.net.Socket;

public class SlaveHandler extends Thread {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private volatile boolean connected;
    private final Object readyLock = new Object();

    public SlaveHandler(Socket socket) {
        this.socket = socket;
        this.connected = false;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            
            synchronized (readyLock) {
                connected = true;
                readyLock.notifyAll();
            }
            System.out.println("SlaveHandler ready for slave: " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            System.err.println("Failed to initialize slave connection: " + e.getMessage());
            synchronized (readyLock) {
                connected = false;
                readyLock.notifyAll();
            }
        }
    }

    public void waitUntilReady() throws InterruptedException {
        synchronized (readyLock) {
            while (!connected && out == null) {
                readyLock.wait();
            }
        }
    }

    public synchronized void sendTask(TaskMessage task) throws IOException {
        if (!connected || out == null) {
            throw new IOException("Slave not connected");
        }
        out.writeObject(task);
        out.flush();
        System.out.println("Task sent to slave: " + socket.getRemoteSocketAddress());
    }

    public synchronized TaskMessage getResult() throws IOException, ClassNotFoundException {
        if (!connected || in == null) {
            throw new IOException("Slave not connected");
        }
        TaskMessage result = (TaskMessage) in.readObject();
        System.out.println("Result received from slave: " + socket.getRemoteSocketAddress());
        return result;
    }

    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }

    public void close() {
        try {
            connected = false;
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("SlaveHandler closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
