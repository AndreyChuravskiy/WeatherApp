package weather;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import weather.api.WeatherUpdater;


public class WeatherApp extends Application {

    private static final String STYLESHEET_PATH = "/styles.css";
    private static final String HUMIDITY_ICON_PATH = "humidity.png";
    private static final String WEATHER_ICON_BASE_URL = "https://yastatic.net/weather/i/icons/funky/dark/";
    private static final String WIND_ICON_BASE_PATH = "wind_";

    private WeatherUpdater updater;
    private Label infoLabel;
    private Label currentTemperatureLabel;
    private Label feelsLikeTemperatureLabel;
    private WebView imageState;
    private Label textState;

    private Label humidity;
    private Label wind;


    @Override
    public void start(Stage stage) {
        initializeUI(stage);
        initializeUpdater();
        updateEvent();
    }

    private void initializeUI(Stage stage) {
        Scene scene = new Scene(createMainLayout(), 300, 270);
        scene.getStylesheets().add(STYLESHEET_PATH);
        scene.setFill(Color.RED);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.setResizable(false);
        stage.show();
    }

    private VBox createMainLayout() {
        VBox vbox = new VBox();
        vbox.getStyleClass().add("background-v1");

        infoLabel = new Label();
        infoLabel.setLayoutX(50);
        infoLabel.setLayoutX(50);

        currentTemperatureLabel = new Label();
        currentTemperatureLabel.setFont(new Font("Arial",  40));
        currentTemperatureLabel.setPrefHeight(80);

        feelsLikeTemperatureLabel = new Label();
        feelsLikeTemperatureLabel.setFont(new Font("Arial", 10));

        VBox temperatureBox = new VBox();
        temperatureBox.getStyleClass().add("vbox-style");
        temperatureBox.setLayoutX(0);
        temperatureBox.setLayoutY(0);
        temperatureBox.setPrefWidth(100);
        temperatureBox.setPrefHeight(100);
        temperatureBox.setAlignment(Pos.CENTER);
        temperatureBox.getChildren().addAll(currentTemperatureLabel, feelsLikeTemperatureLabel);

        imageState = new WebView();
        imageState.setPrefHeight(80);
        imageState.setPrefWidth(80);

        textState = new Label();
        textState.setPrefHeight(20);
        textState.setFont(new Font("Arial", 10));

        VBox additionalInfoBox = new VBox();
        additionalInfoBox.getStyleClass().add("vbox-style");
        additionalInfoBox.setLayoutX(200);
        additionalInfoBox.setLayoutY(0);
        additionalInfoBox.setPrefWidth(100);
        additionalInfoBox.setPrefHeight(100);
        additionalInfoBox.setAlignment(Pos.CENTER);

        humidity = new Label();
        Image image = new Image(HUMIDITY_ICON_PATH);
        ImageView imageView = new ImageView(image);
        humidity.setGraphic(imageView);
        humidity.setPrefHeight(32);
        humidity.setFont(new Font("Arial", 15));

        wind = new Label();
        wind.setPrefHeight(20);
        wind.setFont(new Font("Arial", 15));

        VBox stateBox = new VBox();
        stateBox.getStyleClass().add("vbox-style");
        stateBox.setLayoutX(100);
        stateBox.setLayoutY(0);
        stateBox.setPrefHeight(100);
        stateBox.setPrefWidth(100);
        stateBox.setAlignment(Pos.CENTER);
        stateBox.getChildren().addAll(imageState, textState);
        additionalInfoBox.getChildren().addAll(humidity, wind);

        Pane weatherPane = new Pane();
        weatherPane.getChildren().addAll(temperatureBox, stateBox, additionalInfoBox);

        Button updateButton = new Button("Update");
        updateButton.setLayoutX(50);
        updateButton.setLayoutY(10);
        updateButton.setPrefWidth(200);
        updateButton.setPrefHeight(40);
        updateButton.setOnAction(value -> {
            updateEvent();
        });

        Pane updatePane = new Pane();
        updatePane.setPrefWidth(300);
        updatePane.setPrefHeight(60);
        updatePane.getChildren().addAll(updateButton);

        vbox.getChildren().addAll(infoLabel, weatherPane, updatePane);
        return vbox;
    }

    private void initializeUpdater() {
        Config config = new Config();
        updater = new WeatherUpdater(config.getApiKey(), "53.9", "27.5667");
    }

    private void showWeather(WeatherState weatherState) {
        infoLabel.setText("Last update: " + weatherState.getNow_dt() + "\n" +
                "Lat: " + weatherState.getInfo().getLat() + "\n" +
                "Lon: " + weatherState.getInfo().getLon());
        currentTemperatureLabel.setText(weatherState.getFact().getTemp() + "°");
        feelsLikeTemperatureLabel.setText("feels like: " + weatherState.getFact().getFeels_like() + "°");
        String imageUrl = buildWeatherIconUrl(weatherState.getFact().getIcon());
        String htmlContent = "<html><head><style>"
                + "img { width: 60px; height: 60px; display: block; margin: auto;}"
                + "</style></head><body>"
                + "<img src='" + imageUrl + "'/>"
                + "</body></html>";
        imageState.getEngine().loadContent(htmlContent);
        textState.setText(weatherState.getFact().getCondition());
        humidity.setText(weatherState.getFact().getHumidity() + "%");
        Image image = new Image(buildWindIconPath(weatherState.getFact().getWind_dir()));
        ImageView imageView = new ImageView(image);
        wind.setGraphic(imageView);
        wind.setText(weatherState.getFact().getWind_speed() + "m/s");
    }

    private void updateEvent() {
        WeatherState weatherState = updater.fetchWeatherData();
        showWeather(weatherState);
    }

    private String buildWeatherIconUrl(String iconCode) {
        return WEATHER_ICON_BASE_URL + iconCode + ".svg";
    }

    private String buildWindIconPath(String windDirection) {
        return WIND_ICON_BASE_PATH + windDirection + ".png";
    }

}
