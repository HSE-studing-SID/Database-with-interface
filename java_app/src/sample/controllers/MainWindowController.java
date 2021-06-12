package sample.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> DBsDropList;

    @FXML
    private Button ConnectToDBButton;

    @FXML
    private Button CreateNewDBButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    void initialize() throws SQLException {
        // DBsDropList.setItems()
        /*
         * todo fill DBsDropList
         */

        ConnectToDBButton.setOnAction(actionEvent -> {
            if (!DBsDropList.getSelectionModel().isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/Database.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.show();
            } else {
                ErrorLabel.setText("Укажите базу данных или создайте новую");
            }
        });

        CreateNewDBButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/CreateDB.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.setTitle("Создать базу данных");
            stage.show();
        });
    }
}