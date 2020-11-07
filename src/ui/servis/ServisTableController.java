package ui.servis;

import db.DBQuerrys;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import recordInfo.RecordInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServisTableController implements Initializable {

    public TextField searchField;
    @FXML
    private TableColumn price,name,time,description,owner,telephone,numberOfTicket,status;
    @FXML
    private TableView<Service> table;
    Service service= new Service();
    DBQuerrys dbQuerrys = new DBQuerrys();

    private RecordInfo info = new RecordInfo();

    @FXML
    public void setTable() throws SQLException {
         name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        numberOfTicket.setCellFactory(column -> {
            return new TableCell<Service, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty ) {
                        setStyle("-fx-alignment: CENTER-RIGHT;");
                        int currentIndex = indexProperty()
                                .getValue() < 0 ? 0
                                : indexProperty().getValue();
                        item = getTableColumn().getTableView().getItems().get(currentIndex).getSerivisNumber();
                        setText(String.valueOf(item));

                    }
                }
            };
        });
        //  status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(column -> {
            return new TableCell<Service, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setStyle("-fx-alignment: CENTER-LEFT;");
                        int currentIndex = indexProperty()
                                .getValue() < 0 ? 0
                                : indexProperty().getValue();
                        item = getTableColumn().getTableView().getItems().get(currentIndex).getStatusInt();
                        if (item.equals("prijem")) {
                            setTextFill(Color.BLUE);
                        } else if (item.equals("obrada")){
                            setTextFill(Color.GREEN);
                        }
                        else if (item.equals("zavrseno"))
                        {
                            setTextFill(Color.RED);
                            setStyle("-fx-font-family:System bold;");
                        }
                        setText(item);
                    }
                }
            };
        });
        table.getItems().addAll(dbQuerrys.tableservis());
    }
    @FXML
    public void openAddingWindow()
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../servis/AddingWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("new Serivis");
            stage.setScene(new Scene(root));
            stage.show();
            /*takes action when u close extra window and it refresh  table  with .clear and calling metode to write to table
             * */
            stage.setOnCloseRequest(windowEvent -> {
                table.getItems().clear();

                System.out.println("proda dali se zatvorilo");
            });
        } catch (IOException e) {
        info.imputOfStuff().error(e);
        }
        System.out.println("Button works fine");
    }
    @FXML
    public void searchoFservice() {
        table.getItems().clear();
        if (searchField.getText().trim().isEmpty()) {
            try {
                setTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
        {
            try {
                table.getItems().addAll(dbQuerrys.searchOfService(searchField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            setTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
