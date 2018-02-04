package Game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Platform that Entities move on. Extends rectangle.
 */
public class Platform extends Rectangle {

    /**
     * Creates a Platform at x and y coordinates with a width, height and type
     * @param xAxis X coordinate of Platform
     * @param yAis Y coordinate of Platform
     * @param width Width of Platform
     * @param height Height of Platform
     * @param type Type of Platform refers to the ImagePattern of the Platform
     */
    Platform(double xAxis, double yAis, double width, double height, int type) {
        super(xAxis, yAis, width, height);
        if (type == 1) {
            ImagePattern imagePattern = new ImagePattern(new Image("topdirt.jpg"));
            setFill(imagePattern);
        } else if (type == 2) {
            ImagePattern imagePattern = new ImagePattern(new Image("dirt.jpg"));
            setFill(imagePattern);
        } else {
            // Throw IllegalArgumentException if a Platform is created with a type other than 1 or 2
            throw new IllegalArgumentException("Type can only be 1 or 2");
        }


    }

}
