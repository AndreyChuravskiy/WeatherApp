package weather.api;

import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;


public class WeatherApiClient {
    @Getter
    private final String API_KEY;

    public WeatherApiClient(String apiKey) {
        this.API_KEY = apiKey;
    }

    public ApiResponse getWeatherData(String apiUrl) {
        ApiResponse response = new ApiResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.addHeader("X-Yandex-API-Key", API_KEY);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                response.setResponseBody(EntityUtils.toString(httpResponse.getEntity()));
                response.setStatusCode(httpResponse.getCode());
            } catch (ParseException | IOException e) {
                e.printStackTrace();
                response.setStatusCode(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatusCode(-1);
        }
        System.out.println(response);
        return response;
    }
}