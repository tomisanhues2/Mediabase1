package controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import objects.AddMovie;
import org.json.JSONArray;
import org.json.JSONObject;
import parser.MovieParser;
import resources.IMovie;
import resources.IObservableLists;


public class AddMovieController implements IObservableLists, IMovie {
    @FXML
    public Button addNewMovie;
    @FXML
    public Button searchNewMovie;
    @FXML
    private ImageView addMovieImage;
    @FXML
    private Label addMovieDescription;
    @FXML
    private TableView movieTable;
    @FXML
    private TableColumn listTableTitle;
    @FXML
    private TableColumn listTableYear;
    @FXML
    private TableColumn listTableRatings;
    @FXML
    private TextField newMovieInput;
    @FXML
    private Label addMovieTitle;

    private int databaseID = 0;

    private MovieParser movieParser;

    @FXML
    public void searchNewMovie(ActionEvent event) {
        if (!IObservableLists.searchMoviesObservableList.isEmpty())
            IObservableLists.searchMoviesObservableList.clear();
        ((Button) event.getSource()).setDisable(true);
        newMovieInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(oldValue)) {
                ((Button) event.getSource()).setDisable(true);
                newMovieInput.getStyleClass().set(1, "invalidInput");
            } else
                ((Button) event.getSource()).setDisable(false);
    });

        if (!newMovieInput.getText().isEmpty()) {
            String movieSearchName = newMovieInput.getText();
            getMoviesFromSearch(movieSearchName);
            newMovieInput.getStyleClass().set(1, "validInput");
        } else {
            newMovieInput.getStyleClass().set(1, "invalidInput");
        }
    }

    @FXML
    public void initialize() {
        listTableTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        listTableYear.setCellFactory(TextFieldTableCell.forTableColumn());
        listTableRatings.setCellFactory(TextFieldTableCell.forTableColumn());
        movieTable.setItems(IObservableLists.searchMoviesObservableList);
        movieTable.setRowFactory(tr -> {
            TableRow<ObservableList> oRow = new TableRow<>();
            oRow.setOnMouseClicked(event -> {
                int selectedIndex = movieTable.getSelectionModel().getSelectedIndex();
                selectedMovie(selectedIndex);
            });
            return oRow;
        });
    }

    private void selectedMovie(int selectedIndex) {
        addMovieImage.setImage(searchMoviesObservableList.get(selectedIndex).getThumbnail());
        addMovieTitle.setText(searchMoviesObservableList.get(selectedIndex).getTitle());
        addMovieDescription.setText(searchMoviesObservableList.get(selectedIndex).getDescription());
        setDatabaseID(searchMoviesObservableList.get(selectedIndex).getDatabaseID());

    }


    public void addNewMovie(ActionEvent actionEvent) {
        if (getDatabaseID() != 0) {
            movieParser = new MovieParser(getDatabaseID());
        }
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    private void getMoviesFromSearch(String movieName) {
        try {
            HttpResponse<String> moviesResponse = Unirest.get(IMovie.GET_MOVIES_URL_FROM_SEARCH(movieName)).asString();
            JSONObject moviesJSON = new JSONObject(moviesResponse.getBody());
            JSONArray moviesArray = moviesJSON.getJSONArray("results");
            JSONObject jsonObject;
            for (int i = 0; i < moviesArray.length(); i++) {
                jsonObject = moviesArray.getJSONObject(i);
                IObservableLists.addMovieSearchToObservableList(new AddMovie(jsonObject.getString("title"),
                        jsonObject.getString("release_date"),
                        IMovie.GET_IMAGE_URL_FROM_OBJECT(IMovie.GET_JSON_IMAGE_FROM_OBJECT_URL(jsonObject)),
                        jsonObject.getInt("vote_average") + "",
                        jsonObject.getString("overview"), jsonObject.getInt("id")));
            }
        } catch (UnirestException e) {
            System.out.println(e.getCause().toString());
        }
    }
}
