package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.View;

/**
 * @author Fernando Simões nº 19922
 */

public class BoulderdashBoard extends GridPane implements View {
    private Board board;
    private GameButton[][] buttons;
    private Stage myStage;

    public BoulderdashBoard(Board board, Stage myStage) {
        this.board = board;
        this.board.setView(this);
        createButtonGrid();
        this.myStage = myStage;
        gameStart();

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

    public void createButtonGrid() {
        this.buttons = new GameButton[this.board.getnCol()][this.board.getnLine()];
        ButtonHandler handler = new ButtonHandler();
        for (int line = 0; line < this.board.getnLine(); line++) {
            for (int col = 0; col < this.board.getnCol(); col++) {
                GameButton gameButton = new GameButton(this.board.getEntity(line, col));
                gameButton.setOnAction(handler);
                add(gameButton, col, line);
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
    public void gameWon(int score) {
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("GAME WON!");
        gameWonAlert.setHeaderText("Final score: " + score);
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

    class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            GameButton source = (GameButton) event.getSource();
        }
    }
}
