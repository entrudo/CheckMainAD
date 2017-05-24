package httpserver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {
    private CloseableHttpClient client;
    private HttpGet request;
    private HttpResponse response;
    private String responseBody;

    public HttpServer() {
        this.client = HttpClientBuilder.create().build();
    }

    public void makeRequest(String url) {
        request = new HttpGet(url);
    }

    public void executeRequest() throws IOException {
        response = client.execute(request);
    }

    public String getResponseBody() {

        if (responseBody == null) {
            try {
                Pattern pattern = Pattern.compile(ConstanceForAd.REG_EXP);
                Matcher matcher = pattern.matcher(getFullResponseBody().get());
                if (matcher.find()) {
                    responseBody = matcher.group();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        } else {
            return responseBody;
        }
    }

    public Optional<Integer> getResponseCode() {
        return Optional.ofNullable(response.getStatusLine().getStatusCode());
    }

    public Optional<String> getResponseStatus() {
        return Optional.ofNullable(response.getStatusLine().getReasonPhrase());
    }

    public Optional<String> getFullResponseBody() throws IOException {
        HttpEntity httpEntity;

        if (response != null) {
            httpEntity = response.getEntity();
            return Optional.ofNullable(EntityUtils.toString(httpEntity, "UTF-8"));
        }else {
            throw new IOException("Response is null");
        }
    }

    public void closeClient() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
