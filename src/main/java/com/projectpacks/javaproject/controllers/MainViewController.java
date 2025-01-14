package com.projectpacks.javaproject.controllers;

import com.projectpacks.backend.services.FileService;
import com.projectpacks.javaproject.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
public class MainViewController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField inp;
    @FXML
    private GridPane gridPane;
    private String[] cities;
    @FXML Label message;
    @FXML VBox tableBox;

    @FXML
    public void initialize() {
        Image backgroundImage = new Image("atmo.gif");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        cities = FileService.ReadFile();
    }

    public void send() throws IOException {
        if (inp.getText().trim().equals("")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Error");
            a.setHeaderText("Please Fill in the input field");
            a.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(App.class.getResource("city-view.fxml"));
        Parent root = loader.load();
        CityViewController scene2Controller = loader.getController();
        String textValue = inp.getText();
        boolean result = scene2Controller.setLabelText(textValue);
        if (result) {
            Stage stage = (Stage) inp.getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            inp.setText("");
        }
    }

    public void ipSend() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("city-view.fxml"));
        Parent root = loader.load();


        CityViewController scene2Controller = loader.getController();
        String textValue = inp.getText();
        scene2Controller.SetLabelToCity();
        Stage stage = (Stage) inp.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void resetInput() {
        inp.setText("");
    }

    public void generateGrid() {
        if (tableBox.isVisible()) {
            tableBox.setVisible(false);
            gridPane = new GridPane();
            gridPane.maxHeight(0);
            gridPane.maxWidth(0);
            gridPane.setHgap(0);
            gridPane.setVgap(0);
            return;
        }
        tableBox.setVisible(true);
        if (cities.length > 0) {
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.maxHeight(150);
            gridPane.minHeight(100);

            String[] headings = {"City Name", "Search","Delete"};
            for (int i = 0; i < headings.length; i++) {
                Label l = new Label(headings[i]);
                l.setStyle("-fx-text-fill: white; fx-font-weight: bold;");
                gridPane.add(l, i, 0);
            }


            populateGrid(cities);
        } else {
            message.setText("No cities bookmarked");
        }
    }

    private void populateGrid(String[] cities) {
        for (int i = 0; i < cities.length; i++) {
            addCityRow(cities[i], i + 1);
        }
    }

    private void addCityRow(String cityName, int rowIndex) {

        Label cityLabel = new Label(cityName);
        cityLabel.setStyle("-fx-text-fill: white; fx-font-weight: bold;");


        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            inp.setText(cityName);
            try {
                send();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {

            gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowIndex);
            System.out.println(rowIndex + cities[rowIndex - 1]);
            for (int i = rowIndex + 1; i <= gridPane.getRowCount(); i++) {
                for (var node : gridPane.getChildren()) {
                    Integer currentRow = GridPane.getRowIndex(node);
                    if (currentRow != null && currentRow == i) {
                        GridPane.setRowIndex(node, i - 1);
                    }
                }
            }
            List<String> arr_cities = new ArrayList<>();
            arr_cities.addAll(Arrays.asList(cities));
            arr_cities.remove(rowIndex - 1);
            cities = new String[arr_cities.size()];
            for (int i = 0; i < arr_cities.size(); i++) {
                cities[i] = arr_cities.get(i);
            }
            FileService.UpdateFile(String.join("\n", arr_cities));
        });

        gridPane.add(cityLabel, 0, rowIndex);
        gridPane.add(searchButton, 1, rowIndex);
        gridPane.add(deleteButton, 2, rowIndex);
    }

    public void openMapWindow(ActionEvent actionEvent) throws IOException {
        MapController controller = new MapController();
        controller.start(new Stage());
    }
}