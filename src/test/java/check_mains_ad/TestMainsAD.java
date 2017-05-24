package check_mains_ad;

import httpserver.ConstanceForAd;
import httpserver.HttpServer;
import httpserver.MapperJSON;
import httpserver.TypeOfAd;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import responses.CorrectResponse;
import responses.ErrorResponse;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class TestMainsAD {
    private HttpServer httpServer;
    private MapperJSON mapperJSON;
    private CorrectResponse correctResponse;
    private ErrorResponse errorResponse;

    @BeforeTest
    public void setUp() {
        httpServer = new HttpServer();
        mapperJSON = new MapperJSON();

    }

    @Test
    public void testLoopMeMainsAd() {
        String urlWithParams = ConstanceForAd.BASE_URL +
                "/api/v3/ads?callback=L.loadAdSucess&ak=1test_interstitial_p&vt=7nd3gly3ys";
        httpServer.makeRequest(urlWithParams);

        try {
            httpServer.executeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int responseCode = httpServer.getResponseCode().isPresent() ? httpServer.getResponseCode().get()
                : ConstanceForAd.UNSUPPORTED_RESPONSE_CODE;

        if (responseCode != ConstanceForAd.CORRECT_RESPONSE_CODE) {
            assertEquals(responseCode + " " + httpServer
                    .getResponseStatus(), "", "Page does not open, server return: ");
            return;
        }

        try {
            correctResponse = (CorrectResponse)
                    mapperJSON.getMappedResponse(httpServer.getResponseBody(), TypeOfAd.AD_LOADED);
        } catch (IOException e) {
            try {
                errorResponse = (ErrorResponse)
                        mapperJSON.getMappedResponse(httpServer.getResponseBody(), TypeOfAd.AD_FAILED);
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

        if (correctResponse != null) {
            assertEquals(correctResponse.getAds(), correctResponse.getAds());
        }

        if (errorResponse != null) {
            assertEquals(errorResponse.toString(), "",
                    "Server does not return AD. There is: ");
        }
    }

    @AfterTest
    public void tearDown() {
        try {
            httpServer.closeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
