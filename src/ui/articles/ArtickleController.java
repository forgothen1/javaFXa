package ui.articles;

import db.DBqueryArtikl;
import entites.Articles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ArtickleController implements Initializable {

    @FXML
    public TableView<Articles> table;
    @FXML
    public TableColumn<Articles, String> name,serialNumber,description;

    @FXML
    public TableColumn<Articles, Integer> idArtickle ;
    public TextField searchField;
    @FXML
    private TableColumn<Articles, Integer> quantity;
    @FXML
    private TableColumn<Articles,Integer> quantityInUse;
    @FXML
    private TableColumn<Articles, Integer> price;
    @FXML
    public void settingTable(){

        try {
            DBqueryArtikl artikl= new DBqueryArtikl();
            table.getItems().addAll(artikl.gettingAllArtikles());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
                settingTable();
                System.out.println("proda dali se zatvorilo");
            });
        } catch (IOException e) {
            System.out.println("button dont work ");
        }
        System.out.println("Button works fine");
    }
     @FXML
    public void SearchArticle()
    {   table.getItems().clear();
    if (searchField.getText().isEmpty())
        {settingTable();}
    else {
        try {

            DBqueryArtikl dBqueryArtikl = new DBqueryArtikl();
            table.getItems().addAll(dBqueryArtikl.searchArticles(searchField.getText().trim()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idArtickle.setCellValueFactory(new PropertyValueFactory<>("IdArticles"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityInUse.setCellValueFactory(new PropertyValueFactory<>("quantityUse"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        settingTable();
    }
}
