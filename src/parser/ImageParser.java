package parser;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageParser {

/*    public static BufferedImage getBufferedImageFromUrl(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        return image;
    }*/

    public static Image getImageFromUrl(URL url) throws IOException {
        return new Image(url.toString());
    }

}
