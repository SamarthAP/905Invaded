package Game;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The help menu of the game. Extends Scene.
 */
public class HelpScene extends Scene{

    // Main VBox of the Scene that Text will be added to
    private static VBox vBox = new VBox(40);

    /**
     * Creates a HelpScene with VBox, width, and height
     */
    HelpScene() {
        super(vBox, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        initialize();
    }

    /**
     * Initializes the text information and adds to VBox
     */
    private void initialize() {
        Text title = new Text("Help");
        title.setFont(new Font("Berlin Sans FB", 50));
        title.setFill(Color.WHITE);

        Text about = new Text("The city has been invaded by powerful aliens from space! It is your job as Earth's Guardian to clear the city of aliens and save everyone!");
        about.setFont(new Font("Berlin Sans FB", 20));
        about.setFill(Color.WHITE);

        Text controls = new Text("The controls are simple. Press A to move left, D to move right, W to jump and SPACE to fire your awesome energy balls.");
        controls.setFont(new Font("Berlin Sans FB", 20));
        controls.setFill(Color.WHITE);

        Text hints = new Text("It can take multiple hits of energy balls to kill aliens! Since the aliens are ruthless, they will also fire their own energy balls at you so watch out!" +
                " \nMake sure you don't get hit by them because you will die. You can also die from touching the alien's toxic skin, so don't do that either!");
        hints.setFont(new Font("Berlin Sans FB", 20));
        hints.setFill(Color.WHITE);

        Text leaveHelp = new Text("One you are done reading everything, click on the screen to go back to the main menu.");
        leaveHelp.setFont(new Font("Berlin Sans FB", 20));
        leaveHelp.setFill(Color.WHITE);

        // Background image for HelpScene
        ImagePattern imagePattern = new ImagePattern(new Image("brickwall.jpg"));

        vBox.setBackground(new Background(new BackgroundFill(imagePattern, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().addAll(title, about, controls, hints, leaveHelp);

    }

}
