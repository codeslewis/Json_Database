package server.models;

import com.google.gson.Gson;

public class Response {
    private final String response;
    private final String value;
    private final String reason;

    public String serializeToJson() {
        return new Gson().toJson(this);
    }

    public Response(ResponseBuilder builder) {
        this.response = builder.response;
        this.value = builder.value;
        this.reason = builder.reason;
    }

    public static class ResponseBuilder {
        private String response;
        private String value;
        private String reason;

        public ResponseBuilder setResponse(Result result) {
            this.response = result == Result.OK ? "OK" : "ERROR";
            return this;
        }

        public ResponseBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public ResponseBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
