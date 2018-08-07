package resources;

import java.io.File;
import java.util.ResourceBundle;

public interface IConstant {

    File MOVIE_FILE = new File("movie.ser");

    ResourceBundle messages = ResourceBundle.getBundle("Messages");

}
