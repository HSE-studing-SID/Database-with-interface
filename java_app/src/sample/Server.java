package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Server {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/";
        String user = "postgres" ;
        String pass = "12341234" ;
        return DriverManager.getConnection(url, user, pass);
    }

    static void listDownAllDatabases(Connection connection) {
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
}
