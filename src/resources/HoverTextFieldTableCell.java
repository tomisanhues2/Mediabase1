package resources;

import controllers.HoverInfoController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;

public final class HoverTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> implements IConstant, IObservableLists {

    public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn() {
        return forTableColumn(new DefaultStringConverter());
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(final StringConverter<T> converter) {
        return list -> {
            final TextFieldTableCell<S, T> result = new TextFieldTableCell<>(converter);
            final Popup popup = new Popup();
            popup.setAutoHide(true);

            final EventHandler<MouseEvent> hoverListener = new EventHandler<>() {

                @Override
                public void handle(MouseEvent event) {
                    if (result.getText() != null) {
                        try {
                            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/HoverInfo.fxml"), messages);
                            final Parent root = fxmlLoader.load();
                            HoverInfoController hoverInfoController = fxmlLoader.getController();
                            hoverInfoController.setHoverTitle(IObservableLists.getMoviesObservableList().get(result.getIndex()).getTitle());
                            hoverInfoController.setHoverImage(IMovie.GET_IMAGE_URL_FROM_OBJECT(IObservableLists.getMoviesObservableList().get(result.getIndex()).getImageURL()));
                            hoverInfoController.setHoverActors(IObservableLists.getMoviesObservableList().get(result.getIndex()).getActors());

                            popup.getContent().clear();
                            popup.getContent().addAll(root);


                            if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
                                popup.hide();
                            } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                                popup.show(result, event.getScreenX() + 10, event.getScreenY());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            result.setOnMouseClicked(hoverListener);
            result.setOnMouseExited(hoverListener);
            return result;
        };
    }
}
