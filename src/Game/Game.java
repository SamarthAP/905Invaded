/*
SAMARTH PATEL
Mr. Le
ICS4U
January 22, 2018

Description: This is a 2D platformer. The objective of the game is to complete all levels to save the city.
 */
package Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Main class that handles all interactions between classes and objects.
 * Also launches the game.
 */
public class Game extends Application {

    // Main timeline for animations
    private Timeline timeline;

    // Main stage
    private Stage stage;

    // Scene for help
    private HelpScene helpScene;

    // Group for main menu
    private Group root;

    // Level being displayed
    private Level level;

    // Current level number
    private int levelNumber;

    // Main player object
    private Player player;

    // Keys being pressed
    private HashSet<KeyCode> pressedKeys;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes everything and loads the main menu
     * @param primaryStage Main stage
     */
    @Override
    public void start(Stage primaryStage) {


        stage = primaryStage;

        pressedKeys = new HashSet<>();

        levelNumber = 1;

        helpScene = new HelpScene();

        displayMainMenu();

    }

    /**
     * Starts the game and loads the level
     * @param levelNum Level that is being played
     */
    private void play(int levelNum) {
        generateLevel(levelNum);

        // Initialize timeline and update the game every time the timeline refreshes
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> update())
        );

        // Sets timeline to run indefinitely
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates all entities and important objects
     */
    private void update() {
        setKeyPresses();
        player.updateAll(level);
        updateLevelEntities();

        // If player passes a level
        if (player.getX() + player.getFitWidth() >= level.getWidth()) {
            timeline.stop();
            if (levelNumber <= 2) {
                // Move on to next level
                levelNumber++;
                play(levelNumber);
            } else {
                // Clears levels and HashSets, stops timeline and  and shows the end screen
                timeline.stop();
                level.getChildren().clear();
                level.setLayoutX(0);
                pressedKeys.clear();
                levelNumber = 1;
                finishGame();
            }
        }
    }

    /**
     * Updates all entities in the level
     */
    private void updateLevelEntities() {

        // Moves all balls in level
        for (Ball ball : level.getBalls()) {
            ball.move();
        }
        // Updates all enemies in level
        for (Enemy enemy : level.getEnemies()) {
            enemy.updateAll(level);
        }
        // Updates all enemy balls in level
        for (EnemyBall enemyBall : level.getEnemyBalls()) {
            enemyBall.move();
        }

        removeDeadEntities();
    }

    /**
     * Removes all dead entities from level
     */
    private void removeDeadEntities() {
        // This method avoids java.util.ConcurrentModificationException to occur, referenced from:
        // https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an
        List<Enemy> deadEnemies = new ArrayList<Enemy>();
        List<Ball> deadBalls = new ArrayList<Ball>();
        List<EnemyBall> deadEnemyBalls = new ArrayList<EnemyBall>();

        // Checks for dead entities in level and adds to respective dead lists
        for (Enemy enemy : level.getEnemies()) {
            if (!enemy.isAlive()) deadEnemies.add(enemy);
        }
        for (Ball ball : level.getBalls()) {
            if (!ball.isAlive()) deadBalls.add(ball);
        }
        for (EnemyBall enemyBall : level.getEnemyBalls()) {
            if (!enemyBall.isAlive()) deadEnemyBalls.add(enemyBall);
        }

        // Removes dead entities from level and lists in level
        level.getEnemies().removeAll(deadEnemies);
        level.getChildren().removeAll(deadEnemies);

        level.getBalls().removeAll(deadBalls);
        level.getChildren().removeAll(deadBalls);

        level.getEnemyBalls().removeAll(deadEnemyBalls);
        level.getChildren().removeAll(deadEnemyBalls);

        // If player is dead, clear level and HashSet, stop timeline,
        // reset game progress (change level to first level), reset "camera" (the panning of the level) and show main menu
        if (!player.isAlive()) {
            level.getChildren().clear();
            level.setLayoutX(0);
            //level = null;
            timeline.stop();
            displayMainMenu();
            pressedKeys.clear();
            levelNumber = 1;
        }

    }

    /**
     * Shows the end scene when the game is finished
     */
    private void finishGame() {
        Group endDisplay = new Group();

        Scene endScene = new Scene(endDisplay, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        endDisplay.getChildren().add(new ImageView(new Image("end.jpg")));

        // Sets mouse click event so that user can return to main menu
        endScene.setOnMouseClicked(event -> displayMainMenu());

        stage.setScene(endScene);
        stage.show();
    }

    /**
     * Display the main menu with buttons and background
     */
    private void displayMainMenu() {

        root = new Group();

        root.getChildren().add(new ImageView(new Image("menu.jpg")));

        Scene mainMenu = new Scene(root, Settings.GAME_WIDTH, Settings.GAME_HEIGHT, Color.rgb(51, 51, 51));

        displayButtons();

        stage.setScene(mainMenu);
        stage.show();
    }

    /**
     * Adds buttons to main group
     */
    private void displayButtons() {

        VBox box = new VBox(36);

        generateMainMenuButtons(box);

        box.setTranslateY(Settings.GAME_HEIGHT/2);
        box.setTranslateX(Settings.GAME_WIDTH/2 - Settings.BUTTONWIDTH/2);

        root.getChildren().add(box);
    }

    /**
     * Creates buttons for the main menu with onclick events
     * @param vbox The VBox that the buttons are being added to
     */
    private void generateMainMenuButtons(VBox vbox) {

        Button play = new Button("Play");
        play.setPrefSize(Settings.BUTTONWIDTH, Settings.BUTTONHEIGHT);

        play.setOnMouseClicked(event -> play(levelNumber));

        Button help = new Button("Help");
        help.setPrefSize(Settings.BUTTONWIDTH, Settings.BUTTONHEIGHT);

        help.setOnMouseClicked(event -> displayHelp());

        vbox.getChildren().addAll(play, help);

    }

    /**
     * Displays the help menu
     */
    private void displayHelp() {

        // Onclick event for returning to the main menu
        helpScene.setOnMouseClicked(event -> displayMainMenu());

        stage.setScene(helpScene);
        stage.show();
    }

    /**
     * Creates a level based on the level number
     * @param levelNumber Number that references a level
     */
    private void generateLevel(int levelNumber) {
        this.level = new Level(levelNumber, Settings.GAME_HEIGHT);

        this.player = new Player(100, 0, level);

        level.getChildren().add(player);

        Scene s = new Scene(level, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        s.setFill(new ImagePattern(new Image("toronto1.png")));

        // Set key press events and adds them to HashSet
        s.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        s.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        stage.setScene(s);
        stage.show();
    }

    /**
     * Player controls based on input from HashSet
     */
    private void setKeyPresses() {
        if (pressedKeys.contains(KeyCode.A)) {player.moveLeft(); moveLevel();}
        if (pressedKeys.contains(KeyCode.D)) {player.moveRight(); moveLevel();}
        if (pressedKeys.contains(KeyCode.W)) player.jump();
        if (pressedKeys.contains(KeyCode.SPACE)) {player.throwBall(level);}
    }

    /**
     * Pans level based on the location of the player
     */
    private void moveLevel() {
        // Keep the "camera" at a third of the screen
        double shift = Settings.GAME_WIDTH / 3;

        // Starts moving camera when players reaches the "first third" of the screen in the beginning of the level
        // and stop moving camera at the "last third" of the screen at the end of the level, learned from:
        // https://gamedev.stackexchange.com/questions/44256/how-to-add-a-scrolling-camera-to-a-2d-java-game
        if (!(player.getX() < shift)) {
            level.setLayoutX(-1 * player.getX() + shift);
            double min = -1 * level.getWidth() + Settings.GAME_WIDTH;
            if (level.getLayoutX() < min) {
                level.setLayoutX(min);
            }
        }
    }
}

/*
References:

Game.java:
    removeDeadEntities():
    Method of adding all dead entities to a list then removing all at once was found on Stack Overflow.
    My old method raised java.util.ConcurrentModificationException error.
    This new method avoids java.util.ConcurrentModificationException to occur, referenced from:
    https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an

    moveLevel():
    Had to learn how to move Level with the Player. My old method would cause some glitches to the Level when the Player
    went out of bounds. This resource helped me learn what I was doing wrong and correct my mistakes.
    https://gamedev.stackexchange.com/questions/44256/how-to-add-a-scrolling-camera-to-a-2d-java-game

Level.java:
    Wanted to find a way to easily create a level and objects in level and be able to change them on the fly, but I
    didn't know how to accomplish that.
    These resources helped me understand various concepts I needed to be able to iterate through a text file and
    based on the content of the file, create objects in the Level.
    https://stackoverflow.com/questions/35400110/creating-an-object-from-a-text-file-with-multiple-objects
    https://stackoverflow.com/questions/676250/different-ways-of-loading-a-file-as-an-inputstream
    https://www.youtube.com/watch?v=fnsBoamSscQ
 */