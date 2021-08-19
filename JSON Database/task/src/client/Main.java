package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 2000;

    public static void main(String[] args) {
        start(args);
    }

    static void start(String[] argv) {
        System.out.println("Client started!");

        try (
            Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Request request = new Request();
            JCommander.newBuilder()
                    .addObject(request)
                    .build()
                    .parse(argv);

            System.out.println("Sent: " + request);
            output.writeUTF(request.toString());

            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client closing socket");
        }

    }
}


