package com.projectpacks.javaproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MapCoordinatesApp extends Application {

    // Replace with the dimensions of your image
    private static final double IMAGE_WIDTH = 1320; // Example width
    private static final double IMAGE_HEIGHT = 837; // Example height

    // World map boundaries
    private static final double MIN_LATITUDE = -120;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LONGITUDE = 180;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Load the map image
        Image mapImage = new Image("https://www.mapsinternational.com/pub/media/catalog/product/x/s/a/satellite-map-of-the-world_wm00875.jpg");
        ImageView mapView = new ImageView(mapImage);

        // Resize the ImageView to fit the window
        mapView.setFitWidth(IMAGE_WIDTH);
        mapView.setFitHeight(IMAGE_HEIGHT);
        mapView.setPreserveRatio(true);

        // Create a Pane to hold the map and points
        Pane mapPane = new Pane(mapView);

        // Example coordinates to plot on the map
        double[][] coordinates = {
                {51.5074, -0.1278}, // London
                {40.7128, -74.0060}, // New York
                {35.6895, 139.6917}, // Tokyo
                {-33.8688, 151.2093}, // Sydney
                {48.807880, 2.308952},
        };

        // Plot each coordinate as a point on the map
        for (double[] coordinate : coordinates) {
            double latitude = coordinate[0];
            double longitude = coordinate[1];

            Circle point = createPoint(latitude, longitude);
            point.setOnMouseClicked(event -> showCityTemperaturePopup("Murzyn"));
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

    private void showCityTemperaturePopup(String cityName) {
        // Create an alert to show the city name and temperature
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("City Information");
        alert.setHeaderText(null);
        alert.setContentText(cityName + "\nTemperature: 25°C");

        // Show the alert
        alert.showAndWait();
    }
}
