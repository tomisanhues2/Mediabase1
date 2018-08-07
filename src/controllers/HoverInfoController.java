package controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.IMovie;

public class HoverInfoController {
    public Label hoverTitle;
    public ImageView hoverImage;
    public Label hoverActors;

    public void setHoverTitle(String title) {
        hoverTitle.setText(title);
    }

    public void setHoverImage(Image image) {
        hoverImage.setImage(image);
    }

    public void setHoverActors(String actors) {
        hoverActors.setText(actors);
    }
}
