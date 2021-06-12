package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.postgresql.PGProperty;

import java.sql.*;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/assets/MainWindow.fxml"));
        primaryStage.setTitle("Start screen");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }




    public static void main(String[] args) {
       try{
           Server.getConnection();
       }  catch (Exception ex){
           ex.printStackTrace();
       }

        launch(args);
    }
}
