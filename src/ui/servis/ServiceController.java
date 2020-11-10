package ui.servis;

import db.DBQuerrys;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServiceController  implements Initializable {
    public Label descriptions,servicNUmber,phone,ownerofprod,nameofprod;
    public Pane pane;
    public TextField searchField;
    @FXML
    private TableView<Service> table;
    @FXML
    private TableColumn price,owner,time,description,telephone,numberOfTicket,status,name;
    DBQuerrys dbQuerrys = new DBQuerrys();
    Service service = new Service();
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
        numberOfTicket.setCellValueFactory(new PropertyValueFactory<>("serivisNumber"));

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
                    if (empty)
                    {
                        setText(null);
                        setStyle(null);
                    }
                }
            };
        });
        table.getItems().addAll(dbQuerrys.tableservis());
    }
@FXML
public void hideTable(){
    if (pane.isVisible()==true){
        pane.setVisible(false);
    }
        else
        {
            pane.setVisible(true);
        }

}
    @FXML
    public void pullingService() throws SQLException {
        int indexOfRow= table.getSelectionModel().getFocusedIndex();
        String d = String.valueOf(table.getColumns().get(0));
       // System.out.println(d);

        System.out.println(indexOfRow);
        //amazing  problem sa ovom gluposti oko editovanja  izgleda

        String valueOfRow = String.valueOf(table.getColumns().get(0).getCellObservableValue(indexOfRow).getValue());
        System.out.println(valueOfRow);
        service = dbQuerrys.searchOfServiceByServiceNumber(valueOfRow).get(0);
      //  price.setText(String.valueOf(service.getPrice()));
        nameofprod.setText(service.getName());
        ownerofprod.setText(service.getOwner());
        descriptions.setText(service.getDescription());
        phone.setText(service.getTelephone());
     //   time.setText(service.getTime());
        servicNUmber.setText(String.valueOf(service.getSerivisNumber()));
        System.out.println(servicNUmber);
      //  status.setText(service.getStatusInt());

    }
    @FXML
    public void searchOfService(){
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