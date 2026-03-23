package user;

import shared.TaskMessage;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

public class UserClient {

    public static void main(String[] args) throws Exception {

        String masterIP = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(masterIP, port);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Scanner sc = new Scanner(System.in);

        System.out.print("Master Jar path: ");
        File masterJar = new File(sc.nextLine());

        System.out.print("Master Option: ");
        String masterOption = sc.nextLine();

        System.out.print("Slave Jar path: ");
        File slaveJar = new File(sc.nextLine());

        System.out.print("Slave Option: ");
        String slaveOption = sc.nextLine();

        System.out.print("File path: ");
        File inputFile = new File(sc.nextLine());

        TaskMessage msg = new TaskMessage(
                masterJar.getName(), Files.readAllBytes(masterJar.toPath()), masterOption,
                slaveJar.getName(), Files.readAllBytes(slaveJar.toPath()), slaveOption,
                inputFile.getName(), Files.readAllBytes(inputFile.toPath()));

        out.writeObject(msg);

        TaskMessage response = (TaskMessage) in.readObject();

        String timestamp = String.valueOf(System.currentTimeMillis());
        File outFile = new File("FinalResult_" + timestamp + ".jpg");
        Files.write(outFile.toPath(), response.getResultFile());

        System.out.println("Final result saved.");

        socket.close();
    }
}