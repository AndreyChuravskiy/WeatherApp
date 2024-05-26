package weather;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    private double temp;
    private double feels_like;
    private double temp_water;
    private String icon;
    private String condition;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private double pressure_mm;
    private double pressure_pa;
    private double humidity;
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
    private List<Part> parts;
}

@Getter
@Setter
class Part {
    private double temp_water;
    private String part_name;
    private double temp_min;
    private double temp_max;
    private double temp_avg;
    private double feels_like;
    private String icon;
    private String condition;
    private String daytime;
    private Boolean polar;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private double pressure_mm;
    private double pressure_pa;
    private double humidity;
    private double prec_mm;
    private double prec_period;
    private double prec_prob;
}
