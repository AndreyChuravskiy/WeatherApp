package weather.api;

import lombok.Data;
@Data
public class ApiResponse {
    private int statusCode;
    private String responseBody;
}