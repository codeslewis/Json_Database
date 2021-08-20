package server.models;

public enum RequestType {
    EXIT("exit"),
    GET("get"),
    SET("set"),
    DELETE("delete");

    private final String stringRepresentation;

    RequestType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static RequestType getTypeFromString(String str) {
        switch (str) {
            case "get":
                return RequestType.GET;
            case "set":
                return RequestType.SET;
            case "delete":
                return RequestType.DELETE;
            case "exit":
                return RequestType.EXIT;
            default:
                assert false : "Invalid Request type passed";
                return null;
        }
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
