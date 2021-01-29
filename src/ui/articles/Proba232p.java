package ui.articles;

import entites.Articles;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class Proba232p implements Initializable {
    public TableView table;
    public TableColumn nazivTC;
    public TableColumn serijskiTC;
    public TableColumn kolTC;
    public TableColumn nabavnaTC;


    @FXML
    public void nestoTable(){
        table.setEditable(true);
        nazivTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        nazivTC.setCellFactory(TextFieldTableCell.forTableColumn());
        nazivTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Articles, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Articles, String> t) {
                        ((Articles) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                });
        serijskiTC.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        serijskiTC.setCellFactory(TextFieldTableCell.forTableColumn());
        serijskiTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Articles, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Articles, String> t) {
                ((Articles) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setSerialNumber(t.getNewValue());
            }
        });
        kolTC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        kolTC.setCellFactory(TextFieldTableCell.forTableColumn());
        kolTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Articles, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Articles, String> t) {
                ((Articles) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setQuantity(Integer.valueOf(t.getNewValue()));
            }
        });
        nabavnaTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        nabavnaTC.setCellFactory(TextFieldTableCell.forTableColumn());
        nabavnaTC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Articles, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Articles, String> t) {
                ((Articles) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPrice(Float.valueOf(t.getNewValue()));
            }
        });
        table.getItems().addAll(new Articles());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    nestoTable();
    }
}
