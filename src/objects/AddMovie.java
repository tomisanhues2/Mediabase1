package objects;

import javafx.scene.image.Image;

public class AddMovie {

    private int databaseID;
    private String title;
    private String year;
    private String rating;
    private Image thumbnail;
    private String description;

    public AddMovie(String title, String year, Image thumbnail, String rating, String description, int databaseID) {
        this.title = title;
        setYear(year);
        setThumbnail(thumbnail);
        setRating(rating);
        setDescription(description);
        setDatabaseID(databaseID);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.toCharArray().length > 290) {
            this.description = description.substring(0,290) + "...";
        } else {
            this.description = description;
        }
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
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
            this.year = year.substring(0,4);
        }

    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        if (rating.length() < 3) {
            this.rating = rating + "/10";
        }
    }
}
