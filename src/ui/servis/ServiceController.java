package ui.servis;

import db.DBQuerrys;
import entites.Articles;
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
    public Pane selectPane,workPane,selectArticklePane;
    public TextField searchField,priceOfServis;
    public ChoiceBox statusOfServis;
    public Button addToSerivis;
    @FXML
    private TableView<Articles> articleTableOUT;
    @FXML
    private TableColumn serialNumberOUT,priceArticleOUT,quantityArticleOUT,nameArticleOUT;
    @FXML
    private TableView<Articles> articleTableIN;
    @FXML
    private TableColumn serialNumberIN,priceArticleIN,articleNameIN,quantityArticleIN;
    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn price,owner,time,description,telephone,numberOfTicket,status,name;
    DBQuerrys dbQuerrys = new DBQuerrys();
    Service service = new Service();
    Articles articles= new Articles();
    public ServiceController() {}
    //////////////////////////
    // logic for selectPane

    @FXML
    public void setTable() throws SQLException {
        serviceTable.getItems().clear();
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
                        switch (item) {
                            case "prijem":
                                setTextFill(Color.BLUE);
                                break;
                            case "obrada":
                                setTextFill(Color.GREEN);
                                break;
                            case "zavrseno":
                                setTextFill(Color.RED);
                                setStyle("-fx-font-family:System bold;");
                                break;
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
        serviceTable.getItems().addAll(dbQuerrys.tableservis());
    }
    @FXML
    public void hideTable(){
        if (selectPane.isVisible()){
            selectPane.setVisible(false);
        }
        else
        {
            selectPane.setVisible(true);
        }

    }
    @FXML
    public void searchOfService(){
        serviceTable.getItems().clear();
        if (searchField.getText().trim().isEmpty()) {
            try {
                setTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
        {
            try {
                serviceTable.getItems().addAll(dbQuerrys.searchOfService(searchField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /////////////////////////////////////
    //  logic for workPane

    @FXML
    public void changeOfStatus() throws SQLException {
   //set try method if has someting else  to be disabled
        String serviceNumber = servicNUmber.getText();
     Integer status= statusOfServis.getSelectionModel().getSelectedIndex()+1;
     System.out.println("status ide u :"+status+", a servis je : "+serviceNumber);
     dbQuerrys.statusChange(status,serviceNumber);

        setTable();
    }
    @FXML
    public void pullingService() throws SQLException {
        statusOfServis.setDisable(false);
        int indexOfRow= serviceTable.getSelectionModel().getFocusedIndex();

        System.out.println(indexOfRow);
        String valueOfRow = String.valueOf(serviceTable.getColumns().get(0).getCellObservableValue(indexOfRow).getValue());
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
        String status=service.getStatusInt();

        switch (status) {
            case("prijem"): statusOfServis.getSelectionModel().select(0);
            break;
            case("obrada"): statusOfServis.getSelectionModel().select(1);
            break;
            case("zavrseno"): statusOfServis.getSelectionModel().select(2);
                break;
        }
    }
    @FXML
    public void addArticleToServis(){
    //what if no

        //just make another fxml that will open table with artikles and just get id row and send it back
      //  someting with factory redoo
    }
    @FXML
    public void openTableArtikle() throws SQLException {
        //add to db and to table probably above method will do this all

        if (selectArticklePane.isVisible()){
            articleTableIN.getItems().clear();
            selectArticklePane.setVisible(false);
        }
        else {

            selectArticklePane.setVisible(true);
            selectArticklePane.toFront();
            serialNumberIN.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
            articleNameIN.setCellValueFactory(new PropertyValueFactory<>("name"));
            quantityArticleIN.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            priceArticleIN.setCellValueFactory(new PropertyValueFactory<>("price"));
            articleTableIN.getItems().addAll(dbQuerrys.gettingAllArtikles());
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