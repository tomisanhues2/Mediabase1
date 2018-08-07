package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import resources.IConstant;

import java.io.IOException;

public class StartPageController implements IConstant {


    public Button menuNewListButton;
    public Button menuViewListButton;
    public Label menuTitleString;
    private String buttonPressed;


    public void menuButtonPressed(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Stage stage;
        Stage stage1 = new Stage();
        Parent parent = null;
        Parent parent1 = null;
        boolean bool = false;
        try {
            stage = ((Stage) menuTitleString.getScene().getWindow());

            buttonPressed = ((Button) event.getSource()).getId();

            switch (buttonPressed) {
                case "menuNewListButton":
                    parent = fxmlLoader.load(getClass().getClassLoader().getResource("views/MovieList.fxml"), messages);
                    parent1 = fxmlLoader.load(getClass().getClassLoader().getResource("views/AddMovie.fxml"), messages);
                    bool = true;
                    break;
                case "menuViewListButton":
                    parent = fxmlLoader.load(getClass().getClassLoader().getResource("views/ListView.fxml"), messages);
                    startExistingProcess();
                    break;
            }
            stage.setScene(new Scene(parent));
            if (bool) {
                stage1.setScene(new Scene(parent1));
                stage1.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startExistingProcess() {


    }

}
