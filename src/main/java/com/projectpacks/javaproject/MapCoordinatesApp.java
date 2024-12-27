package com.projectpacks.javaproject;

import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.services.FileService;
import com.projectpacks.backend.services.WeatherService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class MapCoordinatesApp extends Application {

    // Replace with the dimensions of your image
    private static final double IMAGE_WIDTH = 1320; // Example width
    private static final double IMAGE_HEIGHT = 837; // Example height

    // World map boundaries
    private static final double MIN_LATITUDE = -120;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LONGITUDE = 180;
    private List<WeatherData> weatherData = new ArrayList<>();




    public void ReadCoordinates() {
        String[] tab = FileService.ReadFile();
        WeatherService weatherService = new WeatherService();
        weatherService.setWeatherStrategy(new CurrentForecastMethod());
        for (String city: tab) {
            weatherData.add(weatherService.getWeatherForecast(city)[0]);
        }
    }

    @Override
    public void start(Stage stage) {
        ReadCoordinates();
        // Load the map image
        Image mapImage = new Image("https://www.mapsinternational.com/pub/media/catalog/product/x/s/a/satellite-map-of-the-world_wm00875.jpg");
        ImageView mapView = new ImageView(mapImage);

        // Resize the ImageView to fit the window
        mapView.setFitWidth(IMAGE_WIDTH);
        mapView.setFitHeight(IMAGE_HEIGHT);
        mapView.setPreserveRatio(true);

        // Create a Pane to hold the map and points
        Pane mapPane = new Pane(mapView);



        // Plot each coordinate as a point on the map
        for (WeatherData w: weatherData) {
            double latitude = w.getCoord().getLat();
            double longitude = w.getCoord().getLon();

            Circle point = createPoint(latitude, longitude);
            point.setOnMouseClicked(event -> showCityTemperaturePopup(w));
            mapPane.getChildren().add(point);
        }

        // Create a scene and display the map
        Scene scene = new Scene(mapPane, IMAGE_WIDTH, IMAGE_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Map Coordinates Viewer");
        stage.show();
    }

    /**
     * Converts geographic coordinates to pixel coordinates and creates a Circle.
     * @param latitude  Latitude of the point.
     * @param longitude Longitude of the point.
     * @return A Circle representing the point on the map.
     */
    private Circle createPoint(double latitude, double longitude) {
        // Convert latitude and longitude to x, y pixel coordinates
        double x = ((longitude - MIN_LONGITUDE) / (MAX_LONGITUDE - MIN_LONGITUDE)) * IMAGE_WIDTH;
        double y = ((MAX_LATITUDE - latitude + 27.5) / (MAX_LATITUDE - MIN_LATITUDE)) * IMAGE_HEIGHT;

        // Create a point as a Circle
        Circle point = new Circle(x, y, 5); // Radius of 5 pixels
        point.setFill(Color.RED);
        point.setStroke(Color.BLACK);

        return point;
    }

    private void showCityTemperaturePopup(WeatherData wD) {
        // Create an alert to show the city name and temperature
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("City Information");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        VBox content = VisualisationSetup.createWeatherVBox(wD, "dd.MM.yyyy");
        Label heading = new Label(wD.getName());
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        content.getChildren().addFirst(heading);
        content.setStyle("-fx-background-color: #FBE2D5");
        DialogPane dialog = alert.getDialogPane();
        dialog.setPrefSize(200,200);
        dialog.setContent(content);
        // Show the alert
        alert.showAndWait();
    }
}
