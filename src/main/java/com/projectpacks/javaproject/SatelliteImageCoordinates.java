package com.projectpacks.javaproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SatelliteImageCoordinates extends Application {

    // Original lower-left corner coordinates in degrees
    private static final double ORIGIN_LAT = 31.372889; // 31°22'22.4"N
    private static final double ORIGIN_LON = -7.358250; // 7°21'29.7"W

    // Image dimensions (in pixels)
    private static final int IMAGE_WIDTH = 764;
    private static final int IMAGE_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        // Load the satellite image
        Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Europe_satellite_orthographic.jpg/764px-Europe_satellite_orthographic.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageView.setFitHeight(IMAGE_HEIGHT);

        // Create a pane to hold the image and the shapes
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Test the function by creating a box at specific coordinates
        createBox(pane, 35.0, -5.0); // Example: 35°N, 5°W
        createBox(pane, 52.215162, 20.974430);
        createBox(pane, 43.880544, 0.296768);

        // Set up the scene and stage
        Scene scene = new Scene(pane, IMAGE_WIDTH, IMAGE_HEIGHT);
        primaryStage.setTitle("Satellite Image Coordinates");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a box at the given geographic coordinates.
     * @param pane The pane to draw the box on.
     * @param lat Latitude of the desired location.
     * @param lon Longitude of the desired location.
     */
    private void createBox(Pane pane, double lat, double lon) {
        // Define bounding box
        double minLat = 31.23; // Southern limit
        double maxLat = 68.7;  // Northern limit
        double minLon = -7.20; // Western limit
        double maxLon = 60.0;  // Eastern limit

        // Check if point is within bounds
        if (lat < minLat || lat > maxLat || lon < minLon || lon > maxLon) {
            return; // Do not plot points outside the bounds
        }

        // Map latitude and longitude to image coordinates
        double latSpan = maxLat - minLat; // Latitude range
        double lonSpan = maxLon - minLon; // Longitude range

        double adjustedX = ((lon - minLon) / lonSpan) * IMAGE_WIDTH;
        double adjustedY = ((maxLat - lat + 5) / latSpan) * IMAGE_HEIGHT;

        // Create a rectangle at the adjusted coordinates
        Rectangle rectangle = new Rectangle(adjustedX, adjustedY, 10, 10);
        rectangle.setFill(Color.RED);

        // Add the rectangle to the pane
        pane.getChildren().add(rectangle);
    }






    public static void main(String[] args) {
        launch(args);
    }
}
