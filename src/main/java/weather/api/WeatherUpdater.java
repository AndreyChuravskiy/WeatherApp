package weather.api;

import lombok.Getter;
import lombok.Setter;
import weather.WeatherState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WeatherUpdater {
    private final WeatherApiClient API_CLIENT;

    private final String TESTING_RESPONSE_BODY = "{\"now\":1716397426,\"now_dt\":\"2024-05-22T17:03:46.882229Z\",\"info\":{\"url\":\"https://yandex.com/weather/157?lat=53.9\u0026lon=27.5667\",\"lat\":53.9,\"lon\":27.5667},\"fact\":{\"obs_time\":1716397426,\"temp\":20,\"feels_like\":18,\"icon\":\"bkn_d\",\"condition\":\"cloudy\",\"wind_speed\":3.6,\"wind_dir\":\"se\",\"pressure_mm\":744,\"pressure_pa\":991,\"humidity\":62,\"daytime\":\"d\",\"polar\":false,\"season\":\"spring\",\"wind_gust\":7.7},\"forecast\":{\"date\":\"2024-05-23\",\"date_ts\":1716411600,\"week\":21,\"sunrise\":\"04:55\",\"sunset\":\"21:17\",\"moon_code\":0,\"moon_text\":\"moon-code-0\",\"parts\":[{\"part_name\":\"night\",\"temp_min\":15,\"temp_avg\":16,\"temp_max\":18,\"wind_speed\":3.4,\"wind_gust\":6,\"wind_dir\":\"e\",\"pressure_mm\":745,\"pressure_pa\":993,\"humidity\":81,\"prec_mm\":0,\"prec_prob\":0,\"prec_period\":480,\"icon\":\"bkn_n\",\"condition\":\"cloudy\",\"feels_like\":14,\"daytime\":\"n\",\"polar\":false},{\"part_name\":\"morning\",\"temp_min\":14,\"temp_avg\":17,\"temp_max\":21,\"wind_speed\":4.7,\"wind_gust\":9.8,\"wind_dir\":\"e\",\"pressure_mm\":746,\"pressure_pa\":994,\"humidity\":74,\"prec_mm\":0,\"prec_prob\":0,\"prec_period\":360,\"icon\":\"bkn_d\",\"condition\":\"cloudy\",\"feels_like\":15,\"daytime\":\"d\",\"polar\":false}]}}";

    @Getter
    @Setter
    private double latitude;

    @Getter
    @Setter
    private double longitude;

    public WeatherUpdater(String apiKey, double lat, double lon) {
        this.API_CLIENT = new WeatherApiClient(apiKey);
        this.latitude = lat;
        this.longitude = lon;
    }

    public WeatherState fetchWeatherData(boolean testing) {
        WeatherState weatherState = new WeatherState();
        String apiUrl = buildApiUrl(latitude, longitude);
        ApiResponse apiResponse;
        if (testing) {
            apiResponse = new ApiResponse();
            apiResponse.setStatusCode(200);
            apiResponse.setResponseBody(TESTING_RESPONSE_BODY);
        } else {
            apiResponse = API_CLIENT.getWeatherData(apiUrl);
        }


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

    private String buildApiUrl(double latitude, double longitude) {
        return "https://api.weather.yandex.ru/v2/informers?" +
                "lat=" + latitude +
                "&lon=" + longitude +
                "&lang=by-BY";
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
