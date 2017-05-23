package responses;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties({"macros", "package_ids", "measure_partners", "tracking"})
public class CorrectResponse implements ResponseFromServer {
    @JsonProperty
    private String eventLinkTemplate;
    @JsonProperty
    private String apiCreativesLink;
    @JsonProperty
    private String requestId;
    @JsonProperty
    private String[] ads;

    public String getEventLinkTemplate() {
        return eventLinkTemplate;
    }

    public String getApiCreativesLink() {
        return apiCreativesLink;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getAds() {
        return Arrays.toString(ads);
    }

    @Override
    public String toString() {
        return "CorrectResponse{" +
                "eventLinkTemplate='" + eventLinkTemplate + '\'' +
                ", apiCreativesLink='" + apiCreativesLink + '\'' +
                ", requestId='" + requestId + '\'' +
                ", ads=" + Arrays.toString(ads) +
                '}';
    }
}
