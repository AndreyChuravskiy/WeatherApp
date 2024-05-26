package weather;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.api.WeatherUpdater;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherApp extends Application {
    private static final boolean TESTING = false;
    private static final String[] MOON_NAMES = {"Полнолуние", "Убывающая луна", "Убывающая луна", "Убывающая луна",
            "Последняя четверть", "Убывающая луна", "Убывающая луна", "Убывающая луна", "Новолуние", "Растущая луна",
            "Растущая луна", "Растущая луна", "Первая четверть", "Растущая луна", "Растущая луна", "Растущая луна"};

    private static final Map<String, String> PERIOD_NAMES = Map.of("night", "Ночь",
            "morning", "Утро",
            "day", "День",
            "evening", "Вечер");
    private static final Map<String, String> CONDITION_NAMES;

    static {
        CONDITION_NAMES = new HashMap<>();
        CONDITION_NAMES.put("clear", "Ясно");
        CONDITION_NAMES.put("partly-cloudy", "Малооблачно");
        CONDITION_NAMES.put("cloudy", "Облачно с прояснениями");
        CONDITION_NAMES.put("overcast", "Пасмурно");
        CONDITION_NAMES.put("light-rain", "Небольшой дождь");
        CONDITION_NAMES.put("rain", "Дождь");
        CONDITION_NAMES.put("heavy-rain", "Сильный дождь");
        CONDITION_NAMES.put("showers", "Ливень");
        CONDITION_NAMES.put("wet-snow", "Дождь со снегом");
        CONDITION_NAMES.put("light-snow", "Небольшой снег");
        CONDITION_NAMES.put("snow", "Снег");
        CONDITION_NAMES.put("snow-showers", "Снегопад");
        CONDITION_NAMES.put("hail", "Град");
        CONDITION_NAMES.put("thunderstorm", "Гроза");
        CONDITION_NAMES.put("thunderstorm-with-rain", "Дождь с грозой");
        CONDITION_NAMES.put("thunderstorm-with-hail", "Гроза с градом");
    }
    
    private String apiKey;
    private CityInfo city = CityList.CITIES.get(39);
    private WeatherUpdater updater;
    private VBox mainBox;
    private VBox cityBox;
    private Label updateTimeLabel;
    private Label cityLabel;
    private Label latLabel;
    private Label lonLabel;
    private Label daytimeLabel;
    private Label tempIntervalLabel;
    private ImageView conditionImageView;
    private Label conditionLabel;
    private Label factTempLabel;
    private Label feelsLikeTempLabel;
    private Label windSpeedLabel;
    private Label windGustLabel;
    private Label pressureLabel;
    private Label humidityLabel;
    private ImageView windDirImageView;
    private ImageView sunPosImageView;
    private ImageView moonStateImageView;
    private Label sunriseLabel;
    private Label sunsetLabel;
    private Label moonStateLabel;
    private List<Label> nextPeriodLabel;
    private List<ImageView> nextConditionsImageView;
    private List<Label> nextTempLabel;

    @Override
    public void start(Stage stage) {
        initialize();
        buildMainBox();
        buildCityBox();
        HBox core = new HBox();
        core.getChildren().addAll(mainBox, cityBox);
        updateEvent();
        stage.setScene(new Scene(core, 600, 600));
        stage.show();
    }

    private void initialize() {
        Config config = new Config();
        apiKey = config.getApiKey();
    }

    private void buildMainBox() {
        mainBox = new VBox();

        HBox topBox = new HBox();
        buildTopBox(topBox);

        VBox temperatureBox = new VBox();
        buildTemperatureBox(temperatureBox);

        HBox generalInfoBox = new HBox();
        buildGeneralInfoBox(generalInfoBox);

        HBox moonAndSunBox = new HBox();
        buildMoonAndSunBox(moonAndSunBox);

        VBox periodsBox = new VBox();
        buildPeriodsBox(periodsBox);

        mainBox.getChildren().addAll(topBox, temperatureBox, generalInfoBox, moonAndSunBox, periodsBox);
    }

    private void buildCityBox() {
        cityBox = new VBox();
        ObservableList<CityInfo> cityList = FXCollections.observableArrayList(CityList.CITIES);


        ComboBox<CityInfo> cityComboBox = new ComboBox<>(cityList);
        cityComboBox.setPromptText("Введите название города");

        cityComboBox.setOnAction(event -> {
            CityInfo selectedCity = cityComboBox.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                city = selectedCity;
                updateEvent();
            }
        });


        cityBox.getChildren().addAll(cityComboBox);
    }


    private void buildPeriodsBox(VBox periodsBox) {
        Label textLabel = new Label();
        HBox buttonsBox = new HBox();

        nextConditionsImageView = new ArrayList<>();
        nextPeriodLabel = new ArrayList<>();
        nextTempLabel = new ArrayList<>();

        Button firstButton = new Button();
        HBox firstButtonBox = new HBox();
        nextPeriodLabel.add(new Label());
        nextConditionsImageView.add(new ImageView());
        nextTempLabel.add(new Label());
        firstButtonBox.getChildren().addAll(nextPeriodLabel.get(0),
                nextConditionsImageView.get(0), nextTempLabel.get(0));
        firstButton.setGraphic(firstButtonBox);
        firstButton.setOnAction(event -> updatePeriodEvent(0));

        Button secondButton = new Button();
        HBox secondButtonBox = new HBox();
        nextPeriodLabel.add(new Label());
        nextConditionsImageView.add(new ImageView());
        nextTempLabel.add(new Label());
        secondButtonBox.getChildren().addAll(nextPeriodLabel.get(1),
                nextConditionsImageView.get(1), nextTempLabel.get(1));
        secondButton.setGraphic(secondButtonBox);
        secondButton.setOnAction(event -> updatePeriodEvent(1));

        buttonsBox.getChildren().addAll(firstButton, secondButton);

        periodsBox.getChildren().addAll(textLabel, buttonsBox);
    }

    private void buildMoonAndSunBox(HBox moonAndSunBox) {
        VBox sunBox = new VBox();
        sunPosImageView = new ImageView();
        HBox riseSetBox = new HBox();

        VBox textBox = new VBox();
        Label riseLabel = new Label("Восход");
        Label setLabel = new Label("Заход");
        textBox.getChildren().addAll(riseLabel, setLabel);

        VBox infoBox = new VBox();
        sunriseLabel = new Label();
        sunsetLabel = new Label();
        infoBox.getChildren().addAll(sunriseLabel, sunsetLabel);

        riseSetBox.getChildren().addAll(textBox, infoBox);

        sunBox.getChildren().addAll(sunPosImageView, riseSetBox);


        VBox moonBox = new VBox();
        moonStateImageView = new ImageView();
        moonStateLabel = new Label();
        moonBox.getChildren().addAll(moonStateImageView, moonStateLabel);

        moonAndSunBox.getChildren().addAll(sunBox, moonBox);

    }

    private void buildGeneralInfoBox(HBox generalInfoBox) {
        VBox textBox = new VBox(
                new Label("Скорость ветра"),
                new Label("Скорость порывов ветра"),
                new Label("Давление"),
                new Label("Влажность воздуха")
        );

        VBox infoBox = new VBox();

        windSpeedLabel = new Label();
        windGustLabel = new Label();
        pressureLabel = new Label();
        humidityLabel = new Label();

        infoBox.getChildren().addAll(windSpeedLabel, windGustLabel, pressureLabel, humidityLabel);

        windDirImageView = new ImageView();

        generalInfoBox.getChildren().addAll(textBox, infoBox, windDirImageView);
    }

    private void buildTemperatureBox(VBox temperatureBox) {
        VBox daytimeBox = new VBox();
        daytimeLabel = new Label();
        tempIntervalLabel = new Label();
        daytimeBox.getChildren().addAll(daytimeLabel, tempIntervalLabel);


        HBox conditionBox = new HBox();
        conditionImageView = new ImageView();
        conditionLabel = new Label();
        conditionBox.getChildren().addAll(conditionImageView, conditionLabel);

        VBox factTempBox = new VBox();
        factTempLabel = new Label();
        feelsLikeTempLabel = new Label();
        factTempBox.getChildren().addAll(factTempLabel, feelsLikeTempLabel);

        temperatureBox.getChildren().addAll(daytimeBox, conditionBox, factTempBox);
    }

    private void buildTopBox(HBox topBox) {
        Button updateButton = new Button();
        updateButton.setOnAction(event -> updateEvent());

        VBox userInfoBox = new VBox();
        buildUserInfoBox(userInfoBox);

        topBox.getChildren().addAll(updateButton, userInfoBox);
    }

    private void updateEvent() {
        updater = new WeatherUpdater(apiKey, city.getLatitude(), city.getLongitude());
        WeatherState state = updater.fetchWeatherData(TESTING);
        updateTimeLabel.setText("Обновлено: " + transformTime(state.getNow()));
        cityLabel.setText(city.getCityName());
        latLabel.setText("Широта   " + state.getInfo().getLat());
        lonLabel.setText("Долгота  " + state.getInfo().getLon());
        daytimeLabel.setText("Сейчас");
        tempIntervalLabel.setText("");
        conditionImageView.setImage(new Image("condition/" + state.getFact().getCondition() + ".png"));
        conditionLabel.setText(CONDITION_NAMES.get(state.getFact().getCondition()));
        factTempLabel.setText(state.getFact().getTemp() + " °С");
        feelsLikeTempLabel.setText("Ощущается как " + state.getFact().getFeels_like() + " °С");
        windSpeedLabel.setText(state.getFact().getWind_speed() + " м/с");
        windGustLabel.setText(state.getFact().getWind_gust() + " м/с");
        pressureLabel.setText(state.getFact().getPressure_mm() + " мм. рт. ст.");
        humidityLabel.setText(state.getFact().getHumidity() + "%");
        windDirImageView.setImage(new Image("wind/wind_" + state.getFact().getWind_dir() + ".png"));
        sunPosImageView.setImage(new Image("sun_graf.png"));
        sunriseLabel.setText(state.getForecast().getSunrise());
        sunsetLabel.setText(state.getForecast().getSunset());
        moonStateImageView.setImage(new Image("moon/moon" + state.getForecast().getMoon_code() + ".png"));
        moonStateLabel.setText(MOON_NAMES[state.getForecast().getMoon_code()]);
        for (int i = 0; i < nextPeriodLabel.size(); i++) {
            String partNameEN = state.getForecast().getParts().get(i).getPart_name();
            String partNameRU = PERIOD_NAMES.get(partNameEN);
            nextPeriodLabel.get(i).setText(partNameRU);
        }
        for (int i = 0; i < nextConditionsImageView.size(); i++) {
            nextConditionsImageView.get(i).setImage(
                    new Image("condition/" + state.getForecast().getParts().get(i).getCondition() + ".png"));
        }
        for (int i = 0; i < nextTempLabel.size(); i++) {
            nextTempLabel.get(i).setText(state.getForecast().getParts().get(i).getTemp_avg() + " °С");
        }
    }

    private void updatePeriodEvent(int num) {
        WeatherState state = updater.fetchWeatherData(TESTING);
        Part part = state.getForecast().getParts().get(num);
        updateTimeLabel.setText("Обновлено: " + transformTime(state.getNow()));
        daytimeLabel.setText(PERIOD_NAMES.get(part.getPart_name()));
        tempIntervalLabel.setText(part.getTemp_min() + " °С - " + part.getTemp_max() + " °С");
        conditionImageView.setImage(new Image("condition/" + part.getCondition() + ".png"));
        conditionLabel.setText(CONDITION_NAMES.get(part.getCondition()));
        factTempLabel.setText(part.getTemp_avg() + " °С");
        feelsLikeTempLabel.setText("Ощущается как " + part.getFeels_like() + " °С");
        windSpeedLabel.setText(part.getWind_speed() + " м/c");
        windGustLabel.setText(part.getWind_gust() + " м/c");
        pressureLabel.setText(part.getPressure_mm() + " мм. рт. ст.");
        humidityLabel.setText(part.getHumidity() + "%");
        windDirImageView.setImage(new Image("wind/wind_" + part.getWind_dir() + ".png"));
    }

    private String transformTime(int unixTime) {
        Instant instant = Instant.ofEpochSecond(unixTime);

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedDateTime = zonedDateTime.format(formatter);

        return formattedDateTime;
    }

    private void buildUserInfoBox(VBox userInfoBox) {
        updateTimeLabel = new Label();
        cityLabel = new Label();
        latLabel = new Label();
        lonLabel = new Label();

        userInfoBox.getChildren().addAll(updateTimeLabel, cityLabel, latLabel, lonLabel);
    }

}
