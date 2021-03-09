package ui.articles;

import db.DBQuerrys;
import entites.Articles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Proba232p implements Initializable {
    public TableView table;
    public TableColumn serijskiTC,nazivTC,nabavnaTC,kolTC,MPCTC,sifraTC,lokacijaTC,desTC, typeTC,slikTC;
    public Button addToTable,loadToDB;
    public List <String> artikli= new ArrayList<>();
    public List <Articles> articles_collection = new ArrayList<>();
    public TextField SerialNumbTF,priceTF,kolTF,nameTF,sifraTF,lokacijaTF,mpcTF;
    public TextArea descriptionTA;
    public Articles articles;
    public DBQuerrys dbQuerrys;
    public TextField ulazTF,brRacunaTF,nazivFirmeTF;
    public DatePicker datumSlanjaDP,datumPrijemaDP;
    public ChoiceBox slikovnaCijenCB,typeCB;



    /**
     *  method that  parse selection from checkbox to integer from 1 to 3;
     * @return integer from 1 to 3
     */
    @FXML
    public Integer typechange(){
    String proba = typeCB.getValue().toString();
        switch (proba) {
            case "Uredjaj":
                return 1;
            case "Alat":
                return 2;
            case "Ugradbeni dio":
                return 3;
        }
        return null;
    }

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
        typeTC.setCellValueFactory(new PropertyValueFactory<>("sortOfProduct"));
    }

    /**
     *  collecting article from Db if its there alredy and fill some tables for faster imput
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
            Float.valueOf(priceTF.getText()), Float.valueOf(mpcTF.getText()), null, typechange(), lokacijaTF.getText());
    articles_collection.add(articles);
    table.getItems().addAll(articles_collection);
   // loadTF();
}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    loadTable();
    SerialNumbTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue)
            {
                try {
                    loadTF();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    });
    }
}
