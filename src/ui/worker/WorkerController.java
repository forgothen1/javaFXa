package ui.worker;
/* controller that interact with starting gui window, and its asinged for all comunication from starting window to other classes*/
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import entites.Worker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import db.*;
import javafx.util.Callback;
import ui.Xon;


public class WorkerController extends Xon implements Initializable {

    @FXML
    public TextField searchField;
    @FXML
    private TableView<Worker> table;
    @FXML
    private TableColumn<Worker, String> Name;
    @FXML
    private TableColumn<Worker, String> Surname;
    @FXML
    private TableColumn<Worker, Integer> Paymant;
    @FXML
    private TableColumn<Worker, String> Workplace;
    @FXML
    private TableColumn<Worker, Integer> idWorker;
    @FXML
    private  Label labalIdworker, labelName,labelSurname, labelPayment,labelWorkplace;
    @FXML
    private Pane pane;
    @FXML
    private Button editWorkerButton;
    Worker worker;
    DBqueryRadnik dBquery;
    //table adding all content to table from DB

    public void tableCollectingData() throws SQLException {
        DBqueryRadnik dBquery = new DBqueryRadnik();
        table.getItems().addAll(dBquery.gettingAllWorkers());
    }
    //button  that activates new window for adding, removing , editing new worker  to table/DB

    @FXML
    public void setAddPersonButton(javafx.event.ActionEvent actionEvent) {
             try {
            Parent root1 = FXMLLoader.load(getClass().getResource("../worker/AddingGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("menaging");

            stage.setScene(new Scene(root1));
            stage.show();
            /*takes action when u close extra window and it refresh  table  with .clear and calling metode to write to table
             * */
            stage.setOnCloseRequest(windowEvent -> {
                table.getItems().clear();
                try {
                    tableCollectingData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("proda dali se zatvorilo");
            });
        } catch (IOException e) {
            System.out.println("button dont work ");
        }
        System.out.println("Button works fine");
    }
    /*getting  from  gui table select of row and getting labels set*/
@FXML
public void setEditWorkerButton() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../worker/AddingGUI.fxml"));
        //alowing to connect 2 controllers and to transfer data , that from 1st window travel true here to second window

        fxmlLoader.setControllerFactory(new Callback<>() {
            AddingController addingController = new AddingController();
            @Override
            public Object call(Class<?> mainController) {
                if (mainController == AddingController.class) {
                    addingController.definderForSetupOfWindow = labalIdworker.getText();
                    addingController.editable = true;
                }
                return addingController;
            }
        });
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("menaging");
        stage.setScene(new Scene(root1));
        stage.show();
        /*takes action when u close extra window and it refresh  table  with .clear and calling metode to write to table
         * */
        stage.setOnCloseRequest(windowEvent -> {
            table.getItems().clear();
            try {
                tableCollectingData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("proda dali se zatvorilo");
            System.out.println("Button works fine");
        });
    } catch (IOException e) {
        System.out.println("button don't work ");
    }


}
    @FXML
    public void settingLabels() throws SQLException {
         dBquery = new DBqueryRadnik();

        int indexOfRow= table.getSelectionModel().getFocusedIndex();
        Integer valueOfRow = (Integer) table.getColumns().get(4).getCellObservableValue(indexOfRow).getValue();
     //   System.out.println("index of row: "+indexOfRow); // ready for console check up
     //   System.out.println("value of idWorker: "+valueOfRow); // ready for console check up
        worker=dBquery.searchOfWorkerByIdWorker(String.valueOf(valueOfRow)).get(0);
   //     System.out.println( worker.toString());  // ready for console check up
     //   System.out.println(worker.getName());   // ready for console check up
       labelName.setText(worker.getName());
       labelSurname.setText(worker.getSurname());
       labelPayment.setText(String.valueOf(worker.getPaymant()));
       labelWorkplace.setText(worker.getWorkplace());
        labalIdworker.setText(String.valueOf(worker.getIdWorker()));

        // pane is used to show or hide  labels that is selected from table
       pane.setVisible(true);

       editWorkerButton.setDisable(false);

                }


    //react on pressing ENTER in textField  filters table but its attached directly to DB
    @FXML
    public void setSearchParameter() throws SQLException {
        DBqueryRadnik dBquery = new DBqueryRadnik();

        table.getItems().clear();
        if (searchField.getText().trim().isEmpty()) {
            try {
                tableCollectingData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (searchField.getText().length() >= 3) {
            try {
                table.getItems().addAll(dBquery.searchOfWorkers(searchField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
 // seting up table cells  where what to hook

        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        Paymant.setCellValueFactory(new PropertyValueFactory<>("paymant"));
        Workplace.setCellValueFactory(new PropertyValueFactory<>("workplace"));
        idWorker.setCellValueFactory(new PropertyValueFactory<>("idWorker"));

        //calling methot that fill table in GUI.
        try {
            tableCollectingData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
