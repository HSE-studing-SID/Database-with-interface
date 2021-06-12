package sample.controllers;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableRedactorController {

    Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    private TextField TableNameField;

    @FXML
    private Label ErrorLabel;

    @FXML
    void initialize() {

    }
}