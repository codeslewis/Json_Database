package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 2000;
    private static final String DATA_PATH = "src/client/data/";

    public static void main(String[] args) {
        start(args);
    }

    private static void start(String[] argv) {
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

            String req = request.toString();

            if (request.isRequestFromFile()) {
                // reassign request to details in file
                String fileName = request.getInputFile();
                Path pathToFile = Paths.get(DATA_PATH, fileName);
                req = readJsonFromFile(pathToFile);
            }

            System.out.println("Sent: " + req);
            output.writeUTF(req);

            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readJsonFromFile(Path pathToFile) {
        try {
            return Files.readString(pathToFile);
        } catch (IOException e) {
            // Further handle this exception
            e.printStackTrace();
        }
        return "";
    }
}


