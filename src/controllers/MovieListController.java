package controllers;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import objects.Movie;
import resources.HoverTextFieldTableCell;
import resources.IConstant;
import resources.IObservableLists;
import resources.MySQLManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieListController implements IConstant, IObservableLists {

    @FXML
    private TableColumn listTableID;
    @FXML
    private TableColumn listTableTitle;
    @FXML
    private TableColumn listTableYear;
    @FXML
    private TableColumn listTableScore;
    @FXML
    private TableColumn listTableMore;
    @FXML
    private TableView movieTable;
    @FXML
    private Button addMovieButton;
    @FXML
    private TextField listSearchField;
    @FXML
    private TextField listNameField;
    @FXML
    private TextField listAuthorField;
    @FXML
    private Button saveListButton;
    @FXML
    private HBox configHBox;
    @FXML
    private HBox menuBarHBox;

    private static boolean ExistingList;

    private FilteredList<Movie> movies;


    public static boolean isExistingList() {
        return ExistingList;
    }


    @FXML
    public void initialize() throws IOException {
        if (IObservableLists.getMoviesObservableList().size() != 0) {
            addMovieButton.setDisable(true);
            saveListButton.setText("Exit");

            configHBox.setVisible(false);
            ExistingList = true;
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("views/CommentPage.fxml"), messages);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }

        listTableID.setCellFactory(TextFieldTableCell.<Movie, Integer>forTableColumn(new IntegerStringConverter()));
        listTableTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        listTableYear.setCellFactory(TextFieldTableCell.forTableColumn());
        listTableScore.setCellFactory(TextFieldTableCell.forTableColumn());
        listTableMore.setCellFactory(HoverTextFieldTableCell.forTableColumn());
        movies = new FilteredList(IObservableLists.getMoviesObservableList(), p -> true);//Pass the data to
        listSearchField.setOnKeyPressed(keyEvent -> {
            movies.setPredicate(m -> m.getTitle().toLowerCase().contains(listSearchField.getText().toLowerCase().trim()));
        });
        movieTable.setItems(movies);
//        listTablePoster.setCellFactory(TextFieldTableCell.forTableColumn());

    }


    public void addMovieButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/AddMovie.fxml"), messages);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignore) {

        }
    }

    public void saveAndQuitButton(ActionEvent event) {
        if (saveListButton.getText().equalsIgnoreCase("Exit")) {
            MySQLManager.insertCommentToDB();
        }
        if (!isExistingList()) {
            if (!(listAuthorField.getText().isEmpty() || listNameField.getText().isEmpty())) {
                ArrayList<Movie> movies = new ArrayList<>();
                movies.addAll(IObservableLists.getMoviesObservableList());
                MySQLManager.createMoviesFromList(movies, listAuthorField.getText(), listNameField.getText());
                System.out.println("Saved to database");
                System.exit(0);
            } else {

            }
        } else {
            System.exit(0);
        }
    }
}
