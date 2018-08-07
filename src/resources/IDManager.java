package resources;

public class IDManager {

    private static Integer totalMovieID = 0;

    public IDManager() {
        totalMovieID++;
        if (totalMovieID == 41) {
            totalMovieID = 1;
        }
    }

    public static Integer getTotalMovieID() {
        return totalMovieID;
    }
}
