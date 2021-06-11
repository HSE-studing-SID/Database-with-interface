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
        var menus = DatabaseMenuBar.getMenus();

    }
}
