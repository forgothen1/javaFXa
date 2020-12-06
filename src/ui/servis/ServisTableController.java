package ui.servis;

import db.DBQuerrys;
import entites.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import recordInfo.RecordInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServisTableController implements Initializable {

    public TextField searchField;
    @FXML
    private TableColumn<Object, Object> price;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn time;
    @FXML
    private TableColumn description;
    @FXML
    private TableColumn owner;
    @FXML
    private TableColumn telephone;
    @FXML
    private TableColumn numberOfTicket;
    @FXML
    private TableColumn status;
    @FXML
    private TableView<Service> table;
    Service service= new Service();
    DBQuerrys dbQuerrys = new DBQuerrys();

    private RecordInfo info = new RecordInfo();

    /**
     * setting table to show all services
     * @throws SQLException
     */
    @FXML
    public void setTable() throws SQLException {
         name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        numberOfTicket.setCellValueFactory(new PropertyValueFactory<>("serivisNumber"));
   //     status.setCellValueFactory(new PropertyValueFactory<>("statusInt"));


        //  status.setCellValueFactory(new PropertyValueFactory<>("status"));
        /**
         * this is to check what is writed down in row of this column  and change color of text  in colum status
         */
        status.setCellFactory(column -> {
            return new TableCell<Service, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                      //  setStyle("-fx-alignment: CENTER-LEFT;");
                        int currentIndex = indexProperty()
                                .getValue() < 0 ? 0
                                : indexProperty().getValue();
                        item = getTableColumn().getTableView().getItems().get(currentIndex).getStatusInt();
                        switch (item) {
                            case "prijem":
                                setTextFill(Color.BLUE);
                                break;
                            case "obrada":
                                setTextFill(Color.GREEN);
                                break;
                            case "zavrseno":
                                setTextFill(Color.RED);
                                setStyle("-fx-font-family:System bold;");
                                break;
                        }
                        setText(item);
                    }
                    if (empty)
                    {
                        setText(null);
                        setTextFill(null);
                    }

                }
            };
        });
        table.getItems().addAll(dbQuerrys.tableservis());
     //   neka stoji za poslije mozda zatreba po potrebi
    /*   int d =table.getItems().size();
            System.out.println(d);
            table.getStylesheets().add("ui/style/stil.css");
            for (int i=0;i<d;i++)
            {


                String valueOfRow = String.valueOf(table.getColumns().get(2).getCellObservableValue(i).getValue());

                if (valueOfRow.equals("brane"))
                {
                     owner.getStyleClass().set(i,"my");
                }
                else
                    owner.getStyleClass().set(i,"ti");
                System.out.println(valueOfRow);
            } */
    }

    /**
     * opens new window for addin services
     */
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

    /**
     * search of service with  parametrs
     */
    @FXML
    public void searchoFservice() {
        table.getItems().clear();
        if (searchField.getText().trim().isEmpty()) {
            try {
                setTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
        {
            table.getItems().addAll(dbQuerrys.searchOfService(searchField.getText().trim()));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            setTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
