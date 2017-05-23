package check_mains_ad;

import httpserver.HttpServer;
import httpserver.MapperJSON;
import httpserver.TypeOfAd;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import responses.CorrectResponse;
import responses.ErrorResponse;
import responses.ResponseFromServer;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class TestMainsAD {
    private String baseUrl = "https://loopme.me";;
    private HttpServer httpServer;
    private MapperJSON mapperJSON;
    private ResponseFromServer responseFromServer;
    private int unsupportedResponseCode = 0;
    private int responseCode;

    @BeforeTest
    public void setUp() {
        httpServer = new HttpServer();
        mapperJSON = new MapperJSON();

    }

    @Test
    public void testLoopMeMainsAd() {
        String urlWithParams = baseUrl + "/api/v3/ads?callback=L.loadAdSucess&ak=test_interstitial_p&vt=7nd3gly3ys";
        httpServer.makeRequest(urlWithParams);

        try {
            httpServer.makeResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        responseCode = httpServer.getResponseCode().isPresent() ? httpServer.getResponseCode().get()
                : unsupportedResponseCode;

        if (responseCode != 200) {
            assertEquals(responseCode + " " + httpServer
                    .getResponseStatus(), "", "Page does not open, server return: ");
            return;
        }

        responseFromServer = returnMappedObject(httpServer.getResponseBody());

        if (responseFromServer instanceof CorrectResponse) {
            assertEquals(((CorrectResponse) responseFromServer).getAds(),
                    ((CorrectResponse) responseFromServer).getAds());
        }

        if (responseFromServer instanceof ErrorResponse) {
            assertEquals(responseFromServer.toString(), "",
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

    private ResponseFromServer returnMappedObject(String jsonString) {
        ResponseFromServer responseObject;

        try {
            responseObject = mapperJSON
                    .getMappedResponse(jsonString, TypeOfAd.AD_LOADED);
        } catch (IOException e) {
            try {
                responseObject = mapperJSON
                        .getMappedResponse(jsonString, TypeOfAd.AD_FAILED);
            } catch (IOException e1) {
                responseObject = null;
            }
        }

        return responseObject;
    }
}
