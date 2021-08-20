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

    public RequestType getRequestType() {
        return RequestType.getTypeFromString(this.type);
    }

    public Optional<String> getOptionalKey() {
        return Optional.of(key);
    }

    public Optional<String> getOptionalValue() {
        return Optional.of(value);
    }
}
