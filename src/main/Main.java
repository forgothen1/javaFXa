package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


/**
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ui/startingWindow/StartingDisplay.fxml"));
        primaryStage.setTitle("Prikaz Radnika");
        primaryStage.setScene(new Scene(root, 800, 530));
        primaryStage.show();

    }

 public static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("elooo");
        logger.trace("nesto");
        logger.error("neki problemi");
        launch(args);

    }

}
