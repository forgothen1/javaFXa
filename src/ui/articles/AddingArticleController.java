package ui.articles;

import db.DBqueryArtikl;
import entites.Articles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddingArticleController implements Initializable {
@FXML
public TextField name, serialNumber,idArticle,description,quantity,price;
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

     dBqueryArtikl.LoaderForSearch(serialNumber.getText().trim());

 }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
