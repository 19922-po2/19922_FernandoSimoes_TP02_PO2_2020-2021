package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.Score;
import pt.ipbeja.estig.po2.boulderdash.model.View;

import java.util.List;

/**
 * @author Fernando Simões nº 19922
 */

public class BoulderdashBoard extends HBox implements View {
    private Board board;
    private GameButton[][] buttons;
    private Stage myStage;
    private GridPane gameBoard;
    private VBox gameInfoVBOX;
    private Label rockfordLivesLabel;
    private Label currentDiamondCountLabel;
    private Label gameScoreLabel;
    private Button giveUpLevelButton;
    private Label playerNameLabel;
    private Label timerLabel;
    private TextArea highScores;
    private TextArea gameLog;

    public BoulderdashBoard(Board board, Stage myStage) {
        this.gameBoard = new GridPane();
        this.board = board;
        this.board.setView(this);
        this.timerLabel = new Label("Timer: " + this.board.getTimerValue());
        gameStart(); //ask player name and show movement keys before game starts
        createButtonGrid();
        this.myStage = myStage;
        initializeVBOX(board);//game info (side VBox)
        this.gameInfoVBOX.getChildren().addAll(timerLabel, playerNameLabel, rockfordLivesLabel, gameScoreLabel,
                currentDiamondCountLabel, giveUpLevelButton, highScores);
        this.getChildren().addAll(gameInfoVBOX, gameLog, gameBoard);

        this.setOnKeyPressed( //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
                event -> {
                    switch (event.getCode()) {
                        case W:
                            this.board.getRockford().moveEntity(this.board, this.board.getnLine(), this.board.getnCol(), this, -1, 0);
                            break;
                        case S:
                            this.board.getRockford().moveEntity(this.board, this.board.getnLine(), this.board.getnCol(), this, 1, 0);
                            break;
                        case A:
                            this.board.getRockford().moveEntity(this.board, this.board.getnLine(), this.board.getnCol(), this, 0, -1);
                            break;
                        case D:
                            this.board.getRockford().moveEntity(this.board, this.board.getnLine(), this.board.getnCol(), this, 0, 1);
                            break;
                    }
                }
        );
    }

    /**
     * Initializes a VBox containing game info.
     *
     * @param board game board
     */
    private void initializeVBOX(Board board) {
        this.gameInfoVBOX = new VBox();
        this.rockfordLivesLabel = new Label("Rockford Lives: " + this.board.getRockford().getRockfordLives());
        this.gameScoreLabel = new Label("Score: " + this.board.getScore());
        this.currentDiamondCountLabel = new Label("Diamonds: " + this.board.getnDiamonds());
        this.playerNameLabel = new Label("Current Player:\n" + this.board.getPlayerName());
        this.giveUpLevelButton = new Button("Give Up\n(Restart Game)");
        this.highScores = new TextArea("HIGH SCORES:\nName Level Score\n");
        this.highScores.setEditable(false);
        this.highScores.setPrefSize(120, 150);
        this.gameLog = new TextArea("MOVEMENT LOG:\n");
        this.gameLog.setEditable(false);
        this.gameLog.setPrefSize(120, 150);
        this.giveUpLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.resetGame("src/resources/map_1.txt");
            }
        });
    }

    /**
     * Updates the GUI with the current diamond amount.
     */
    public void setDiamondCount() {
        this.currentDiamondCountLabel.setText("Diamonds: " + this.board.getnDiamonds());
    }

    /**
     * Updates the GUI with the current rockford lives.
     */
    public void setRockfordLivesCount() {
        this.rockfordLivesLabel.setText("Rockford Lives: " + this.board.getRockford().getRockfordLives());
    }

    /**
     * Updates the GUI timer.
     *
     * @param timeValue current timer value.
     */
    @Override
    public void timerRefresh(int timeValue) {
        Platform.runLater(() -> {
            this.timerLabel.setText("Timer: " + timeValue);
        });
    }

    /**
     * Updates the GUI with the user score.
     */
    public void setGameScore() {
        this.gameScoreLabel.setText("Score: " + this.board.getScore());
    }

    /**
     * Creates an array of arrays of buttons.
     */
    public void createButtonGrid() {
        this.buttons = new GameButton[this.board.getnCol()][this.board.getnLine()];
        for (int line = 0; line < this.board.getnLine(); line++) {
            for (int col = 0; col < this.board.getnCol(); col++) {
                GameButton gameButton = new GameButton(this.board.getEntity(line, col));
                this.gameBoard.add(gameButton, col, line);
                buttons[col][line] = gameButton;
            }
        }
    }

    /**
     * Updates the GUI whenever rockford moves.
     *
     * @param rockford rockford object.
     * @param entity   object that "changes" places with rockford.
     */
    @Override
    public void rockfordMoved(AbstractPosition rockford, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[rockford.getCol()][rockford.getLine()].setButtonImage(rockford);
        // updates log, converting lines into characters (ASCII table)
        this.gameLog.appendText("rockford " + ((char) (entity.getLine() + 'A' - 1)) + entity.getCol() + "->" +
                ((char) (rockford.getLine() + 'A' - 1)) + rockford.getCol() + "\n");
    }

    /**
     * Updates the GUI whenever an enemy moves.
     *
     * @param enemy  enemy object.
     * @param entity object that "changes" places with the enemy.
     */
    @Override
    public void enemyMoved(AbstractPosition enemy, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[enemy.getCol()][enemy.getLine()].setButtonImage(enemy);
    }

    /**
     * Updates the GUI whenever a diamond moves.
     *
     * @param diamond diamond object.
     * @param entity  object that "changes" places with the diamond.
     */
    @Override
    public void diamondMoved(AbstractPosition diamond, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[diamond.getCol()][diamond.getLine()].setButtonImage(diamond);
    }

    /**
     * Updates the GUI whenever a rock moves.
     *
     * @param rock   rock object.
     * @param entity object that "changes" places with the rock.
     */
    @Override
    public void rockMoved(AbstractPosition rock, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[rock.getCol()][rock.getLine()].setButtonImage(rock);
    }

    /**
     * Updates the GUI when a gate appears.
     *
     * @param gate gate object.
     */
    public void gateAppeared(AbstractPosition gate) {
        this.buttons[gate.getCol()][gate.getLine()].setButtonImage(gate);
    }

    /**
     * Notifies the user that thee current level is over and shows the high score.
     *
     * @param score high score.
     */
    @Override
    public void lvlWon(int score) {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("LEVEL WON!");
        gameWonAlert.setHeaderText("Current Score: " + score);
        gameWonAlert.showAndWait();
    }

    /**
     * Notifies the user that thee game is over and shows the high score.
     *
     * @param score high score.
     */
    @Override
    public void gameOver(int score) {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("GAME WON!");
        gameWonAlert.setHeaderText("Final Score: " + score);
        gameWonAlert.showAndWait();
    }

    /**
     * shows the high scores to the user.
     */
    @Override
    public void showScores(List<Score> highScores) {
        for (Score score : highScores) {
            this.highScores.appendText(score + "\n");
        }
    }

    /**
     * Shows the user that rockford, and an enemy are in the same position and rockford died.
     */
    @Override
    public void rockfordDied() {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("ROCKFORD DIED!");
        gameWonAlert.setHeaderText("Respawn...");
        gameWonAlert.showAndWait();
    }

    /**
     * Resets the playable board and adjusts the window.
     *
     * @param board board's logic.
     */
    @Override
    public void resetBoard(Board board) {
        this.board = board;
        this.board.setView(this);
        this.getChildren().remove(this.gameBoard); //removes gridpane from hbox
        this.gameBoard = new GridPane();
        createButtonGrid();
        this.getChildren().add(this.gameBoard); //adds gridpane
        this.myStage.sizeToScene();
    }

    /**
     * Text view that shows the user how to play the game.
     * Asks the user for the name.
     */
    public void gameStart() {
        TextInputDialog gameStartDialog = new TextInputDialog("(max 8 characters)");
        gameStartDialog.setTitle("BoulderDash Game");
        gameStartDialog.setHeaderText("Movement Keys:\nW - Move Up\nA - Move Left\nS - Move Down\nD - Move Right");
        gameStartDialog.setContentText("Player Name:");
        gameStartDialog.showAndWait();
        validatePlayerName(gameStartDialog.getEditor().getText());
        this.board.setPlayerName(gameStartDialog.getEditor().getText());
        this.board.startTimer();
    }

    /**
     * shows user an error message if there is a IO exception.
     *
     * @param message name of the file that caused the error.
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Validates the player name.
     * The number of characters must be smaller than 8.
     *
     * @param name name inputted by user.
     */
    private void validatePlayerName(String name) {
        if (name.length() > 8) {
            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
            nameError.setTitle("Error");
            nameError.setHeaderText(null);
            nameError.setContentText("Invalid Name\nPlease enter a name with less than 8 characters");
            nameError.showAndWait();
            gameStart();
        }
    }
}
