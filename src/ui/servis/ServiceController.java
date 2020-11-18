package ui.servis;

import db.DBQuerrys;
import entites.Articles;
import entites.Service;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServiceController  implements Initializable {
    public Label descriptions, servicNumber,phone,ownerofprod,nameofprod;
    public Pane selectPane,workPane,selectArticklePane;
    public TextField searchField,priceOfServis;
    public ChoiceBox statusOfServis;
    public Button addToSerivis;
    public ToggleButton lookServis;
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

    /**
     * seting servis table for chosing servis
     * @throws SQLException
     */
    @FXML
    private void setTable() throws SQLException {
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

    /**
     * for button to show or hide whole pane with table and searchbox
     */
    @FXML
    private void hideTable(){
        if (selectPane.isVisible()){
            selectPane.setVisible(false);
        }
        else
        {
            selectPane.setVisible(true);
        }

    }

    /**
     * search of service in table
     */
    @FXML
    private void searchOfService(){
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
        String serviceNumber = servicNumber.getText();
     Integer status= statusOfServis.getSelectionModel().getSelectedIndex()+1;
     System.out.println("status ide u :"+status+", a servis je : "+serviceNumber);
     dbQuerrys.statusChange(status,serviceNumber);

        setTable();
    }

    /**
     * sending servis data from selectpane to workpane and arange it
     */
    @FXML
    public void pullingService()  {
         serviceTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                //making sure that is avaible for clicking
                lookServis.toFront();
                // aloving to be modifyed
                statusOfServis.setDisable(false);
                service = serviceTable.getSelectionModel().getSelectedItem();
                //  price.setText(String.valueOf(service.getPrice()));
                nameofprod.setText(service.getName());
                ownerofprod.setText(service.getOwner());
                descriptions.setText(service.getDescription());
                phone.setText(service.getTelephone());
                servicNumber.setText(String.valueOf(service.getSerivisNumber()));
                System.out.println(servicNumber);
                String status = service.getStatusInt();

                switch (status) {
                    case ("prijem"):
                        statusOfServis.getSelectionModel().select(0);
                        break;
                    case ("obrada"):
                        statusOfServis.getSelectionModel().select(1);
                        break;
                    case ("zavrseno"):
                        statusOfServis.getSelectionModel().select(2);
                        break;
                }
                    }
                }
            }
        });
    }
    //////////////////////////////////////////////////////
    // logic for selectArticklePane
    @FXML
    public void addArticleToServis()  {
        articleTableIN.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        int indexOfRow = articleTableIN.getSelectionModel().getFocusedIndex();
                        // what to do  to call for db or not to call db but i need to send data to db  altho i just select it  no need for db to recall it need to implent it to others
                        System.out.println(indexOfRow);
                        String valueOfRow = String.valueOf(articleTableIN.getColumns().get(0).getCellObservableValue(indexOfRow).getValue());
                        System.out.println("kako idee "+valueOfRow);
                        try { //neka glupost smisliti kako poslati sve u bazu hmm mozda da se pokrenu 2 metode
                                // clasas query
                            Integer brojServisa= Integer.valueOf(servicNumber.getText());
                            articles = dbQuerrys.searchBySerialNumber(valueOfRow).get(0);
                                dbQuerrys.mergingArticleService(brojServisa,valueOfRow);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            });

    }
    @FXML
    public void openTableArtikle() throws SQLException {
        //add to db and to table probably above method will do this all

        if (selectArticklePane.isVisible()){
            articleTableIN.getItems().clear();
            selectArticklePane.toBack();

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