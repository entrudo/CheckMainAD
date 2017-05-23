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
    private String regExp = "(?=\\{)[\\W\\D\\d]*?\\}";

    public HttpServer() {
        this.client = HttpClientBuilder.create().build();
    }

    public void makeRequest(String URL) {
        request = new HttpGet(URL);
    }

    public void executeRequest() throws IOException {
        response = client.execute(request);
    }

    public String getResponseBody() {
        HttpEntity httpEntity = response.getEntity();

        if (responseBody == null) {
            try {
                Pattern pattern = Pattern.compile(regExp);
                Matcher matcher = pattern.matcher(EntityUtils.toString(httpEntity, "UTF-8"));
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

//        try {
//            responseBody = EntityUtils.toString(httpEntity, "UTF-8")
//                    .replace("L.loadAdSucess", "").replace("(", "").replace(")", "");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
