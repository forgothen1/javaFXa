package ui.servis;

import db.DBQuerrys;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import recordInfo.RecordInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * class for adding new services and modifiang basic  info
 */
public class AddController implements Initializable {
    @FXML
    private TextField name,owner,telephone;
    @FXML
    private TextArea description;
    Service service= new Service();
    DBQuerrys dbQuerrys= new DBQuerrys();
    RecordInfo logCon;
    /**
     * sending data about new services
     */
    @FXML
    private void sendData() {
        service.setName(name.getText().trim());
        service.setOwner(owner.getText().trim());
        service.setTelephone(telephone.getText().trim());
        service.setDescription(description.getText().trim());
        try {
            dbQuerrys.addServices(service);
        } catch (SQLException e) {

            logCon.forConnection().error("unable to add to DB",e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
