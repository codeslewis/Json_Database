package server;

import com.google.gson.Gson;

import java.util.Optional;

public class Request {
    private String type;
    private String key;
    private String value;

    private Request() {}

    public static Request deserializeFromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }

    public Command getType() {
        switch (this.type) {
            case "get":
                return Command.GET;
            case "set":
                return Command.SET;
            case "delete":
                return Command.DELETE;
            case "exit":
                return Command.EXIT;
            default:
                assert false : "Invalid Request type passed";
                return null;
        }
    }

    public Optional<String> getOptionalKey() {
        return Optional.of(key);
    }

    public Optional<String> getOptionalValue() {
        return Optional.of(value);
    }
}
