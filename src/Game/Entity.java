package Game;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Superclass for all entities in the game. Extends ImageView.
 * Allows certain actions to be performed.
 */
public class Entity extends ImageView {

    // Image of entity
    private Image image;

    // Basic attributes of entity
    private boolean canJump, isJumping;
    private boolean gravityOn;
    private boolean alive;

    // Entity performs actions based on level
    private Level level;

    // Max change in yAxis for jumping
    private static final int DELTA_Y = 10;

    // Current change in yAxis for jumping
    private double measureY;

    /**
     * Creates an entity at x and y axis' with respect to level, and creates and image based on the fileName
     * @param xAxis X coordinate of entity
     * @param yAxis Y coordinate of entity
     * @param level Level being used to reference information
     * @param fileName File name of the image
     */
    Entity(double xAxis, double yAxis, Level level, String fileName) {
        setX(xAxis);
        setY(yAxis);

        this.level = level;

        this.image = new Image(fileName);
        setImage(image);

        gravityOn = true;
        measureY = 0;

        alive = true;

        isJumping = false;

    }

    /**
     * Moves entity left
     */
    public void moveLeft() {
        setX(getX() - Settings.PLAYER_SPEED);

        // Makes sure entity doesn't pass through the platform
        for (Platform platform : level.getPlatforms()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                // +0.01 just to make sure entity stays right of the bound
                setX(bounds.getMaxX() + 0.01);
            }
        }

        // Makes sure entity doesn't go offscreen to the left
        if (getX() < 0) {
            setX(0);
        }
    }

    /**
     * Moves entity right
     */
    public void moveRight() {
        setX(getX() + Settings.PLAYER_SPEED);

        // Makes sure entity doesn't pass through the platform
       for (Platform platform : level.getPlatforms()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                // -0.01 just to make sure entity stays left of the bound
                setX(bounds.getMinX() - this.getImage().getWidth() - 0.01);
            }
        }
    }

    /**
     * Updates gravity affecting the entity
     */
    public void updateGravity() {
        if (gravityOn) {
            setY(getY() + Settings.GRAVITY);

            // Makes sure entity doesn't pass through the platform
            for (Platform platform : level.getPlatforms()) {
                Bounds bounds = platform.getLayoutBounds();
                if(intersects(bounds)) {
                    // +0.01 just to make sure the entity stays above the bound
                    setY(bounds.getMinY() - this.getImage().getHeight() - 0.01);
                    // The entity can only jump if it is touching the ground
                    canJump = true;
                }
            }
        }
    }

    /**
     * Updates jump information for the entity
     */
    public void updateJump() {

        // Makes entity move up as long as it hasn't reached DELTA_Y
        if (isJumping && measureY < DELTA_Y) {
            setY(getY() - Settings.PLAYER_JUMP_SPEED);
            measureY += 0.75;
        } else {
            // If entity reaches DELTA_Y turns gravity on
            gravityOn = true;
            isJumping = false;
        }
    }

    /**
     * Makes entity jump
     */
    public void jump() {

        if (canJump) {
            // Sets attributes to handle gravity
            gravityOn = false;
            isJumping = true;
            canJump = false;
            measureY = 0;
        }
    }

    /**
     * Kills entity
     */
    public void kill() {
        alive = false;
    }

    // Getters

    public Level getLevel() {
        return level;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public Image getInitImage() {
        return image;
    }
}
