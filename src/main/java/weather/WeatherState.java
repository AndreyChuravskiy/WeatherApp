package weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WeatherState {
    private int now;
    private String now_dt;
    private Info info;
    private Fact fact;
    private Forecast forecast;
}
@Getter
@Setter
class Info {
    private double lat;
    private double lon;
    private String url;
}

@Getter
@Setter
class Fact {
    private int temp;
    private int feels_like;
    private int temp_water;
    private String icon;
    private String condition;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private int pressure_mm;
    private int pressure_pa;
    private int humidity;
    private String daytime;
    private Boolean polar;
    private String season;
    private int obs_time;
}

@Getter
@Setter
class Forecast {
    private String date;
    private int date_ts;
    private int week;
    private String sunrise;
    private String sunset;
    private int moon_code;
    private String moon_text;
    private List<Parts> parts;
}

@Getter
@Setter
class Parts {
    private String part_name;
    private int temp_min;
    private int temp_max;
    private int temp_avg;
    private int feels_like;
    private String icon;
    private String condition;
    private String daytime;
    private Boolean polar;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private int pressure_mm;
    private int pressure_pa;
    private int humidity;
    private int prec_mm;
    private int prec_period;
    private int prec_prob;
}
