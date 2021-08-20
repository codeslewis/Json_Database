package server.controllers;

import server.models.Request;
import server.models.Response;
import server.models.Result;

public abstract class Controller {
    protected boolean exit;

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
                return new Response.ResponseBuilder()
                        .setResponse(Result.ERROR)
                        .setReason("Unexpected Argument")
                        .build();
        }
    }
    protected abstract Response handleDeleteRequest(Request request);
    protected abstract Response handleSetRequest(Request request);
    protected abstract Response handleGetRequest(Request request);

    protected Response handleExitRequest() {
        this.exit = true;
        return new Response.ResponseBuilder()
                .setResponse(Result.OK)
                .build();
    }
}
