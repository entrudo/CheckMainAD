package check_mains_ad;

import org.testng.Assert;
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
    HttpServer httpServer;
    MapperJSON mapperJSON;

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

        if (httpServer.getResponseCode() != 200) {
            assertEquals(Integer.toString(httpServer.getResponseCode()) + " " + httpServer
                    .getResponseStatus(), "", "Page does not open server return: ");
            return;
        }

        ResponseFromServer responseFromServer = returnMappedObject(httpServer.gerResponseBody());

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

    public ResponseFromServer returnMappedObject(String jsonString) {
        ResponseFromServer responseObject = new CorrectResponse();
//        ResponseFromServer responseObject2 = new IncorrectResponse();


        try {
            responseObject = mapperJSON
                    .getMappedCorrectResponse(jsonString, MapperJSON.TYPE.AD_LOADED);
        } catch (IOException e) {
            responseObject = mapperJSON
                    .getMappedCorrectResponse(jsonString, MapperJSON.TYPE.AD_FAILED);
            responseObject = null;
        }

//        try {
//            responseObject2 = mapperJSON
//                    .getMappedIncorrectResponse(jsonString);
//        } catch (IOException e) {
//        }
//

        return responseObject;
    }
}
