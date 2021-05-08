package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.View;
import java.io.IOException;

public class BoulderdashBoard extends GridPane implements View {
    private Board board;
    private GameButton[][] buttons;

    public BoulderdashBoard(Board board) throws IOException {
        this.board = board;
        this.board.setView(this);
        createButtonGrid();
    }

    public void createButtonGrid() {
        this.buttons = new GameButton[this.board.getnCol()][this.board.getnLine()];
        ButtonHandler handler = new ButtonHandler();
        for(int line = 0; line < this.board.getnLine(); line++){
            for(int col = 0; col < this.board.getnCol(); col++){
                GameButton gameButton = new GameButton(this.board.getEntity(line, col));
                gameButton.setOnAction(handler);
                add(gameButton, col, line);
                buttons[col][line] = gameButton;
            }
        }
    }

    @Override
    public void rockfordMovedRight(AbstractPosition rockford, AbstractPosition entity) {
        this.buttons[entity.getCol() - 1][entity.getLine()].setButtonImage(entity);
        this.buttons[rockford.getCol()][rockford.getLine()].setButtonImage(rockford);


    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            GameButton source = (GameButton) event.getSource();
            int row = GridPane.getRowIndex(source);
            int col = GridPane.getColumnIndex(source);

            board.rockfordMoveRight();//TODO
        }
    }
}
