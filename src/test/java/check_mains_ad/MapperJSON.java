package check_mains_ad;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.ResponseFromServer;

import java.io.IOException;

public class MapperJSON {

    enum TYPE {
        AD_LOADED, AD_FAILED, NO_RESPONSE
    }

    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature
            .FAIL_ON_UNKNOWN_PROPERTIES, false);

    public ResponseFromServer getMappedCorrectResponse(String jsonString, TYPE type)
            throws IOException {
        switch (type) {
            case AD_LOADED: {
                return mapper.readValue(jsonString, CorrectResponse.class);
            }
            case AD_FAILED: {
                return mapper.readValue(jsonString, IncorrectResponse.class);
            }
            case NO_RESPONSE: {
                return null;
            }
            default:{
                return null;
            }

        }
    }

    public ResponseFromServer getMappedIncorrectResponse(String jsonString)
            throws IOException {
        return mapper.readValue(jsonString, IncorrectResponse.class);
    }
}
