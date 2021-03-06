package ui.worker;

/*Controller class for AddingGUI.fxml class  controlle flow from gui to work classes*/
import db.DBQuerrys;
import entites.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import recordInfo.RecordInfo;
import security.Securty;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddingController extends Securty implements Initializable {
    @FXML
    public TextField name,surname,paymant,workplace,idWorker;
    @FXML
    public Button button;
    @FXML
    public Button removeButton;
    @FXML
    public String definderForSetupOfWindow;

    public boolean editable;
    @FXML
    public Label infoLine;
     Worker worker;
     DBQuerrys dBquery;
     RecordInfo recordInfo;

    /**
     * method for removing worker from DB with idWorker
     */
    // method for removing worker from DB with idWorker
    @FXML
    public void remove()  {

        try {
            dBquery.remove(definderForSetupOfWindow);
        } catch (SQLException e) {
            recordInfo.forConnection().error("didnt load to db",e);
        }
        infoLine.setText("Radnik je obrisan ");
    }

    /**
     * method that talks to class inputofData and send new data to implement in DB .trim() rly important later allot problems with out it
     */
    // method that talks to class inputofData and send new data to implement in DB .trim() rly important later allot problems with out it
    @FXML
    public void inserintIntoDBWorker() {

        //  promjeniti nazit metode,  dodati exceptione posebnop za idworker  da na label izbacuje da
        //   nemoze da vec ima itd.

        if (!paymant.getText().isEmpty() && !name.getText().isEmpty() && !surname.getText().isEmpty()
                && !workplace.getText().isEmpty() && !idWorker.getText().isEmpty()) {
            worker.setName(name.getText().trim());
            worker.setSurname(surname.getText().trim());
            worker.setPaymant(Float.valueOf(paymant.getText().trim()));
            worker.setWorkplace(workplace.getText().trim());
            worker.setIdWorker(Integer.valueOf(idWorker.getText().trim()));
            //    System.out.println("*****************************");
            //  System.out.println(worker.toString());     for geting in console printout
            try {
                if (editable) {
                    dBquery.editWorker(worker, definderForSetupOfWindow);
                    infoLine.setText("Radnik je izmjenjen pod idWokrer: " + definderForSetupOfWindow);
                } else {
                    dBquery.inputOfWorker(worker);
                    infoLine.setText("Radnik je dodan u bazu podataka");
                }

            } catch (SQLException e) {
                recordInfo.forConnection().error("ddint menage to loadt ot DB", e);
            }
        } else {
            System.out.println("niste unijeli sve zahtjevne podatke");
            infoLine.setText("niste unijeli sve zahtjevne podatke");
        }
    }

    /**
     * method that set up new window for editing or adding , changing name of button add/eddit show remove button
     */
    /* method that set up new window for editing or adding , changing name of button add/eddit show remove button */
    public void settingEditingWindow() {
        dBquery= new DBQuerrys();
        System.out.println("u novom prozoru   "+ editable);
        if(editable){
            button.setText("EDIT");
            removeButton.visibleProperty().setValue(true);
            System.out.println(definderForSetupOfWindow);
            try {
                worker=dBquery.searchOfWorkerByIdWorker(String.valueOf(definderForSetupOfWindow)).get(0);
            } catch (SQLException e) {
           recordInfo.forConnection().error(e); }
            name.setText(worker.getName());
            surname.setText(worker.getSurname());
            paymant.setText(String.valueOf(worker.getPaymant()));
            workplace.setText(worker.getWorkplace());
            idWorker.setText(String.valueOf(worker.getIdWorker()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* dodati komande za editanje , spoji se na metodu za pretragu , te podatke ubaciti po sektorima u textfildove
         *  i onda da se moze izmjenjat i na pritsak dugmeta da se pokupi trenutni value textfildova i posalje u metodu
         * koja ce imati sqlquery za alter table
         */
        addTetLimiter(name,20);
        addTetLimiter(surname,20);
        addTetLimiter(paymant, 7);
        addTetLimiter(workplace, 10);
        addTetLimiter(idWorker, 5);
        addTetLimiter2(paymant);
        addTetLimiter1(idWorker);
        settingEditingWindow();


    }
}
