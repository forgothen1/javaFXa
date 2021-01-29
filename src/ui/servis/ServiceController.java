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
import recordInfo.RecordInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServiceController  implements Initializable {
    public TextArea comment;
    @FXML
    private Label descriptions, servicNumber,phone,ownerofprod,nameofprod,priceOfServiceLabel;
    @FXML
    private Pane selectPane,workPane,selectArticklePane,manualAddPane;
    @FXML
    private TextField searchField,priceOfServis,priceOfHands,manualPrice,manualSerialNumber,manualName,manualQuantity;
    @FXML
    private ChoiceBox statusOfServis;
    @FXML
    private Button addToSerivis,manualAddBtn;
    @FXML
    private ToggleButton lookServis;
    @FXML
    private TableView<Articles> articleTableOUT;
    @FXML
    private TableColumn serialNumberOUT,priceArticleOUT,quantityArticleOUT,nameArticleOUT,sumPriceOUT;
    @FXML
    private TableView<Articles> articleTableIN;
    @FXML
    private TableColumn serialNumberIN,priceArticleIN,articleNameIN,quantityArticleIN;
    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn price,owner,time,description,telephone,numberOfTicket,status,name,commentService;

    DBQuerrys dbQuerrys = new DBQuerrys();
    Service service = new Service();
    Articles articles= new Articles();
    RecordInfo recordInfo;
    public ServiceController() {}
    //////////////////////////
    // logic for selectPane

    /**
     * seting servis table for chosing servis
     */
    @FXML
    private void setTable()  {
        serviceTable.getItems().clear();
        lookServis.toFront();
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
                        item = getTableColumn().getTableView().getItems().get(currentIndex).getStatusStg();
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
        commentService.setCellValueFactory(new PropertyValueFactory<>("comment"));
        try {
            serviceTable.getItems().addAll(dbQuerrys.tableservis());
        } catch (SQLException e) {
     recordInfo.forConnection().error("didnt load from db",e);
        }
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
    private void searchOfService() throws SQLException {
        serviceTable.getItems().clear();
        if (searchField.getText().trim().isEmpty()) {
                setTable();
        }
        else {
            serviceTable.getItems().addAll(dbQuerrys.searchOfService(searchField.getText().trim()));
        }
    }
    /////////////////////////////////////
    //  logic for workPane

    /**
     * this is just for change of status  service i choiceBox
     * @throws SQLException mhe
     */
    @FXML
    private void changeOfStatus() throws SQLException {
        //set try method if has someting else  to be disabled
        String serviceNumber = servicNumber.getText();
        Integer status= statusOfServis.getSelectionModel().getSelectedIndex()+1;
        System.out.println("status ide u :"+status+", a servis je : "+serviceNumber);
        dbQuerrys.statusChange(status,serviceNumber);
        setTable();
    }
    /**
     * its just to add a coment to service that is aloved
     */
    @FXML
    private void addCommentServis() {
        try {
            System.out.println(comment.getText().trim());
            dbQuerrys.addComment(Integer.valueOf(servicNumber.getText().trim()),comment.getText().trim());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sending servis data from selectpane to workpane and arange it
     */
    @FXML
    private void pullingService()  {
        serviceTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        //making sure that is avaible for clicking

                        // aloving to be modifyed
                        statusOfServis.setDisable(false);
                        service = serviceTable.getSelectionModel().getSelectedItem();
                        //  price.setText(String.valueOf(service.getPrice()));
                        nameofprod.setText(service.getName());
                        ownerofprod.setText(service.getOwner());
                        descriptions.setText(service.getDescription());
                        phone.setText(service.getTelephone());
                        servicNumber.setText(String.valueOf(service.getSerivisNumber()));
                        comment.setText(service.getComment());
                        System.out.println(servicNumber);
                        String status = service.getStatusStg();

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
                            case  ("naplaceno"):
                                statusOfServis.getSelectionModel().select(3);
                        }
                            fillingTableOfArtikle();
                            getPrice();
                    }
                }
            }
        });
    }

    //////////////////////////////////////////////////////
    // logic for selectArticklePane

    /**
     * just showing panel for manual adding of articles to service just needed for temperery solution
     */
    @FXML
    private void manualaddOfArticleShow(){
        if (!manualAddPane.isVisible()){
            manualAddPane.setVisible(true);
            manualName.clear();
            manualPrice.clear();
            manualQuantity.clear();
            manualSerialNumber.clear();
        }
        else {manualAddPane.setVisible(false);}
    }

    /**
     * adding article to db to rest of articles and adding article to service
     * @throws SQLException
     */
    @FXML
    private void manualaddOfAricles() throws SQLException {
        dbQuerrys= new DBQuerrys();
        String broj=manualSerialNumber.getText().trim();
        System.out.println(broj);

        try{
            articles= dbQuerrys.searchBySerialNumber(broj).get(0);
        if (!articles.getSerialNumber().isEmpty()) {
            System.out.println("nema tog broja");
        }} catch (Exception e) {

            articles.setName(manualName.getText().trim());
            articles.setSerialNumber(manualSerialNumber.getText().trim());
            articles.setQuantity(Integer.valueOf(manualQuantity.getText().trim()));
            articles.setPrice(Float.valueOf(manualPrice.getText().trim()));
            articles.setIdArticles(0);
            dbQuerrys.addingToArticles(articles);
        }
        dbQuerrys.manualArticleInServisDB(servicNumber.getText().trim(),manualSerialNumber.getText().trim(), Integer.valueOf(manualQuantity.getText().trim()));
        fillingTableOfArtikle();
        getPrice();
    }

    /**
     * calling from db sum of all price with specific servis number
     */
    @FXML
    private void getPrice()  {
        Integer  ne= service.getSerivisNumber();
        Float d= null;
        try {
            d = dbQuerrys.getingVolePrice(ne);
        } catch (SQLException e) {
          recordInfo.forConnection().error("didint load rom DB",e);
        }
        priceOfServis.setText(String.valueOf(d));
        System.out.println("cijena unijeta u polje "+d);
        setPrice();
    }

    /**
     *  setting sum of price of articles and of hands
     */
    @FXML
    private void setPrice()  {
        if (priceOfHands.getText().trim().isEmpty()){
            priceOfHands.setText("0");
        }
        Float priceofHands= Float.valueOf(priceOfHands.getText())+Float.valueOf(priceOfServis.getText());
        priceOfServiceLabel.setText(String.valueOf(priceofHands));
        try {
            dbQuerrys.setVolePrice(service.getSerivisNumber(),priceofHands);
        } catch (SQLException e) {
            recordInfo.forConnection().error("didnt load from db ",e);
        }
    }
    /**
     * transfering articles from articles to service articles and  informing db
     */
    @FXML
    private void addArticleToServis()  {
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
                            String brojServisa= servicNumber.getText();
                            dbQuerrys.mergingArticleService(brojServisa, valueOfRow);
                            fillingTableOfArtikle();
                            getPrice();
                            setPrice();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        fillingInsideArticleTable();
                    }
                }
            }
        });
    }

    /**
     * deleting articles from service
     */
    @FXML
    private void  deleteArticleFromServis()
    {
        articleTableOUT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            System.out.println("sta sad ovdje  " + service.getSerivisNumber());
                            String articleNumber = articleTableOUT.getItems().get(articleTableOUT.getSelectionModel().getFocusedIndex()).getSerialNumber();
                            Integer articleQuantity = articleTableOUT.getItems().get(articleTableOUT.getSelectionModel().getFocusedIndex()).getQuantity();
                            System.out.println(articleNumber);
                            dbQuerrys.removeArticleFromServis(service.getSerivisNumber(), articleNumber, articleQuantity);
                            fillingTableOfArtikle();
                            getPrice();
                            setPrice();
                            if (selectArticklePane.isVisible()) {
                                fillingInsideArticleTable();
                                getPrice();
                                setPrice();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * showing article table and filling its row from db
     */
    @FXML
    private void fillingInsideArticleTable(){
        articleTableIN.getItems().clear();
        selectArticklePane.setVisible(true);
        selectArticklePane.toFront();
        serialNumberIN.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        articleNameIN.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityArticleIN.setCellValueFactory(new PropertyValueFactory<>("kolicinaUkupno"));
        priceArticleIN.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            articleTableIN.getItems().addAll(dbQuerrys.searchArticles("","(1,0)"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  just hiding and showing table
     */
    @FXML
    private void openTableArtikle() {
        //add to db and to table probably above method will do this all
        if (selectArticklePane.isVisible())
        {
            articleTableIN.getItems().clear();
            selectArticklePane.toBack();
            selectArticklePane.setVisible(false);
        }
        else
        {
            fillingInsideArticleTable();
        }
    }

    /**
     * filling  table where it shows what articles are included in service
     */
    @FXML
    private void fillingTableOfArtikle() {
        articleTableOUT.getItems().clear();
        Integer servis_number = Integer.valueOf(servicNumber.getText());
        serialNumberOUT.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameArticleOUT.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityArticleOUT.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceArticleOUT.setCellValueFactory(new PropertyValueFactory<>("price"));
        sumPriceOUT.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));
        try {
            articleTableOUT.getItems().addAll(dbQuerrys.listOfArticleInServis(servis_number));
        } catch (SQLException e) {
            recordInfo.forConnection().error("didnt load from artikli in service db table",e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setTable();
    }
}