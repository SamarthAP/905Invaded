package Game;

/**
 * Energy balls that Player throws. Extends Entity.
 */
public class Ball extends Entity{

    /**
     * Creates a Ball at x and y coordinates
     * @param xAxis X coordinate of Ball
     * @param yAxis Y coordinate of Ball
     * @param level Level being used to reference information
     */
    Ball(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "ball.png");
    }

    /**
     * Moves Ball
     */
    public void move() {
        setX(getX() + Settings.BALL_SPEED);
        collisionWithPlatforms();
        collisionWithEnemy();
    }

    /**
     * Kills Ball if it collides with a Platform
     */
    public void collisionWithPlatforms() {
        for (Platform platform : getLevel().getPlatforms()) {
            if (intersects(platform.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Kills Ball if it collides with Enemy
     */
    public void collisionWithEnemy() {
        for (Enemy enemy : getLevel().getEnemies()) {
            if (intersects(enemy.getLayoutBounds())) {
                kill();
            }
        }
    }
}
