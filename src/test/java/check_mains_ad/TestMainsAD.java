package check_mains_ad;

import httpserver.HttpServer;
import httpserver.MapperJSON;
import httpserver.TypeOfAD;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.ResponseFromServer;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class TestMainsAD {
    private String URL;
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
        URL = "https://loopme.me/api/v3/ads?callback=L.loadAdSucess&ak=test_interstitial_p&vt=7nd3gly3ys";
        httpServer.makeRequest(URL);

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

        responseFromServer = returnMappedObject(httpServer.gerResponseBody());

        if (responseFromServer instanceof CorrectResponse) {
            assertEquals(((CorrectResponse) responseFromServer).getAds(), ((CorrectResponse) responseFromServer).getAds());
        }

        if (responseFromServer instanceof IncorrectResponse) {
            assertEquals(responseFromServer.toString(), "", "Server does not return AD. There is: ");
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
                    .getMappedResponse(jsonString, TypeOfAD.AD_LOADED);
        } catch (IOException e) {
            try {
                responseObject = mapperJSON
                        .getMappedResponse(jsonString, TypeOfAD.AD_FAILED);
            } catch (IOException e1) {
                responseObject = null;
            }
        }

        return responseObject;
    }
}
