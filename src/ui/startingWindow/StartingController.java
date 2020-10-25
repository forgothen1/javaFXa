package ui.startingWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* class that have controller for starting window few buttons and pane to load other fxml*/
public class StartingController implements Initializable {
@FXML
 private Pane secPane;

    /**
     * method that load  worker table and whole gui for workers , upload is used into pane but 1st its clear
     * @throws IOException somwehere will be fixed
     */
 /*  method that load  worker table and whole gui for workers , upload is used into pane but 1st its clear*/
    @FXML
    public void loadWorkerMenu() throws IOException {
        secPane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../worker/WorkerDisplay.fxml"));
        secPane.getChildren().add(newLoadedPane);
    }

    /**
     *  method that load  table and buttons for article into pane element but 1st clears pane
     * @throws IOException omwehere will be fixed
     */
    /*method that load  table and buttons for article into pane element but 1st clears pane*/
    @FXML
    public void loadArtikleMenu() throws IOException {
        secPane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../articles/ArticleDisplay.fxml"));
        secPane.getChildren().add(newLoadedPane);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
