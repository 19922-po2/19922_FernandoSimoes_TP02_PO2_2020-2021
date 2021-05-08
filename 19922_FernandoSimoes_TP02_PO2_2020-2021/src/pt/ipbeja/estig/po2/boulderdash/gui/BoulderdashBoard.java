package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.layout.VBox;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.View;
import java.io.IOException;


public class BoulderdashBoard extends VBox implements View {
    private Board board;
    private String mapFile = "map_1.txt";

    public BoulderdashBoard() throws IOException {
        this.board = new Board(mapFile);
    }

}
