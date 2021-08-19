package client;

import com.beust.jcommander.Parameter;

public class Request {
    @Parameter(names = { "-t", "--type" }, description = "CRUD operation")
    private String type;

    @Parameter(names = { "-i", "--index" }, description = "Index of the db cell")
    private int index;

    @Parameter(names = { "-m", "--message" }, description = "Value to save in 'set' operation")
    private String message;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        sb.append(" ");
        sb.append(index);
        if (message != null) {
            sb.append(" ");
            sb.append(message);
        }
        return sb.toString();
    }
}
