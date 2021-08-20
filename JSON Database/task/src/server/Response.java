package server;

import com.google.gson.Gson;

public class Response {
    private String response;
    private String value;
    private String reason;

    public String serializeToJson() {
        return new Gson().toJson(this);
    }

    /*
        build with Enum Command, GET, SET, DELETE, EXIT
            Optional String value
            Optional String reason
        For now, no args ctor plus setters
     */

    public Response() {
        setResponse(Result.ERROR); // response with nothing set defaults to err
        setReason("Response not correctly built");
    }

    public void setResponse(Result result) {
        this.response = result == Result.OK ? "OK" : "ERROR";
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
