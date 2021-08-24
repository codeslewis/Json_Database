package server;

import server.controllers.Controller;
import server.controllers.Database;
import server.repositories.DataRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 2000;
    private static final AtomicBoolean exit = new AtomicBoolean(false);
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Controller dbController = new Database(DataRepository.getInstance());

    public static void main(String[] args) {
        System.out.println("Server started!");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(5);

            while (!exit.get()) {
                Socket socket;

                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                executor.submit(new Session(socket, inputStream, outputStream, dbController, exit));
            }

            executor.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
