package ui.articles;

import db.DBQuerrys;
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
import recordInfo.RecordInfo;
import security.Securty;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ArtickleController extends Securty implements Initializable {

    @FXML
    private TableView<Articles> table;
    @FXML
    private TableColumn<Articles, String> name,serialNumber,description;
    @FXML
    private TableColumn<Articles, Integer> idArtickle ;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Articles, Integer> quantity;
    @FXML
    private TableColumn<Articles,Integer> quantityInUse;
    @FXML
    private TableColumn<Articles, Integer> price;
    RecordInfo logInfo;
    DBQuerrys artikl= new DBQuerrys();
    DBQuerrys DBQuerrys = new DBQuerrys();

    /**
     * filling table wih data
     * @throws SQLException trows somwehre
     */
    @FXML
    public void settingTable() throws SQLException {
        table.getItems().addAll(artikl.gettingAllArtikles());
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
     * search of articles and if seach is activeted empty then its backed al
     */
    // searching article
    @FXML
    public void SearchArticle() {
        table.getItems().clear();
        try {
            if (searchField.getText().isEmpty()) {
                settingTable();
            } else {
                table.getItems().addAll(DBQuerrys.searchArticles(searchField.getText().trim()));
            }
        } catch (SQLException e) {
            logInfo.forConnection().error("unable to load from DB.e");
        }
    }
    /**
     *  seting stuff that loads on opeing window gui
     * @param url hm
     * @param resourceBundle hmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idArtickle.setCellValueFactory(new PropertyValueFactory<>("IdArticles"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityInUse.setCellValueFactory(new PropertyValueFactory<>("quantityUse"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addTetLimiter(searchField,20);
        try {
            settingTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
