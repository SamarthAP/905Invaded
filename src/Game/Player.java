package Game;

import javafx.scene.image.Image;

/**
 * Player of the game, extends Entity. Player jumps, moves and shoots energy balls
 */
public class Player extends Entity{

    // Image for when the Player is jumping
    private Image jumpingImage;

    /**
     * Creates a Player at x and y coordinates, in respect to the level
     * @param xAxis X coordinate of player
     * @param yAxis Y coordinate of player
     * @param level Level being used to reference information
     */
    Player(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "player.png");
        jumpingImage = new Image("playerjump.png");
    }

    /**
     * Throws ball
     * @param level Level that ball is added to to keep track of
     */
    public void throwBall(Level level) {
        Ball ball = new Ball(getX() + getImage().getWidth() - 20, getY() + 10, getLevel());
        level.getBalls().add(ball);
        level.getChildren().add(ball);
    }

    /**
     * Kills Player if Player collides with an enemy ball
     * @param level Level that enemy balls are stored in
     */
    public void collisionWithEnemyBall(Level level) {
        for (EnemyBall enemyBall : level.getEnemyBalls()) {
            if (intersects(enemyBall.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Kills Player if Player collides with an Enemy
     * @param level Level that enemies are stored in
     */
    public void collisionWithEnemy(Level level) {
        for (Enemy enemy : level.getEnemies()) {
            if (intersects(enemy.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Updates the image of Player if Player is jumping
     */
    private void updateJumpAnimation() {
        if (isJumping()) {
            setImage(jumpingImage);
        } else {
            setImage(getInitImage());
        }
    }

    /**
     * Updates all attributes of Player
     * @param level Level used to reference certain information
     */
    public void updateAll(Level level) {
        updateGravity();
        updateJump();
        updateJumpAnimation();
        collisionWithEnemyBall(level);
        collisionWithEnemy(level);
    }

}
