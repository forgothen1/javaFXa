package ui.servis;

import db.DBQuerrys;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServiceController  implements Initializable {
    @FXML
    private TableView<Service> table;
    @FXML
    private TableColumn price,owner,time,description,telephone,numberOfTicket,status,name;

    public ServiceController() {
    }

    @FXML
    public void setTable() throws SQLException {
        DBQuerrys dbQuerrys = new DBQuerrys();
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
    public void pullingService(){
        Integer valueOfRow = (Integer) table.getColumns().get(1).getCellObservableValue(table.getFocusModel().getFocusedIndex()).getValue();


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