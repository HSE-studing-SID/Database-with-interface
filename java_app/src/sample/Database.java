package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Database {
    private final String name;
    private final Server server;
    private Connection connection;

    Database(Server server, String name) {
        this.name = name;
        this.server = server;
    }

    public void connect() throws SQLException {
        if (connection == null) {
            this.connection = DriverManager.getConnection(server.getUrl() + name, server.getUsername(), server.getPass());
            System.out.println("Connected to: " + server.getUrl() + name);
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
            System.out.println("Disconnected from: " + server.getUrl() + name);
        }
    }

    public String getName() {
        return name;
    }

    public Connection getConnection() {
        return connection;
    }

    public Server getServer() {
        return server;
    }

    public ObservableList<MenuItem> getTableNames() throws SQLException {
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        ResultSet rs =  connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});

        while (rs.next()) {
            var name = rs.getString(3);
            result.add(new MenuItem(name));
        }

        return result;
    }

    public ResultSet getTable(String name) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM " + name);   // todo change
    }

    public void deleteTable(String tableName) throws SQLException {
        connection.prepareStatement("DROP TABLE " + tableName + ';').execute();
    }

    public void clearTable() {

    }

    public void createCopyTable(String from, String to) throws SQLException {
        try {
            connection.prepareStatement("CREATE TABLE \""+ to +"\" AS SELECT * FROM \"" + from + "\";").execute();
        } catch (SQLException throwables) {
            deleteTable(to);
            createCopyTable(from, to);
        }
    }

    public void addTableRow(String tableName, String[] fields) throws SQLException {
        var t = getTable(tableName);
        String[] arr = new String[t.getMetaData().getColumnCount() - 1];
        for (int i = 1; i < t.getMetaData().getColumnCount(); i++) {
            if (!t.getMetaData().getColumnName(i + 1).equals("ИДЕНТИФИКАТОР")) {
                arr[i - 1] = t.getMetaData().getColumnName(i + 1);
            }
        }

        int j = 0;
        String[] tt = new String[fields.length - 1];
        for (String field : fields) {
            if (field != null) {
                tt[j++] = field;
            }
        }

        String s = String.format("""
                         INSERT INTO %s ("%s") VALUES ('%s');""",
                            tableName,
                            String.join("\", \"", arr),
                            String.join("', '", tt));

        System.out.println(s);
        connection.prepareStatement(s).execute();
    }
}
