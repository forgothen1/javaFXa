package ui.servis;

import db.DBQuerrys;
import entites.Articles;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import recordInfo.RecordInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    public Label serviceNumb;
    @FXML
    private CheckBox enableBox;
    @FXML
    private ChoiceBox statusCB;
    @FXML
    private TextField priceTF,numbTF,nameProductTF,ownerTF;
    @FXML
    private TableColumn sumPriceTC,priceTC,quantityTC,nameTC,serialNumbTC;
    @FXML
    private TableView tableOfArticle;
    @FXML
    private TextArea description,coment;
    public String definderForSetupOfWindow;
    Service service;
    DBQuerrys dbQuerrys;
    Articles articles;
    RecordInfo recordInfo;

    /**
     * loading stuff to gui
     */
    @FXML
    private void loadstuff() {
    serviceNumb.setText(String.valueOf(service.getSerivisNumber()));
    numbTF.setText(service.getTelephone());
    nameProductTF.setText(service.getName());
    ownerTF.setText(service.getOwner());
    priceTF.setText(String.valueOf(service.getPrice()));
    description.setText(service.getDescription());
    coment.setText(service.getComment());
    String status = service.getStatusStg();
        switch (status) {
            case ("prijem"):
                statusCB.getSelectionModel().select(0);
                break;
            case ("obrada"):
                statusCB.getSelectionModel().select(1);
                break;
            case ("zavrseno"):
                statusCB.getSelectionModel().select(2);
                break;
            case  ("naplaceno"):
                statusCB.getSelectionModel().select(3);
        }
    }

    /**
     * loading table of articles that implemented in service
     */
    @FXML
    private void loadingArticle()  {
        tableOfArticle.getItems().clear();
        dbQuerrys= new DBQuerrys();
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumbTC.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        quantityTC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        sumPriceTC.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));
        try {
            tableOfArticle.getItems().addAll(dbQuerrys.listOfArticleInServis(service.getSerivisNumber()));
        } catch (SQLException e) {
           recordInfo.forConnection().error("didnt menage to get artiles from data",e);
        }
    }
    /**
     * enable editing mode
     */
    @FXML
    private void eneler() {
        enableBox.setOnAction(actionEvent -> {
            if (enableBox.isSelected()){
            numbTF.setDisable(false);
            nameProductTF.setDisable(false);
            ownerTF.setDisable(false);
            priceTF.setDisable(false);
            description.setDisable(false);
            coment.setDisable(false); }
            else
            {  numbTF.setDisable(true);
                nameProductTF.setDisable(true);
                ownerTF.setDisable(true);
                priceTF.setDisable(true);
                description.setDisable(true);
                coment.setDisable(true);
            importChange();
            }
        });
    }

    /**
     * sending changed data to DB
     */
    @FXML
    private void importChange(){
        service.setOwner(ownerTF.getText());
        service.setTelephone(numbTF.getText());
        service.setDescription(description.getText());
        service.setName(nameProductTF.getText());
        service.setStatusInt(statusCB.getSelectionModel().getSelectedIndex()+1);
        service.setComment(coment.getText());
        service.setPrice(Float.valueOf(priceTF.getText()));
        service.setSerivisNumber(Integer.valueOf(serviceNumb.getText()));
        try {
            dbQuerrys.editService(service);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    loadstuff();
    eneler();
    loadingArticle();


    }

}
