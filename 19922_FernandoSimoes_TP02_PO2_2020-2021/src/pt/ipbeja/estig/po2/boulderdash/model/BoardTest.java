package pt.ipbeja.estig.po2.boulderdash.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private String mapFile = "map_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        this.board = new Board(mapFile);
        this.board.printMap();
        //this.rockford = board.getRockford();
        System.out.println("^SETUP^");
    }

    @Test
    void testFreeTunnelMovement(){
        //previous rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to free position
        this.board.rockfordMoveRight();
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        this.board.printMap();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol + 1, nextCol);
    }

    @Test
    void testWallMovement() {
        //previous rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement
        this.board.rockfordMoveUp();
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        this.board.printMap();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol, nextCol);
    }

    @Test
    void testRockMovement(){
        //previous rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement (once to a free spot and once into a rock)
        this.board.rockfordMoveLeft();
        this.board.rockfordMoveLeft();
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        this.board.printMap();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol -1, nextCol);
    }

    @Test
    void testDiamondMovement(){
        //previous rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement (once to a free spot and once into a rock)
        this.board.rockfordMoveRight();
        this.board.rockfordMoveRight();
        this.board.rockfordMoveRight();
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        this.board.printMap();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol +3, nextCol);
        assertEquals(previousScore + 10, nextScore);
    }
    @Test
    void testDiamondFall(){
        //TODO
    }
}