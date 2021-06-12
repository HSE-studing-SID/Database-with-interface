package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DatabaseController {

    Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    private Label label;

    @FXML
    private MenuBar DatabaseMenuBar;

    @FXML
    private Menu DBSettings;

    @FXML
    void initialize() {
        var menus = DatabaseMenuBar.getMenus();
        var File = menus.get(0).getItems();
        var Edit = menus.get(1).getItems();

        // database settings
        var settings = DBSettings.getItems();
        // delete database
        settings.get(0).setOnAction(actionEvent -> {
            Label text = new Label("Вы действительно хотите удалить эту базу данных?\nЭто действие нельзя отменить");
            StackPane.setAlignment(text, Pos.CENTER);

            Button confirm = new Button("Да");
            confirm.setPrefSize(100, 50);

            Button cancel = new Button("Отмена");
            cancel.setPrefSize(100, 50);

            StackPane.setAlignment(cancel, Pos.BOTTOM_RIGHT);
            StackPane.setMargin(cancel, new Insets(15, 15, 15, 15));
            StackPane.setAlignment(confirm, Pos.BOTTOM_LEFT);
            StackPane.setMargin(confirm, new Insets(15, 15, 15, 15));
            StackPane pane = new StackPane(text, confirm, cancel);
            pane.setStyle("-fx-background-color: #FFE4B5");

            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 400, 200));
            stage.setTitle("Подтверждение");
            stage.show();

            cancel.setOnAction(actionEvent1 -> {
                stage.hide();
            });
            confirm.setOnAction(actionEvent1 -> {
                String dbName = ((Stage) label.getScene().getWindow()).getTitle();
                // todo SQL delete database 'dbName'
                stage.hide();
                label.getScene().getWindow().hide();
            });
        });

        // table redactor
        Edit.get(0).setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/TableRedactor.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ((TableRedactorController) loader.getController()).setConnection(connection);

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.setTitle("Редактор таблицы");
            stage.show();
        });

        // clear table
        Edit.get(2).setOnAction(actionEvent -> {

        });

        // clear all tables
        Edit.get(3).setOnAction(actionEvent -> {

        });
    }
}
