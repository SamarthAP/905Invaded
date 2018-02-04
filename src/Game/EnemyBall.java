package Game;

/**
 * Energy balls that Enemies throw. Extends Entity.
 */
public class EnemyBall extends Entity{

    /**
     * Creates an EnemyBall at x and y coordinates
     * @param xAxis X coordinate of EnemyBall
     * @param yAxis Y coordinate of EnemyBall
     * @param level Level being used to reference information
     */
    EnemyBall(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "enemyball.png");
    }

    /**
     * Moves EnemyBall
     */
    public void move() {
        setX(getX() - Settings.BALL_SPEED);
        collisionWithPlatforms();
    }

    /**
     * Kills EnemyBall if it collides with a Platform
     */
    public void collisionWithPlatforms() {
        for (Platform platform : getLevel().getPlatforms()) {
            if (intersects(platform.getLayoutBounds())) {
                kill();
            }
        }
    }

}
