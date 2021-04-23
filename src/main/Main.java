package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * this is main class start gui it has start method
 */
public class Main extends Application {
    /**
     * start method call for starting window
     * @param primaryStage  starting stage that is called
     * @throws Exception exception that will be fixed somewhere
     */
    @Override
    public void start(Stage primaryStage) throws IOException {


            Parent root = FXMLLoader.load(getClass().getResource("/ui/startingWindow/StartingDisplay.fxml"));
            primaryStage.setTitle("Prikaz Radnika");
            // primaryStage.initStyle(StageStyle.UNDECORATED);
            // BorderPane borderPane = new BorderPane();
            // borderPane.setStyle("-fx-background-color: #00FF00");
            primaryStage.setScene(new Scene(root, 1300, 800));
            primaryStage.show();




    }


    public static Logger logger = Logger.getLogger(Main.class);

    /**
     * main metod that calls start gui + some loggers for now
     * @param args
     */
    public static void main(String[] args) throws JRException {
        logger.info("elooo");
        logger.trace("nesto");
        logger.error("neki problemi");
        launch(args);

       // JasperReport jasperReport= JasperCompileManager.compileReport("");
    }

}
