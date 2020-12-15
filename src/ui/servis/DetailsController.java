package ui.servis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

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
    @FXML
    private void loadstuff() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
