package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import objects.List;
import objects.Movie;
import resources.IConstant;
import resources.IObservableLists;
import resources.MySQLManager;

import java.io.IOException;

public class ListViewController implements IObservableLists, IConstant {

    @FXML
    public TableColumn listIDColumn;
    @FXML
    public TableColumn listNameColumn;
    @FXML
    public TableColumn listAuthorColumn;
    @FXML
    public TableView listTable;

    private int selectedIndex = -1;

    @FXML
    public void initialize() {
        listIDColumn.setCellFactory(TextFieldTableCell.<List, Integer>forTableColumn(new IntegerStringConverter()));
        listNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        listAuthorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        listTable.setItems(IObservableLists.getListObservableList());
    }

    @FXML
    public void selectListButton(ActionEvent event) {

        try {
            selectedIndex = listTable.getSelectionModel().getSelectedIndex();
        } catch (Exception ignored) {

        }


        if (selectedIndex < 0) {

        } else {
            MySQLManager.getMoviesFromList(selectedIndex);
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                    "views/MovieList.fxml"), messages);
            Stage stage = (Stage) listTable.getScene().getWindow();

            try {
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

}
