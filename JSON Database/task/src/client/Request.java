package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class Request {
    @Parameter(names = { "-t", "--type" }, description = "CRUD operation")
    private String type;

    @Parameter(names = { "-k", "--key" }, description = "String key")
    private String key;

    @Parameter(names = { "-v", "--value" }, description = "Value to save in 'set' operation")
    private String value;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
