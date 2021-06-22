package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.javatuples.*;
import sample.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableRedactorController {
    public DatabaseController databaseController;
    private String originalTableName;
    public String tempTableName;

    @FXML
    private TableView<Tuple> tableView;

    @FXML
    private ToolBar toolBar;

    @FXML
    private TextField TableNameField;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button deleteTableButton;

    @FXML
    private Button addRowButton;

    @FXML
    private Button saveChangesButton;

    static public Tuple createTuple(String[] arr) {
        return switch (arr.length) {
            case 1 -> new Unit<>(arr[0]);
            case 2 -> new Pair<>(arr[0], arr[1]);
            case 3 -> new Triplet<>(arr[0], arr[1], arr[2]);
            case 4 -> new Quartet<>(arr[0], arr[1], arr[2], arr[3]);
            case 5 -> new Quintet<>(arr[0], arr[1], arr[2], arr[3], arr[4]);
            case 6 -> new Sextet<>(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
            case 7 -> new Septet<>(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
            case 8 -> new Octet<>(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7]);
            case 9 -> new Ennead<>(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]);
            case 10 -> new Decade<>(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
            default -> throw new IllegalStateException("Unexpected value: " + arr.length);
        };
    }

    private void buildData() throws SQLException {
        ObservableList<Tuple> data = FXCollections.observableArrayList();
        ResultSet rs = databaseController.database.getTable(tempTableName);
        int colCount = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            var arr = new String[colCount];
            for (int j = 0; j < colCount; j++) {
                arr[j] = rs.getString(j + 1);
            }
            data.add(createTuple(arr));
        }
        tableView.setItems(data);
    }

    public void init(DatabaseController db, String tableName) {
        databaseController = db;
        originalTableName = tableName;
        tempTableName = tableName + "_TEMP";
        TableNameField.setText(tableName);
        tableView.setEditable(true);

        try {
            databaseController.database.connect();
            databaseController.database.createCopyTable(originalTableName, tempTableName);
            ResultSet rs = databaseController.database.getTable(tempTableName);
            rs.next();

            int colCount = rs.getMetaData().getColumnCount();
            for (int i = 0; i < colCount; i++) {
                var colName = rs.getMetaData().getColumnName(i + 1);

                var column = new TableColumn<Tuple, String>(colName);
                    column.setPrefWidth(tableView.getPrefWidth() / colCount);
                    column.setCellValueFactory(new PropertyValueFactory<>("value" + i));

                var textField = new TextField();
                    textField.setPrefWidth((toolBar.getPrefWidth() / (colCount - 1)) - 6);

                if (!colName.equals("ИДЕНТИФИКАТОР")) {
                    toolBar.getItems().add(textField);
                }
                tableView.getColumns().add(column);
            }

            buildData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        deleteTableButton.setOnAction(actionEvent -> {
            var stage = new Stage();
            Utils.createConfirmationWindow(
                stage,
                new Label("Вы действительно хотите удалить эту таблицу?\nЭто действие нельзя отменить"),
                cancelEvent -> stage.hide(),
                confirmEvent -> {
                    try {
                        databaseController.database.deleteTable(originalTableName);
                        databaseController.database.deleteTable(tempTableName);
                        databaseController.refreshTables();
                        stage.hide();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            );
        });

        addRowButton.setOnAction(actionEvent -> {
            String[] fields = new String[toolBar.getItems().size() + 1];

            int i = 1;
            for (Node node : toolBar.getItems()) {
                TextField tf = ((TextField) node);
                fields[i] = (tf.getText());
                tf.clear();
                i++;
            }

            try {
                databaseController.database.addTableRow(tempTableName, fields);
                buildData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        saveChangesButton.setOnAction(actionEvent -> { // todo test this
            try {
                String newName = TableNameField.getText();

                if (!newName.equals(originalTableName)) {
                    var arrList = new ArrayList<String>();
                    for (var item : databaseController.database.getTableNames()) {
                        arrList.add(item.getText());
                    }
                    if (arrList.contains(newName)) {
                        ErrorLabel.setText("Таблица с таким именем уже существует!");
                        throw new SQLException();
                    }
                }
                if (newName.isEmpty()) {
                    ErrorLabel.setText("Введите название");
                    throw new SQLException();
                }

                databaseController.database.deleteTable(originalTableName);
                databaseController.database.createCopyTable(tempTableName, newName);
                databaseController.database.deleteTable(tempTableName);
                databaseController.refreshTables();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}