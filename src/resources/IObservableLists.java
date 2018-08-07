package resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.AddMovie;
import objects.List;
import objects.Movie;

public interface IObservableLists {

    ObservableList<Movie> moviesObservableList = FXCollections.observableArrayList();
    ObservableList<AddMovie> searchMoviesObservableList = FXCollections.observableArrayList();
    ObservableList<List> listObservableList = FXCollections.observableArrayList();
    ObservableList<String> commentsObservableList = FXCollections.observableArrayList();

    static void addMovieToObservableList(Movie movie) {
        moviesObservableList.add(movie);
    }

    static void addMovieSearchToObservableList(AddMovie movie) {
        searchMoviesObservableList.add(movie);
    }

    static void addListToObservableList(List list) {
        listObservableList.add(list);
    }

    static void addCommentToObservableList(String string) {
        commentsObservableList.add(string);
    }

    static ObservableList<Movie> getMoviesObservableList() {
        return moviesObservableList;
    }

    static ObservableList<AddMovie> getSearchMoviesObservableList() {
        return searchMoviesObservableList;
    }

    static ObservableList<List> getListObservableList() {
        return listObservableList;
    }

    static ObservableList<String> getCommentsObservableList() {
        return commentsObservableList;
    }
}
