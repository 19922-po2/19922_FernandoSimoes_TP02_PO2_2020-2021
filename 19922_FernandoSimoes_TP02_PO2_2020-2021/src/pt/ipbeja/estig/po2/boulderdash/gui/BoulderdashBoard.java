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
import pt.ipbeja.estig.po2.boulderdash.model.View;

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

    public BoulderdashBoard(Board board, Stage myStage) {
        this.gameBoard = new GridPane();
        this.board = board;
        this.board.setView(this);
        this.timerLabel = new Label("Timer: " + this.board.getTimerValue());
        gameStart(); //ask player name and show movement keys before game starts
        createButtonGrid();
        this.myStage = myStage;

        //game info (side VBox)
        this.gameInfoVBOX = new VBox();
        //this.timerLabel = new Label(String.valueOf(this.board.getTimerValue()));
        this.rockfordLivesLabel = new Label("Rockford Lives: " + this.board.getRockford().getRockfordLives());
        this.gameScoreLabel = new Label("Score: " + this.board.getScore());
        this.currentDiamondCountLabel = new Label("Diamonds: " + this.board.getnDiamonds());
        this.playerNameLabel = new Label("Current Player:\n" + this.board.getPlayerName());
        this.giveUpLevelButton = new Button("Give Up\n(Restart Lvl)");
        this.giveUpLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.resetGame("src/resources/map_1.txt");
            }
        });
        this.gameInfoVBOX.getChildren().addAll(timerLabel, playerNameLabel, rockfordLivesLabel, gameScoreLabel, currentDiamondCountLabel, giveUpLevelButton);
        this.getChildren().addAll(gameInfoVBOX, gameBoard);

        this.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case W:
                            //this.board.getRockford().rockfordMoveUp(this.board, this.board.getnLine(), this);
                            this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this, -1, 0);
                            break;
                        case S:
                            //this.board.getRockford().rockfordMoveDown(this.board, this.board.getnLine(), this);
                            this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this, 1, 0);
                            break;
                        case A:
                            //this.board.getRockford().rockfordMoveLeft(this.board, this.board.getnCol(), this);
                            this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this, 0, -1);
                            break;
                        case D:
                            //this.board.getRockford().rockfordMoveRight(this.board, this.board.getnCol(), this);
                            this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this, 0, 1);
                            break;
                    }
                }
        );
    }

    public void setDiamondCount() {
        this.currentDiamondCountLabel.setText("Diamonds: " + this.board.getnDiamonds());
    }

    public void setRockfordLivesCount() {
        this.rockfordLivesLabel.setText("Rockford Lives: " + this.board.getRockford().getRockfordLives());
    }

    @Override
    public void timerRefresh(int timeValue) {
        Platform.runLater(() -> {
            this.timerLabel.setText("Timer: " + timeValue);
        });
    }

    public void setGameScore() {
        this.gameScoreLabel.setText("Score: " + this.board.getScore());
    }

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

    @Override
    public void rockfordMoved(AbstractPosition rockford, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[rockford.getCol()][rockford.getLine()].setButtonImage(rockford);
    }

    @Override
    public void enemyMoved(AbstractPosition enemy, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[enemy.getCol()][enemy.getLine()].setButtonImage(enemy);
        //TODO test
    }

    @Override
    public void diamondMoved(AbstractPosition diamond, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[diamond.getCol()][diamond.getLine()].setButtonImage(diamond);
    }

    @Override
    public void rockMoved(AbstractPosition rock, AbstractPosition entity) {
        this.buttons[entity.getCol()][entity.getLine()].setButtonImage(entity);
        this.buttons[rock.getCol()][rock.getLine()].setButtonImage(rock);
    }


    public void gateAppeared(AbstractPosition gate) {
        this.buttons[gate.getCol()][gate.getLine()].setButtonImage(gate);
    }

    @Override
    public void lvlWon(int score) {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("LEVEL WON!");
        gameWonAlert.setHeaderText("Current Score: " + score);
        gameWonAlert.showAndWait();
    }

    @Override
    public void gameOver(int score) {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("GAME WON!");
        gameWonAlert.setHeaderText("Final Score: " + score);
        gameWonAlert.showAndWait();
    }

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

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validatePlayerName(String name) {
        if(name.length() > 8) {
            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
            nameError.setTitle("Error");
            nameError.setHeaderText(null);
            nameError.setContentText("Invalid Name\nPlease enter a name with less than 8 characters");
            nameError.showAndWait();
            gameStart();
        }
    }
}
