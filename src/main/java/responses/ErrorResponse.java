package responses;

import org.codehaus.jackson.annotate.JsonProperty;

public class ErrorResponse implements ResponseFromServer {
    @JsonProperty
    private int statusCode;
    @JsonProperty
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
