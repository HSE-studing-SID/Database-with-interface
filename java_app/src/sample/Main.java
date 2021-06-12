package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.postgresql.PGProperty;
import sample.controllers.MainWindowController;

import java.sql.*;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/assets/MainWindow.fxml"));
        loader.load();
        ((MainWindowController)loader.getController()).setConnection(getConnection());

        primaryStage.setTitle("Start screen");
        primaryStage.setScene(new Scene(loader.getRoot(), 1280, 800));
        primaryStage.show();
    }

    private static void listDownAllDatabases(Connection connection) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/TryDriver";
        String user = "postgres";
        String pass = "12341234";
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) {
//        try {
//            getConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        launch(args);
    }
}
