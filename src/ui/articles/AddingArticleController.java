package ui.articles;

import db.DBqueryArtikl;
import entites.Articles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddingArticleController implements Initializable {
@FXML
public TextField name, serialNumber,idArticle,description,quantity,price;
    public Button sendingDataButton;

    /*alowing to add more articles into table/DB  probably need refactor to reedo for adding existing*/
 @FXML
 public void adding() throws SQLException {
     Articles articles= new Articles();
     DBqueryArtikl dBqueryArtikl= new DBqueryArtikl();
     articles.setName(name.getText().trim());
     articles.setIdArticles(Integer.valueOf(idArticle.getText().trim()));
     articles.setSerialNumber(serialNumber.getText().trim());
     articles.setDescription(description.getText().trim());
     articles.setQuantity(Integer.valueOf(quantity.getText().trim()));
     articles.setPrice(Integer.valueOf(price.getText().trim()));
    dBqueryArtikl.addingToArticles(articles);
 }
 @FXML
 public  void getSerialNumber() throws SQLException {
     DBqueryArtikl dBqueryArtikl = new DBqueryArtikl();

        serialNumber.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1 && serialNumber.getText().length()>3) {
                dBqueryArtikl.LoaderForSearch(serialNumber.getText().trim());
                System.out.println(serialNumber.getText().trim());
                Articles articles;

                try {
                    articles = dBqueryArtikl.searchArticles().get(0);

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
                } catch (SQLException e) {
                   System.out.println("nije bilo podudarnosti u bazi");
                }
            }
     });



 }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            getSerialNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
