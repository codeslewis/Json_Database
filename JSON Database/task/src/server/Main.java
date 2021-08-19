package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 2000;

    public static void main(String[] args) {
//        startServer();
        System.out.println("Server started!");
        boolean exit = false;

        do {
            try (
                    ServerSocket serverSocket = new ServerSocket(PORT);
                    Socket socket = serverSocket.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                String request = input.readUTF();

                Database db = Database.getInstance();
                String response = db.handleInput(request.split(" "));

                output.writeUTF(response);
                if (db.isExit()) {
                    exit = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } while (!exit);
    }

    static void startServer() {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            Session session = new Session(server);
            System.out.println("New Session accepted");
            session.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
