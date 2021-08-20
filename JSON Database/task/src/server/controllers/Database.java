package server.controllers;

import server.exceptions.NoSuchKeyException;
import server.models.Request;
import server.models.Response;
import server.models.Result;
import server.repositories.DataRepository;

public class Database extends Controller {
    private final DataRepository source;

    public Database(DataRepository source) {
        this.source = source;
    }

    @Override
    protected Response handleDeleteRequest(Request request) {
        Response.ResponseBuilder response = new Response.ResponseBuilder();
        if (request.getOptionalKey().isPresent()) {
            String key = request.getOptionalKey().get();
            try {
                source.delete(key);
                response.setResponse(Result.OK);
            } catch (NoSuchKeyException e) {
                response
                    .setResponse(Result.ERROR)
                    .setReason("No such key");
            }
        } else {
            assert false : "Expect Key to be passed with DELETE request";
            response
                .setResponse(Result.ERROR)
                .setReason("Expect Key to be passed with DELETE request");
        }
        return response.build();
    }

    @Override
    protected Response handleSetRequest(Request request) {
        Response.ResponseBuilder response = new Response.ResponseBuilder();
        if (
            request.getOptionalKey().isPresent() &&
            request.getOptionalValue().isPresent()
        ) {
            source.set(
                request.getOptionalKey().get(),
                request.getOptionalValue().get()
            );
            response.setResponse(Result.OK);
        } else {
            assert false : "Expect Key and value to be passed with SET request";
            response
                .setResponse(Result.ERROR)
                .setReason("Either key or value not passed with SET request");
        }
        return response.build();
    }

    @Override
    protected Response handleGetRequest(Request request) {
        Response.ResponseBuilder response = new Response.ResponseBuilder();
        if (request.getOptionalKey().isPresent()) {
            String key = request.getOptionalKey().get();
            try {
                response
                    .setValue(source.get(key))
                    .setResponse(Result.OK);
            } catch (NoSuchKeyException e) {
                response
                    .setResponse(Result.ERROR)
                    .setReason("No such key");
            }
        } else {
            assert false : "Expect Key to be passed with GET request";
        }
        return response.build();
    }
}
