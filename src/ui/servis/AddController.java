package ui.servis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private TextField name,owner,telephone;
    @FXML
    private TextArea description;
    @FXML
    private void sendData() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
