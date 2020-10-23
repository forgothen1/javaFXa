package ui.articles;

import db.DBqueryArtikl;
import entites.Articles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import security.Securty;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.security.Security;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddingArticleController extends Securty implements Initializable {
@FXML
public TextField name, serialNumber,idArticle,description,quantity,price;
    public Button sendingDataButton;

    /*alowing to add more articles into table/DB  probably need refactor to reedo for adding existing*/

    /**
     *
     * @throws SQLException
     */
 @FXML
 public void adding() throws SQLException {
     Articles articles= new Articles();
     DBqueryArtikl dBqueryArtikl= new DBqueryArtikl();
     articles.setName(name.getText().trim());
     articles.setIdArticles(Integer.valueOf(idArticle.getText().trim()));
     articles.setSerialNumber(serialNumber.getText().trim());
     articles.setDescription(description.getText().trim());
     articles.setQuantity(Integer.valueOf(quantity.getText().trim()));
     articles.setPrice(Float.valueOf(price.getText().trim()));
        if(sendingDataButton.getText().equals("Add")) {
            dBqueryArtikl.addingToArticles(articles);
        }
        if (sendingDataButton.getText().equals("Edditing")) {
          //  dBqueryArtikl.LoaderForSearch(serialNumber.getText().trim());
            dBqueryArtikl.editingArticle(articles,serialNumber.getText().trim());
        }
 }

    /**
     *
     */
 @FXML
 public  void getSerialNumber() {
     DBqueryArtikl dBqueryArtikl = new DBqueryArtikl();
 /* when textfield called serialNumber lost focus and there is more then 3 letter we search DB  and collect data*/
        serialNumber.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1 && serialNumber.getText().length()>2) {
                System.out.println(serialNumber.getText().trim());
                Articles articles;

                try{
                    articles = dBqueryArtikl.searchBySerialNumber(serialNumber.getText().trim()).get(0);
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
                    System.out.println("nema veze");
                }
            }
     });

 }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        addTetLimiter(serialNumber,15);
       addTetLimiter(name,10);
        addTetLimiter(idArticle, 10);
        addTetLimiter(description, 100);
        addTetLimiter(quantity, 5);
        addTetLimiter(price ,9);
        addTetLimiter1(quantity);
        addTetLimiter1(idArticle);
        addTetLimiter2(price);
        getSerialNumber();
    }
}
