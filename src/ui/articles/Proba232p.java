package ui.articles;

import db.DBQuerrys;
import entites.Articles;
import entites.PurchesInvoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Proba232p implements Initializable {
    public Label totalVPC, totalMPC, totalPDV;
    @FXML
    private TableView<Articles> table;
    @FXML
    private TableColumn serijskiTC,nazivTC,nabavnaTC,kolTC,MPCTC,sifraTC,lokacijaTC,desTC, typeTC,slikTC,vrstaTC;
    @FXML
    private Button addToTable,loadToDB;
    @FXML
    private List <String> artikli= new ArrayList<>();
    @FXML
    private List <Articles> articles_collection = new ArrayList<>();
    @FXML
    private TextField SerialNumbTF,priceTF,kolTF,nameTF,sifraTF,lokacijaTF,mpcTF;
    @FXML
    private TextArea descriptionTA;
    @FXML
    private Articles articles;
    @FXML
    private DBQuerrys dbQuerrys;
    @FXML
    private TextField ulazTF,brRacunaTF,nazivFirmeTF;
    @FXML
    private DatePicker datumSlanjaDP,datumPrijemaDP;
    @FXML
    private ChoiceBox slikovnaCijenCB,typeCB,mesureType;
    @FXML
    private  PurchesInvoice purchesInvoice;
    private DecimalFormat df = new DecimalFormat("#.##");


    /**
     * just setting peripherals for table columns
     */
    @FXML
    public void loadTable(){
        serijskiTC.setCellValueFactory(new PropertyValueFactory<>("SerialNumber"));
        nazivTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        nabavnaTC.setCellValueFactory(new PropertyValueFactory<>("entryPrice"));
        kolTC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        MPCTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        sifraTC.setCellValueFactory(new PropertyValueFactory<>("idArticles"));
        lokacijaTC.setCellValueFactory(new PropertyValueFactory<>("location"));
        desTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("sordOfProductSTG"));
        vrstaTC.setCellValueFactory(new PropertyValueFactory<>("jedMjereStg"));
        slikTC.setCellValueFactory(new PropertyValueFactory<>("sizeOfEtickSTG"));
    }

    /**
     *  collecting article from Db if its there already and fill some tables for faster impout
     * @throws SQLException
     */
    @FXML
    public void loadTF () throws SQLException {
        //load tf  from db
        dbQuerrys= new DBQuerrys();
       List <Articles> articles_collection2;
       System.out.println("proba");
        articles_collection2 = dbQuerrys.searchBySerialNumber(SerialNumbTF.getText().trim());
        if (!articles_collection2.isEmpty()) {
            articles = articles_collection2.get(0);
            nameTF.setText(articles.getName());
            kolTF.setText("1");
            priceTF.setText(String.valueOf(articles.getEntryPrice()));
            mpcTF.setText(String.valueOf(articles.getPrice()));
            sifraTF.setText(String.valueOf(articles.getIdArticles()));
            lokacijaTF.setText(articles.getLocation());
            descriptionTA.setText(articles.getDescription());
            typeCB.getSelectionModel().select(articles.getSortOfProduct());
        }
        else { System.out.println("nadaaa"); }
    }

    /**
     * fill table with product / articles  from above imput
     */
    @FXML
    public void tableFill() {
        table.getItems().clear();
        articles = new Articles(null, nameTF.getText(), SerialNumbTF.getText(), Integer.valueOf(sifraTF.getText()), descriptionTA.getText(),
            Integer.valueOf(kolTF.getText()), null, null, null,
            Float.valueOf(priceTF.getText()), Float.valueOf(mpcTF.getText()), null, typeCB.getSelectionModel().getSelectedIndex(),
            lokacijaTF.getText(), mesureType.getSelectionModel().getSelectedIndex(),slikovnaCijenCB.getSelectionModel().getSelectedIndex());
        articles_collection.add(articles);
        table.getItems().addAll(articles_collection);
        float sumMPC = (float) 0;
        float sumVPC= (float) 0;
        for (int i=0; i<table.getItems().size();i++) {
            sumVPC = sumVPC +(table.getItems().get(i).getEntryPrice() *table.getItems().get(i).getQuantity().floatValue());
            sumMPC = sumMPC + (table.getItems().get(i).getEntryPrice() *table.getItems().get(i).getQuantity().floatValue());
        }
        totalVPC.setText(df.format(sumVPC));
        totalMPC.setText(df.format(sumMPC));
        totalPDV.setText(String.valueOf(0));
}

    /**
     * collecting data from  gui to store data of entery  in DB
     * @throws SQLException
     */
    @FXML
    public void  transitionToDB() throws SQLException {
        dbQuerrys= new DBQuerrys();
        purchesInvoice = new PurchesInvoice();
        purchesInvoice.setEntry(ulazTF.getText());
        purchesInvoice.setOrderNumber(brRacunaTF.getText());
        purchesInvoice.setQuantity(articles_collection.size());
        purchesInvoice.setSuplayer(nazivFirmeTF.getText());
        purchesInvoice.setDateRecive(String.valueOf(datumPrijemaDP.getValue()));
        purchesInvoice.setDateSent(String.valueOf(datumSlanjaDP.getValue()));
        purchesInvoice.setMPprice(Float.valueOf(totalMPC.getText()));
        purchesInvoice.setVPPrice(Float.valueOf(totalVPC.getText()));
        purchesInvoice.setPDV(Float.valueOf(totalPDV.getText()));
        dbQuerrys.addInvoice(purchesInvoice);
        articles= new Articles();
        for (Articles value : articles_collection) {
            articles = value;
            dbQuerrys.addingToArticles(articles);
        }
    }

    @FXML
    public void StringEntryArticles () {
        dbQuerrys= new DBQuerrys();
 ///cemo vidjeti dali  je potrebno
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    loadTable();
    SerialNumbTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue)
        {
            try {
                loadTF();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    });
    }
}
