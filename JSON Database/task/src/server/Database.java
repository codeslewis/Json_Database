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
        switch (request.getType()) {
            case EXIT:
                // pass exit
                return handleExitRequest();
            case GET:
                // build get response
                return handleGetRequest(request);
            case SET:
                // build set response
                return handleSetRequest(request);
            case DELETE:
                // build delete response
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

//    public String handleInput(String[] commands) {
//        Command command = Command.valueOf(commands[0].toUpperCase());
//        if (command == Command.EXIT) {
//            this.exit = true;
//            return "OK";
//        }
//        if (indexOutOfBounds(Integer.parseInt(commands[1]))) {
//            return "ERROR";
//        }
//        switch (command) {
//            case GET:
//                return get(Integer.parseInt(commands[1]));
//            case SET:
//                return set(Integer.parseInt(commands[1]), buildString(commands));
//            case DELETE:
//                return delete(Integer.parseInt(commands[1]));
//            default:
//                return "ERROR";
//        }
//    }

//    private String get(int index) {
//        if (cells[index - 1] == null) {
//            return "ERROR";
//        }
//        return cells[index - 1];
//    }
//
//    private String set(int index, String value) {
//        cells[index -1] = value;
//        return "OK";
//    }
//
//    private String delete(int index) {
//        cells[index - 1] = null;
//        return "OK";
//    }
//
//    private boolean indexOutOfBounds(int index) {
//        return index < 1 || index > 100;
//    }
//
//    private String buildString(String[] commands) {
//        StringBuilder result = new StringBuilder();
//        for (int i = 2; i < commands.length; i++) {
//            result.append(commands[i]);
//            result.append(" ");
//        }
//        result.deleteCharAt(result.length() - 1);
//        return result.toString();
//    }
}
