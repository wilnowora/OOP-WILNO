import javafx.application.Application;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;

public class App extends Application {
    TableView<ManUn> tableView = new TableView<ManUn>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("UAS OOP");
        TableColumn<ManUn, String> columnNama = new TableColumn<>("NAMA");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));

        TableColumn<ManUn, Integer> columnNomorPunggung = new TableColumn<>("NOMORPUNGGUNG");
        columnNomorPunggung.setCellValueFactory(new PropertyValueFactory<>("NomorPunggung"));

        TableColumn<ManUn, String> columnAsal = new TableColumn<>("ASAL");
        columnAsal.setCellValueFactory(new PropertyValueFactory<>("Asal"));

        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnNomorPunggung);
        tableView.getColumns().add(columnAsal);

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Update");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());

        Button button2 = new Button("Delete");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());

        Button button3 = new Button("Edit");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());

        Button button4 = new Button("Refresh");
        toolBar.getItems().add(button4);
        button4.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from manchesterunited");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new ManUn(rs.getString("Nama"), rs.getInt("NomorPunggung"), rs.getString("Asal")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("simpan");

        addStage.setTitle("add data Menu");

        TextField namaField = new TextField();
        TextField nomorpunggungField = new TextField();
        TextField asalField = new TextField();
        Label labelNama = new Label("Nama");
        Label labelNomorPunggung = new Label("NomorPunggung");
        Label labelAsal = new Label("Asal");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelNomorPunggung, nomorpunggungField);
        VBox hbox3 = new VBox(5, labelAsal, asalField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into manchesterunited SET nama='%s', nomorpunggung='%s', asal='%s'";
                sql = String.format(sql, namaField.getText(), nomorpunggungField.getText(), asalField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField noField = new TextField();
        Label labelNo = new Label("Nama");

        VBox hbox1 = new VBox(5, labelNo, noField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from manchesterunited WHERE nama='%s'";
                sql = String.format(sql, noField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Update");

        TextField namaField = new TextField();
        TextField nomorpunggungField = new TextField();
        TextField asalField = new TextField();
        Label labelNama = new Label("Nama");
        Label labelNomorPunggung = new Label("NomorPunggung");
        Label labelAsal = new Label("Asal");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelNomorPunggung, nomorpunggungField);
        VBox hbox3 = new VBox(5, labelAsal, asalField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE manchesterunited SET NomorPunggung ='%s', Asal ='%s' WHERE Nama='%s'";
                sql = String.format(sql, nomorpunggungField.getText(), asalField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();

            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from manchesterunited");
            while (rs.next()) {
                tableView.getItems().addAll(new ManUn(rs.getString("Nama"), rs.getInt("NomorPunggung"), rs.getString("Asal")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE menu DROP id";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE menu ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}
