package resources;


import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import objects.Movie;
import org.json.JSONObject;

public interface IMovie {


    String DATABASE_API_KEY = "?api_key=9ea34de517a4414366db6520cafda608";
    String DATABASE_URL_STRING = "https://api.themoviedb.org/3/movie/";
    String DATABASE_SEARCH_URL_STRING = "https://api.themoviedb.org/3/search/movie";
    String DATABASE_OPTIONAL_LANGUAGE = "&language=en-US";

    String DATABASE_IMAGE_URL_STRING = "https://image.tmdb.org/t/p/w500";

    String TEST_MOVIE_ID_STRING = "348350"; //remove me
    int TEST_MOVIE_ID_INT = 348350; //remove me
    //


    static String GET_DETAILS_URL_FROM_WEB(int movieID) {
        return DATABASE_URL_STRING + movieID + DATABASE_API_KEY + DATABASE_OPTIONAL_LANGUAGE;
    }

    static String GET_CREDITS_URL_FROM_WEB(int movieID) {
        return DATABASE_URL_STRING + movieID + "/credits"+ DATABASE_API_KEY;
    }

    static String GET_IMAGES_URL_FROM_WEB(int movieID) {
        return DATABASE_URL_STRING + movieID + "/images"+ DATABASE_API_KEY;
    }

    static Image GET_IMAGE_URL_FROM_OBJECT(String link) {
        if (link.equalsIgnoreCase("https://i.imgur.com/eG4HIxN.jpg")) {
            return new Image("https://i.imgur.com/eG4HIxN.jpg");
        }
        Image image = new Image(DATABASE_IMAGE_URL_STRING + link);
        return image;
    }

    static String GET_JSON_IMAGE_FROM_OBJECT_URL(JSONObject object) {

        if ((object.optString("poster_path", null)) != null) {
            return object.getString("poster_path");
        }
        return "https://i.imgur.com/eG4HIxN.jpg";
    }

    static String GET_MOVIES_URL_FROM_SEARCH(String movieName) { //&query=Star%20wars
        String newMovieName = movieName.replace(" ", "%20");
        return DATABASE_SEARCH_URL_STRING + DATABASE_API_KEY + "&query="+ newMovieName  + "&include_adult=false";
    }


}
