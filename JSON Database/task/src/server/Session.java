package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Session extends Thread {

    private final ServerSocket socketForClient;
    private boolean exit = false;

    public Session(ServerSocket socketForClient) {
        this.socketForClient = socketForClient;
    }

    @Override
    public void run() {
        do {

        } while (!exit);
        try (
                Socket socket = socketForClient.accept();
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
    }

}
