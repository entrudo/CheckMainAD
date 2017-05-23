package httpserver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Optional;

public class HttpServer {
    private CloseableHttpClient client;
    private HttpGet request;
    private HttpResponse response;
    private String responseBody = "";

    public HttpServer() {
        this.client = HttpClientBuilder.create().build();
    }

    public void makeRequest(String URL) {
        request = new HttpGet(URL);
    }

    public void makeResponse() throws IOException {
        response = client.execute(request);
    }

    public String getResponseBody() {
        HttpEntity httpEntity = response.getEntity();

        try {
            responseBody = EntityUtils.toString(httpEntity, "UTF-8")
                    .replace("L.loadAdSucess", "").replace("(", "").replace(")", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    public Optional<Integer> getResponseCode() {
        return Optional.ofNullable(response.getStatusLine().getStatusCode());
    }

    public Optional<String> getResponseStatus() {
        return Optional.ofNullable(response.getStatusLine().getReasonPhrase());
    }

    public void closeClient() throws IOException {
        client.close();
    }
}
