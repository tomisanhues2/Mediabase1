package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import resources.IObservableLists;
import resources.MySQLManager;

public class CommentPageController implements IObservableLists {

    private int currentComment = 0;

    @FXML
    private Label commentText;
    @FXML
    private TextArea commentField;
    @FXML
    private Button addCommentButton;


    @FXML
    public void initialize() {
        commentText.setText(IObservableLists.getCommentsObservableList().get(currentComment));
    }

    public void previousButton(ActionEvent actionEvent) {
        currentComment--;
        if (currentComment < 0)
            currentComment = getMaxIndex();
        commentText.setText(IObservableLists.getCommentsObservableList().get(currentComment));
    }

    public void nextButton(ActionEvent actionEvent) {
        currentComment++;
        if (currentComment > getMaxIndex())
            currentComment = 0;
        commentText.setText(IObservableLists.getCommentsObservableList().get(currentComment));
    }

    private int getMaxIndex() {
        return IObservableLists.getCommentsObservableList().size() -1;
    }

    public void addCommentToDB(ActionEvent actionEvent) {
        if (commentField.getText().isEmpty()) {
            commentField.setPromptText("Invalid comment, try again");
        } else {
            IObservableLists.addCommentToObservableList(commentField.getText());
            addCommentButton.setDisable(true);
        }
    }
}
