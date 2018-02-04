package Game;

import java.util.Random;

/**
 * Enemy of the game. Extends Entity. Throws enemy balls in attempt to kill Player
 */
public class Enemy extends Entity{

    // random and NUMBER used to determine if enemy will throw enemy balls
    private Random random;
    private static int NUMBER = 1;

    // Number of times Enemy is hit by Player's balls
    private int hits;

    /**
     * Creates an Enemy at x and y coordinates, in respect to the level
     * @param xAxis X coordinate of Enemy
     * @param yAxis Y coordinate of Enemy
     * @param level Level being used to reference information
     */
    Enemy(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "enemy.png");
        random = new Random();
        hits = 0;
    }

    /**
     * Throws an enemy ball
     * @param level Level that enemy ball is added to to keep track of
     */
    public void throwBall(Level level) {
        // Throws a ball "randomly"
        // Random was used since this project doesn't use time as a unit for calculations
        if (random.nextInt(100) == NUMBER) {
            EnemyBall enemyBall = new EnemyBall(getX() + getImage().getWidth() - 20, getY() + 10, getLevel());
            level.getEnemyBalls().add(enemyBall);
            level.getChildren().add(enemyBall);
        }
    }

    /**
     * Adds to hits if Enemy collides with a Player's ball
     * @param level Level that Player's balls are stored in
     */
    private void ballCollision(Level level) {
        for (Ball ball : level.getBalls()) {
            if (intersects(ball.getLayoutBounds())) {
                hits++;
            }
        }
    }

    /**
     * Kills Enemy if hits are more than 40
     */
    private void updateAliveStatus() {
        if (hits > 40) kill();
    }

    /**
     * Updates all attributes of an Enemy
     * @param level Level used to reference certain information
     */
    public void updateAll(Level level) {
        throwBall(level);
        ballCollision(level);
        updateAliveStatus();
        updateGravity();
    }

}
