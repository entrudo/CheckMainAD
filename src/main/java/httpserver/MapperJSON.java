package httpserver;

import org.codehaus.jackson.map.ObjectMapper;
import responses.CorrectResponse;
import responses.ErrorResponse;
import responses.ResponseFromServer;

import java.io.IOException;

public class MapperJSON {
    private ObjectMapper mapper = new ObjectMapper();

    public ResponseFromServer getMappedResponse(String jsonString, TypeOfAd type)
            throws IOException {
        switch (type) {
            case AD_LOADED: {
                return mapper.readValue(jsonString, CorrectResponse.class);
            }
            case AD_FAILED: {
                return mapper.readValue(jsonString, ErrorResponse.class);
            }
            default: {
                throw new IOException("Unsupported Class");
            }

        }
    }

    public Object getMappedResponse2(String jsonString, TypeOfAd type)
            throws IOException {
        switch (type) {
            case AD_LOADED: {
                return mapper.readValue(jsonString, CorrectResponse.class);
            }
            case AD_FAILED: {
                return mapper.readValue(jsonString, ErrorResponse.class);
            }
            default: {
                throw new IOException("Unsupported Class");
            }

        }
    }
}
