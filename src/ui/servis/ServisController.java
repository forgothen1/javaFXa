package ui.servis;

import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import recordInfo.RecordInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServisController implements Initializable {

    @FXML
    private TableColumn price,name,time,description,owner,telephone,numberOfTicket;
    @FXML
    private TableView<Service> table;
    private RecordInfo info = new RecordInfo();
    @FXML
    public void openAddingWindow()
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../servis/AddingWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("new Serivis");
            stage.setScene(new Scene(root));
            stage.show();
            /*takes action when u close extra window and it refresh  table  with .clear and calling metode to write to table
             * */
            stage.setOnCloseRequest(windowEvent -> {
                table.getItems().clear();

                System.out.println("proda dali se zatvorilo");
            });
        } catch (IOException e) {
        info.imputOfStuff().error(e);
        }
        System.out.println("Button works fine");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
