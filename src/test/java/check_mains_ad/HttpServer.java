package check_mains_ad;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpServer {
    private CloseableHttpClient client = HttpClientBuilder.create().build();
    private HttpGet request;
    private HttpResponse response;
    private HttpEntity httpEntity;

    public HttpServer() {
    }

    public void makeRequest(String URL) {
        request = new HttpGet(URL);
    }

    public void makeResponse() throws IOException {
        response = client.execute(request);
    }

    public String gerResponseBody() {
        httpEntity = response.getEntity();
        String responseBody = "";
        try {
            responseBody = EntityUtils.toString(httpEntity, "UTF-8")
                    .replace("L.loadAdSucess", "").replace("(", "").replace(")", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    public Integer getResponseCode() {
        return response.getStatusLine().getStatusCode();
    }

    public String getResponseStatus() {
        return response.getStatusLine().getReasonPhrase();
    }

    public void closeClient() throws IOException {
        client.close();
    }
}
