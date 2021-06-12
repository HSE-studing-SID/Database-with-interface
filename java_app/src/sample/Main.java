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

    public static void getConnection() {
        String url = "jdbc:postgresql://localhost:5432/TryDriver";
        String user = "postgres" ;
        String pass = "12341234" ;
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            listDownAllDatabases(connection);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        getConnection();
        //launch(args);
    }
}
