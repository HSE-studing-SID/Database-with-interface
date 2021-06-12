package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label errorLable;

    @FXML
    void initialize() {
        /*
        // Connection connection = <your java.sql.Connection>
ResultSet resultSet = connection.getMetaData().getCatalogs();

//iterate each catalog in the ResultSet
while (resultSet.next()) {
  // Get the database name, which is at position 1
  String databaseName = resultSet.getString(1);
}
resultSet.close();
         */
        // Change window on button pressed
        CreateDBButton.setOnAction(actionEvent -> {
            String text = newDBNameField.getText();
            if (!text.isEmpty() /* && !isExist(text) */) {
                CreateDBButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/Database.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Создать базу данных");
                stage.show();
            } else if (text.isEmpty()) {
                errorLable.setText("Поле пустое!");
            } else {
                errorLable.setText("База данных с таким именем уже существует");
            }
        });
    }
}