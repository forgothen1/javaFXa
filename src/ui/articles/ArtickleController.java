package ui.articles;

import db.DBQuerrys;
import entites.Articles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import recordInfo.RecordInfo;
import security.Securty;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ArtickleController extends Securty implements Initializable {

    public Label totalUlazneVrijednosti;
    public Label totalIzlazneVrijednosti;
    public Label totalTrenutnevrijednosti;
    public CheckBox cbMasine,cbDjelovi;
    @FXML
    private TableView<Articles> table;
    @FXML
    private TableColumn<Articles, String> name,serialNumber,description;
    @FXML
    private TableColumn<Articles, Integer> idArtickle,prodatoART, stanjeART,UpotrebaART,ulazART ;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Articles, Float> price, sumPrice,imputPrice,outputPrice;
    RecordInfo logInfo;
    DBQuerrys artikl= new DBQuerrys();
    DBQuerrys DBQuerrys = new DBQuerrys();
    Articles articles= new Articles();

    /**
     * filling table wih data
     * @throws SQLException trows somwehre
     */
    @FXML
    public void settingTable() throws SQLException {
      //  table.setEditable(true);
        idArtickle.setCellValueFactory(new PropertyValueFactory<>("IdArticles"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ulazART.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        UpotrebaART.setCellValueFactory(new PropertyValueFactory<>("quantityUse"));
        prodatoART.setCellValueFactory(new PropertyValueFactory<>("kolicinaProdato"));
        stanjeART.setCellValueFactory(new PropertyValueFactory<>("kolicinaUkupno"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        imputPrice.setCellValueFactory(new PropertyValueFactory<>("imputPrice"));
        outputPrice.setCellValueFactory(new PropertyValueFactory<>("outputPrice"));
        sumPrice.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));
        table.getItems().addAll(artikl.gettingAllArtikles());
        countdown(artikl.gettingAllArtikles());
    }

    /**
     * its swich to seperate machines and spere parts if needed
     * @return String filtering by defoult =(1,2)
     */
    @FXML
    private String filteredArticleByType(){
        String filtering="(1,2)";
        if (cbMasine.isSelected() && !cbDjelovi.isSelected()) {

            return  "(1,0)";
        }
        else if (cbDjelovi.isSelected() && !cbMasine.isSelected())
        {   return "(2,0)";
        }
        return filtering;
    }

    /**
     * counting totals
     * @throws SQLException
     */
    @FXML
    public void countdown(List <Articles> article_list) {

        Float totalImputPrice = (float) 0;
        Float totalOutputPrice= (float) 0;
        Float totalATMPrice=(float) 0;
        for (int i =0;i<article_list.size();i++) {
         float b= article_list.get(i).getImputPrice();
         float b1=article_list.get(i).getOutputPrice();
         float b2 = article_list.get(i).getKolicinaUkupno()*article_list.get(i).getPrice();

            totalImputPrice= totalImputPrice+b;
            totalOutputPrice = totalOutputPrice+b1;
            totalATMPrice = totalATMPrice+b2;
            totalUlazneVrijednosti.setText(String.valueOf(totalImputPrice));
            totalIzlazneVrijednosti.setText(String.valueOf(totalOutputPrice));
            totalTrenutnevrijednosti.setText(String.valueOf(totalATMPrice));
        }
    }
    @FXML
    public void addingEntrys() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("../articles/proba232p.fxml"));
        Stage stage = new Stage();
        stage.setTitle("INLISTING ARTIKLI");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * method that is for adding / or edditing article on new fxml
     * @param actionEvent
     */
    /*calling new fxml for adding articles*/
    @FXML
    public void settingArticle(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../articles/AddingAtricle.fxml"));
            Stage stage = new Stage();
            stage.setTitle("ading");
            stage.setScene(new Scene(root));
            stage.show();
            /*takes action when u close extra window and it refresh  table  with .clear and calling metode to write to table
             * */
            stage.setOnCloseRequest(windowEvent -> {
                table.getItems().clear();
                try {
                    settingTable();
                } catch (SQLException e) {
                    logInfo.forConnection().error("table didint return , feild to connect to db",e);
                }
                System.out.println("proda dali se zatvorilo");
            });
        } catch (IOException e) {
            logInfo.imputOfStuff().error("button dont work",e);
        }
        System.out.println("Button works fine");
    }

    /**
     * search of articles and if seach is activeted empty then its backed all
     */
    // searching article
    @FXML
    public void SearchArticle() {
        table.getItems().clear();
        try {
            if (searchField.getText().isEmpty() && !cbMasine.isSelected() && !cbDjelovi.isSelected()) {
                settingTable();
            } else {
                table.getItems().addAll(DBQuerrys.searchArticles(searchField.getText().trim(), filteredArticleByType()));
                countdown(DBQuerrys.searchArticles(searchField.getText().trim(), filteredArticleByType()));
            }
        } catch (SQLException e) {
            logInfo.forConnection().error("unable to load from DB");
        }
    }
    /**
     *  seting stuff that loads on opeing window gui
     * @param url hm
     * @param resourceBundle hmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTetLimiter(searchField,20);
        try {
            settingTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
