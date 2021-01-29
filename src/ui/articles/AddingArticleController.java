package ui.articles;

import db.DBQuerrys;
import entites.Articles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import recordInfo.RecordInfo;
import security.Securty;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddingArticleController extends Securty implements Initializable {
    @FXML
    private TextField name, serialNumber, idArticle, description, quantity, price;
    @FXML
    private Button sendingDataButton;
    private Articles articles;
    private DBQuerrys DBQuerrys;
    RecordInfo logInfo;
    /*alowing to add more articles into table/DB  probably need refactor to reedo for adding existing*/

    /**
     * method that add articles
     * @throws SQLException exception will be fixed somwhere
     */
    @FXML
    public void adding() {
        articles.setName(name.getText().trim());
        articles.setIdArticles(Integer.valueOf(idArticle.getText().trim()));
        articles.setSerialNumber(serialNumber.getText().trim());
        articles.setDescription(description.getText().trim());
        articles.setQuantity(Integer.valueOf(quantity.getText().trim()));
        articles.setPrice(Float.valueOf(price.getText().trim()));
        try {
            if (sendingDataButton.getText().equals("Add")) {
                DBQuerrys.addingToArticles(articles);
            }
            if (sendingDataButton.getText().equals("Edditing")) {
                DBQuerrys.editingArticle(articles, serialNumber.getText().trim());
            }
        } catch (SQLException e) {
            logInfo.forConnection().error("didint menage to connect to db",e);

        }
    }

    /**
     * when textfield called serialNumber lost focus and there is more then 3 letter we search DB  and collect data
     */
    /* when textfield called serialNumber lost focus and there is more then 3 letter we search DB  and collect data*/
    @FXML
    public void getSerialNumber() {
        DBQuerrys= new DBQuerrys();
        serialNumber.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1 && serialNumber.getText().length() > 2) {
                System.out.println(serialNumber.getText().trim());
                try {
                    articles = DBQuerrys.searchBySerialNumber(serialNumber.getText().trim()).get(0);
                    if (!articles.getSerialNumber().isEmpty()) {
                        System.out.println(articles.toString());
                        sendingDataButton.setText("Edditing");
                        name.setText(articles.getName());
                        idArticle.setText(String.valueOf(articles.getIdArticles()));
                        serialNumber.setText(articles.getSerialNumber());
                        quantity.setText(String.valueOf(articles.getQuantity()));
                        price.setText(String.valueOf(articles.getPrice()));
                        description.setText(articles.getDescription());
                    }
                } catch (Exception e) {
                    logInfo.forConnection().error("didint connect to db ",e);
                }
            }
        });
    }
    /**
     * basic setup for logic gui adding article
     * @param url            get url
     * @param resourceBundle emmmmmmmmmmmmmmmmm
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTetLimiter(serialNumber, 15);
        addTetLimiter(name, 20);
        addTetLimiter(idArticle, 10);
        addTetLimiter(description, 100);
        addTetLimiter(quantity, 5);
        addTetLimiter(price, 9);
        addTetLimiter1(quantity);
        addTetLimiter1(idArticle);
        addTetLimiter2(price);
        getSerialNumber();
    }
}
