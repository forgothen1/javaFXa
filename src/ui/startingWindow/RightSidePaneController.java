package ui.startingWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RightSidePaneController implements Initializable {
    @FXML
    private Pane pane;

    /**
     * loading servis workstation for service
     * @throws IOException
     */
    @FXML
    public void nesto() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../servis/Service.fxml"));
        Stage stage = new Stage();
        stage.setTitle("new Serivis");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
