package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public BoulderdashBoard(Board board, Stage myStage) {

        this.gameBoard = new GridPane();
        this.board = board;
        this.board.setView(this);
        createButtonGrid();
        this.myStage = myStage;

        //game info (left side VBox)
        this.gameInfoVBOX = new VBox();
        this.rockfordLivesLabel = new Label("Rockford Lives: " + this.board.getRockford().getRockfordLives());
        this.gameScoreLabel = new Label("Score: " + this.board.getScore());
        this.currentDiamondCountLabel = new Label("Diamonds: " + this.board.getnDiamonds());
        this.giveUpLevelButton = new Button("Give Up\n(Restart Lvl)");
        giveUpLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.resetGame("src/resources/map_1.txt");
            }
        });
        this.gameInfoVBOX.getChildren().addAll(rockfordLivesLabel, gameScoreLabel, currentDiamondCountLabel, giveUpLevelButton);
        this.getChildren().addAll(gameInfoVBOX, gameBoard);
        gameStart(); //shows movement keys before game starts

        this.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case W:
                            this.board.getRockford().rockfordMoveUp(this.board, this.board.getnLine(), this);
                            break;
                        case S:
                            this.board.getRockford().rockfordMoveDown(this.board, this.board.getnLine(), this);
                            break;
                        case A:
                            this.board.getRockford().rockfordMoveLeft(this.board, this.board.getnCol(), this);
                            break;
                        case D:
                            this.board.getRockford().rockfordMoveRight(this.board, this.board.getnCol(), this);
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
        gameWonAlert.setHeaderText("Score: " + score);
        gameWonAlert.showAndWait();
    }

    @Override
    public void resetBoard(Board board) {      // TODO test "myStage", doesn't scale properly
        this.board = board;
        this.board.setView(this);
        createButtonGrid();
        this.myStage.sizeToScene();
    }

    public void gameStart() {
        Alert gameStart = new Alert(Alert.AlertType.INFORMATION);
        gameStart.setTitle("BoulderDash Game");
        gameStart.setHeaderText("Movement Keys:");
        gameStart.setContentText("W - Move Up\nA - Move Left\nS - Move Down\nD - Move Right");
        gameStart.showAndWait();
    }
}
