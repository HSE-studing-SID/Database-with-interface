package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDBController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newDBNameField;

    @FXML
    private Button CreateDBButton;

    @FXML
    void initialize() {
        String DBName = newDBNameField.getText();

        /*
         * todo check if DBName already exists
         */

        // Change window on button pressed
        CreateDBButton.setOnAction(actionEvent -> {
            CreateDBButton.getScene().getWindow().hide();

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
    }
}