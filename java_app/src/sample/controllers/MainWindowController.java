package sample.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> DBsDropList;

    @FXML
    private Button ConnectToDBButton;

    @FXML
    private Button CreateNewDBButton;

    @FXML
    void initialize() {
        /*
         * todo fill DBsDropList
         */

        ConnectToDBButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Database.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        });

        CreateNewDBButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/CreateDB.fxml"));


            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        });
    }

}