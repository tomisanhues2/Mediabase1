package objects;

import javafx.scene.image.Image;
import parser.MovieParser;
import resources.IDManager;
import resources.IObservableLists;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Movie extends IDManager implements Serializable, IObservableLists {

    private int databaseID;
    private Integer ID;
    private String title;
    private String year;
    private String genres;
    private String actors;
    private String score;
    private Image thumbnail;
    private String imageURL;

    private final String more = "Click here for more...";

    public Movie(int databaseID) {
        this.databaseID = databaseID;
        this.ID = getTotalMovieID();
        IObservableLists.addMovieToObservableList(this);
    }

    public String getMore() {
        return more;
    }



    public Movie(String title, String year, String genres, String actors, String score) {
        this.title = title;
        setYear(year);
        this.genres = genres;
        this.actors = actors;
        setScore(score);
        this.ID = getTotalMovieID();
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        if (year.length() < 4) {
            this.year = "0000";
        } else {
            this.year = year.substring(0, 4);
        }
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        if (genres == null) {
            this.genres = "N/A";
        }else {
            this.genres = genres;
        }
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        if (score.length() < 3) {
            this.score = score + "/10";
        }
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setScore(int score) {
        this.score = score + "/10";
    }
}