package weather.api;

import lombok.Getter;
import lombok.Setter;
import weather.WeatherState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WeatherUpdater {
    private final WeatherApiClient API_CLIENT;

    @Getter
    @Setter
    private String latitude;

    @Getter
    @Setter
    private String longitude;

    public WeatherUpdater(String apiKey, String lat, String lon) {
        this.API_CLIENT = new WeatherApiClient(apiKey);
        this.latitude = lat;
        this.longitude = lon;
    }

    public WeatherState fetchWeatherData() {
        WeatherState weatherState = new WeatherState();
        String apiUrl = buildApiUrl(latitude, longitude);
        ApiResponse apiResponse = API_CLIENT.getWeatherData(apiUrl);


        if (apiResponse != null && apiResponse.getStatusCode() == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                weatherState = objectMapper.readValue(apiResponse.getResponseBody(), WeatherState.class);
            } catch (JsonProcessingException e) {
                handleJsonParsingError(e);
            }
        } else {
            handleApiError(apiResponse);
        }

        return weatherState;
    }

    private String buildApiUrl(String latitude,String longitude) {
        return "https://api.weather.yandex.ru/v2/informers?" +
                "lat=" + latitude +
                "&lon=" + longitude +
                "&lang=ru-BY";
    }

    private void handleJsonParsingError(JsonProcessingException e) {
        System.out.println("Error parsing API response: " + e.getMessage());
    }

    private void handleApiError(ApiResponse apiResponse) {
        if (apiResponse != null) {
            System.out.println("Failed to fetch weather data. Status code: " + apiResponse.getStatusCode());
        } else {
            System.out.println("Failed to fetch weather data. Unknown error occurred.");
        }
    }

}
