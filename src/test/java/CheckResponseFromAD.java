import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import responses.CorrectResponse;
import responses.IncorrectResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckResponseFromAD {
    public static void main(String[] args) throws IOException {

//        test_interstitial_p
//        626f11a289


        String URL = "https://loopme.me/api/v3/ads?callback=L.loadAdSucess&ak=626f11a289&vt=7nd3gly3ys";
        String URL3 = "https://google.com";
        String URL2 = "https://loopme.me/api/v3/ads?callback=L" +
                ".loadAdSucess&ak=test_interstitial_p&vt=7nd3gly3ys&ww=375&wh=667&width=375&height=667&delay=2000&di=di&impurl=http%3A%2F%2Floopme.biz&clickurl=http%3A%2F%2Floopme.biz%2Fclick";


        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL);
        HttpResponse response = client.execute(request);
        HttpEntity httpEntity = response.getEntity();

        String responseString = EntityUtils.toString(httpEntity, "UTF-8")
                .replace("L.loadAdSucess", "").replace("(", "").replace(")", "");
        StringBuilder builder = new StringBuilder();
        builder.append(response.getStatusLine().getStatusCode() + " " + response.getStatusLine()
                .getReasonPhrase());

        System.out.println(builder.toString());
        System.out.println(responseString);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CorrectResponse response1 = mapper.readValue(responseString, CorrectResponse.class);

        System.out.println(response1.toString());
        System.out.println(builder.toString());
        System.out.println(responseString);

        client.close();


//        {"statusCode":204, "message":"No content"}


//        BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//
//        while (reader.readLine() != null) {
//            System.out.println("Test" + reader.readLine());
//        }











        String str = "https://loopme.me/api/v3/ads?" +
                "callback=L.loadAdSucess&ak=626f11a289&vt=7nd3gly3ys&ww=375&wh=667&width=375&height=667" +
                "&delay=2000&di=di&impurl=http%3A%2F%2Floopme.biz&clickurl=http%3A%2F%2Floopme.biz%2Fclick";

        String str2 = "https://loopme.me/api/v3/ads?" +
                "callback=L.loadAdSucess&ak=test_interstitial_p&vt=7nd3gly3ys&ww=375&wh=667&width=375&height=667" +
                "&delay=2000&di=di&impurl=http%3A%2F%2Floopme.biz&clickurl=http%3A%2F%2Floopme.biz%2Fclick";

    }
}
