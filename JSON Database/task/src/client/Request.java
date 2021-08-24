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

    @Parameter(names = { "-in", "--input-file" }, description = "JSON file to read requests from")
    private String inputFile;

    public boolean isRequestFromFile() {
        return this.inputFile != null;
    }

    public String getInputFile() {
        assert isRequestFromFile() : "Trying to get filename when it hasn't been passed as an arg";
        return this.inputFile;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
