package com.projectpacks.javaproject;

import com.sothawo.mapjfx.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MapController {
    private final Coordinate warsaw = new Coordinate(52.2297, 21.0122);
    private final Coordinate paris = new Coordinate(48.8566, 2.3522);
    private static final Coordinate coordKarlsruheCastle = new Coordinate(49.013517, 8.404435);
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);
    private static final Coordinate coordKarlsruheStation = new Coordinate(48.993284, 8.402186);
    private static final Coordinate coordKarlsruheSoccer = new Coordinate(49.020035, 8.412975);
    private static final Coordinate coordKarlsruheUniversity = new Coordinate(49.011809, 8.413639);
    private static final Extent extentAllLocations = Extent.forCoordinates(coordKarlsruheCastle,
            coordKarlsruheHarbour, coordKarlsruheStation, coordKarlsruheSoccer);

    public MapController() {
        show();
    }
    public void show() {
        Stage mapStage = new Stage();
        BorderPane mapPane = new BorderPane();

        // Initialize the MapView
        MapView mapView = new MapView();
        mapView.initialize(Configuration.builder()
                .showZoomControls(true)
                .build());
        mapView.setZoom(5);
        mapView.setCenter(warsaw);

        // Add markers with labels
        //addMarkerWithLabel(mapView, warsaw, "Warsaw", "5°C");
        //addMarkerWithLabel(mapView, paris, "Paris", "10°C");
        //mapView.addMarker();
        mapPane.setCenter(mapView);
        Scene mapScene = new Scene(mapPane, 800, 600);

        mapStage.setTitle("City Map");
        mapStage.setScene(mapScene);

        mapStage.show();

        // Cleanup when the stage is closed
        mapStage.setOnCloseRequest(event -> mapView.close());
    }

    private void addMarkerWithLabel(MapView mapView, Coordinate coordinate, String cityName, String temperature) {
        // Create a Marker
        Marker marker = Marker.createProvided(Marker.Provided.BLUE)
                .setPosition(coordinate)
                .setVisible(true);

        // Add the marker to the map
        mapView.addMarker(marker);

        // Create a MapLabel with specific position and visibility
        MapLabel label = new MapLabel(cityName + " (" + temperature + ")", 10, 20)
                .setPosition(coordinate)
                .setVisible(true);

        // Add the label to the map
        mapView.addLabel(label);
    }
}
