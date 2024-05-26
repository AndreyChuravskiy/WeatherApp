package weather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CityInfo {
    private String cityName;
    private double latitude;
    private double longitude;
    private int timeZoneOffset;

    @Override
    public String toString() {
        return cityName;
    }
}
