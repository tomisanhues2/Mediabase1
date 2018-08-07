package parser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import objects.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import resources.IMovie;

public class MovieParser extends Movie implements IMovie {

    private int movieID;


    public MovieParser(int movieID) {
        super(movieID);
        this.movieID = movieID;
        try {
            getDetailsFromWeb();
            getCreditsFromWeb();
            getImagesFromWebURL();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }


    private void getDetailsFromWeb() throws UnirestException {
        HttpResponse<String> detailsResponse = Unirest.get(IMovie.GET_DETAILS_URL_FROM_WEB(movieID)).asString();
        JSONObject detailsJSON = new JSONObject(detailsResponse.getBody());

        setID(getTotalMovieID());
        setTitle(detailsJSON.getString("title"));
        setYear(detailsJSON.getString("release_date"));
        if (detailsJSON.optJSONArray("genres").length() < 1 || detailsJSON.optJSONArray("genres") == null) {
            setGenres("N/A");
        } else {
            setGenres(detailsJSON.optJSONArray("genres").optJSONObject(0).optString("name", "N/A"));
        }
        setScore(detailsJSON.getInt("vote_average"));

    }

    private void getCreditsFromWeb() throws UnirestException {
        HttpResponse<String> creditsResponse = Unirest.get(IMovie.GET_CREDITS_URL_FROM_WEB(movieID)).asString();
        JSONObject creditsJSON = new JSONObject(creditsResponse.getBody());
        JSONArray actorsArray = creditsJSON.optJSONArray("cast");
        String actorsString = "";
        int totalActors = (actorsArray.length() < 3) ? actorsArray.length() : 3;
        for (int i = 0; i < totalActors; i++) {
            actorsString += actorsArray.getJSONObject(i).getString("name") + ", ";
        }
        setActors(actorsString);
    }

    private void getImagesFromWebURL() throws UnirestException {
        HttpResponse<String> imagesResponse = Unirest.get(IMovie.GET_IMAGES_URL_FROM_WEB(movieID)).asString();
        JSONObject imagesJSON = new JSONObject(imagesResponse.getBody());
        JSONArray imagesArray = imagesJSON.getJSONArray("posters");
        if (imagesArray.length() < 1) {
            setImageURL("https://i.imgur.com/eG4HIxN.jpg");
        } else {
            setImageURL(imagesArray.getJSONObject(0).getString("file_path"));
        }
    }
}