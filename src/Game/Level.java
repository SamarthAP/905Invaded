package Game;

import javafx.scene.Group;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Level class used for creating various levels of the game, extends Group.
 * Platforms and other objects are referenced from text files.
 * Holds all objects in type-specific ArrayLists
 */
public class Level extends Group{

    // ID's to identify objects being added to level
    private static final int EMPTY_ID = 0;
    private static final int PLATFORM_TOP_ID = 1;
    private static final int PLATFORM_ID = 2;
    private static final int ENEMY_ID = 3;

    private static  final double WIDTH_RATIO = 5;

    private double width, height;

    // Holds all active objects
    private ArrayList<Platform> platforms;
    private ArrayList<Ball> balls;
    private ArrayList<EnemyBall> enemyBalls;
    private ArrayList<Enemy> enemies;

    private Random random;

    /**
     * Basic constructor. Loads information from text file and creates objects referenced from the file.
     * Also sets ratios relating to width and height.
     * @param number The level number
     * @param height Height of the level
     */
    Level(int number, double height) {
        platforms = new ArrayList<Platform>();
        balls = new ArrayList<Ball>();
        enemyBalls = new ArrayList<EnemyBall>();
        enemies = new ArrayList<Enemy>();

        random = new Random();

        // Gets text file as InputStream, used to add objects to level based on text file, resources used:
        // https://stackoverflow.com/questions/35400110/creating-an-object-from-a-text-file-with-multiple-objects
        // https://stackoverflow.com/questions/676250/different-ways-of-loading-a-file-as-an-inputstream
        // https://www.youtube.com/watch?v=fnsBoamSscQ
        InputStream levelsInfo = getClass().getClassLoader().getResourceAsStream("Levels/Level" + number + ".txt");

        Scanner reader = new Scanner(levelsInfo);

        // Numbers referencing width and height of level information from text file
        int widthInPlatform = reader.nextInt();
        int heightInPlatform = reader.nextInt();

        // Height and width of level
        this.height = height;
        this.width = widthInPlatform / heightInPlatform * this.height * WIDTH_RATIO;

        // Sets platform ratios
        double platformHeightRatio = height / heightInPlatform; // Basically 720/8 = 90
        double platformWidthRatio = platformHeightRatio * WIDTH_RATIO; // 90 * 5 = 450

        // For-each loop creating objects (platforms, other sprites, etc.) referenced from text file
        for (int y = 0; y < heightInPlatform; y++) {
            for (int x = 0; x < widthInPlatform; x++) {
                int checkID = reader.nextInt();

                if (checkID == EMPTY_ID) {
                    continue;
                } else if (checkID == PLATFORM_TOP_ID) {

                    // Creates platform and adds to Level
                    Platform p = new Platform(platformWidthRatio * x, platformHeightRatio * y, platformWidthRatio, platformHeightRatio, PLATFORM_TOP_ID);
                    platforms.add(p);
                    getChildren().add(p);
                } else if (checkID == PLATFORM_ID) {
                    Platform p = new Platform(platformWidthRatio * x, platformHeightRatio * y, platformWidthRatio, platformHeightRatio, PLATFORM_ID);
                    platforms.add(p);
                    getChildren().add(p);
                } else if (checkID == ENEMY_ID) {

                    // Places enemy in a random location around the middle of the platform
                    double xAxis = (x * platformWidthRatio) + platformWidthRatio/2 + (((random.nextInt(101 + 100) - 100)/100) * (platformWidthRatio / 2));
                    double yAxis = (y * platformHeightRatio);
                    Enemy enemy = new Enemy(xAxis, yAxis, this);
                    enemies.add(enemy);
                    getChildren().add(enemy);
                }

            }

        }


    }

    // Getters

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<EnemyBall> getEnemyBalls() {
        return enemyBalls;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public double getWidth() {
        return width;
    }
}
