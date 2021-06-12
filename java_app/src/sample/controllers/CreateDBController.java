package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server;

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
    private Label errorLable;

    @FXML
    void initialize() {

        CreateDBButton.setOnAction(actionEvent -> {
            String newDBname = newDBNameField.getText();
            if (!newDBname.isEmpty() /* && !isExist(text) */) {
                CreateDBButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/Database.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    createDB(newDBname, Server.getConnection());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle(newDBname);
                stage.show();
            } else if (newDBname.isEmpty()) {
                errorLable.setText("Поле пустое!");
            } else {
                errorLable.setText("База данных с таким именем уже существует");
            }
        });
    }

    public static void createDB(String newDBname, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("CREATE DATABASE " + newDBname);

    }
}


