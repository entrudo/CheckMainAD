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
    private CorrectResponse correctResponse;
    private ErrorResponse errorResponse;

    @BeforeTest
    public void setUp() {
        httpServer = new HttpServer();
        mapperJSON = new MapperJSON();

    }

    @Test
    public void testLoopMeMainsAd() {
        String urlWithParams = baseUrl + "/api/v3/ads?callback=L.loadAdSucess&ak=626f11a289&vt=7nd3gly3ys";
        httpServer.makeRequest(urlWithParams);

        try {
            httpServer.executeRequest();
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



        try {
            correctResponse = (CorrectResponse) returnMapOb(httpServer.getResponseBody(), TypeOfAd.AD_LOADED);
        } catch (IOException e) {
            try {
                errorResponse = (ErrorResponse) returnMapOb(httpServer.getResponseBody(), TypeOfAd.AD_FAILED);
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

//        responseFromServer = returnMappedObject(httpServer.getResponseBody());
//
//        if (responseFromServer instanceof CorrectResponse) {
//            assertEquals(((CorrectResponse) responseFromServer).getAds(),
//                    ((CorrectResponse) responseFromServer).getAds());
//        }
//
//        if (responseFromServer instanceof ErrorResponse) {
//            assertEquals(responseFromServer.toString(), "",
//                    "Server does not return AD. There is: ");
//        }
    }

    @AfterTest
    public void tearDown() {
        try {
            httpServer.closeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object returnMapOb(String jsonSting, TypeOfAd type) throws IOException {
        return mapperJSON.getMappedResponse2(jsonSting, type);
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
