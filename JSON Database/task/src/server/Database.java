package server;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, String> data;
    private static Database instance;
    private boolean exit = false;

    {
        data = new HashMap<>();
    }

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean isExit() {
        return exit;
    }

    public Response handleRequest(Request request) {
        switch (request.getRequestType()) {
            case EXIT:
                return handleExitRequest();
            case GET:
                return handleGetRequest(request);
            case SET:
                return handleSetRequest(request);
            case DELETE:
                return handleDeleteRequest(request);
            default:
                assert false : "Not all Enum constants handled";
                return new Response();
        }
    }

    private Response handleDeleteRequest(Request request) {
        Response response = new Response();
        if (request.getOptionalKey().isPresent()) {
            String key = request.getOptionalKey().get();
            if (data.containsKey(key)) {
                data.remove(key);
                response.setResponse(Result.OK);
                response.setValue(null);
                response.setReason(null);
            } else {
                response.setReason("No such key");
            }
        } else {
            assert false : "Expect Key to be passed with DELETE request";
        }
        return response;
    }

    private Response handleSetRequest(Request request) {
        Response response = new Response();
        if (
            request.getOptionalKey().isPresent() &&
            request.getOptionalValue().isPresent()
        ) {
            data.put(
                request.getOptionalKey().get(),
                request.getOptionalValue().get()
            );
            response.setResponse(Result.OK);
            response.setReason(null);
        } else {
            assert false : "Expect Key and value to be passed with SET request";
        }
        return response;
    }

    private Response handleGetRequest(Request request) {
        Response response = new Response();
        if (request.getOptionalKey().isPresent()) {
            String key = request.getOptionalKey().get();
            if (data.containsKey(key)) {
                response.setResponse(Result.OK);
                response.setValue(data.get(key));
                response.setReason(null);
            } else {
                response.setReason("No such key");
            }
        } else {
            assert false : "Expect Key to be passed with GET request";
        }
        return response;
    }

    private Response handleExitRequest() {
        this.exit = true;
        Response response = new Response();
        response.setResponse(Result.OK);
        return response;
    }
}
