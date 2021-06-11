package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class DatabaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar DatabaseMenuBar;

    @FXML
    void initialize() {
        /*
         * todo add title to stage with database name
         */
        var menus = DatabaseMenuBar.getMenus();
        var File = menus.get(0).getItems();
        var Edit = menus.get(1).getItems();

        // find
        File.get(0).setOnAction(actionEvent -> {

        });

        // database settings
        File.get(1).setOnAction(actionEvent -> {

        });

        // create table
        Edit.get(0).setOnAction(actionEvent -> {

        });

        // delete table
        Edit.get(1).setOnAction(actionEvent -> {

        });

        // clear table
        Edit.get(2).setOnAction(actionEvent -> {

        });

        // clear all tables
        Edit.get(3).setOnAction(actionEvent -> {

        });
    }
}
