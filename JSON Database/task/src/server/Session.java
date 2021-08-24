package server;

import server.controllers.Controller;

import server.models.Request;
import server.models.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Session extends Thread {

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Controller controller;
    private final AtomicBoolean serverStoppingFlag;

    public Session(Socket socket, DataInputStream inputStream, DataOutputStream outputStream, Controller controller, AtomicBoolean serverStoppingFlag) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.controller = controller;
        this.serverStoppingFlag = serverStoppingFlag;
    }


    @Override
    public void run() {
        try (socket; inputStream; outputStream) {
            Request request = Request.deserializeFromJson(inputStream.readUTF());

            Response res = controller.handleRequest(request);
            outputStream.writeUTF(res.serializeToJson());
            if (controller.isExit()) {
                serverStoppingFlag.set(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
