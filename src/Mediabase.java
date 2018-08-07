import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.IConstant;
import resources.MySQLManager;

public class Mediabase extends Application implements IConstant {

    public static void main(String[] args) {
        MySQLManager mySQLManager = new MySQLManager();
        mySQLManager.getListsFromList();
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader test = new FXMLLoader();
        Parent parent = test.load(getClass().getClassLoader().getResource("views/StartPage.fxml"),messages);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mediabase");
        primaryStage.show();
    }
}
