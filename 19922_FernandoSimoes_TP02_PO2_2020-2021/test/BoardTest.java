import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.gui.BoulderdashBoard;
import pt.ipbeja.estig.po2.boulderdash.model.Board;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private String mapFile = "src/resources/map_test.txt";
    private BoulderdashBoard boulderdashBoard;

    @BeforeEach
    void setUp() throws IOException {
        this.board = new Board(this.mapFile);
        this.board.printMap();
        //this.boulderdashBoard = new BoulderdashBoard(this.board);
        //this.board.setView(boulderdashBoard);
        //this.rockford = board.getRockford();
        System.out.println("^SETUP^");
    }

    @Test
    // Rockford tries to move to a free position
    void test1(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to free tunnel
        this.board.rockfordGoTo(2,6);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        //verifies position after movement
        assertEquals(2, nextLine);
        assertEquals(6, nextCol);
    }

    @Test
    // Rockford tries to move to a wall
    void test2(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to wall
        this.board.rockfordGoTo(2,9);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol, nextCol);
    }

    @Test
    // Rockford tries to move to a position with a Rock
    void test3(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to Rock
        this.board.rockfordGoTo(0,1);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol, nextCol);
    }

    @Test
    // Rockford tries to move to a position with a Diamond
    void test4(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to Diamond
        this.board.rockfordGoTo(1,5);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(1, nextLine);
        assertEquals(5, nextCol);
        //verifies score (diamond = 10 points)
        assertEquals(previousScore + 10, nextScore);
    }

    @Test
    // Rockford tries to move to a free position and trigger diamond fall
    void test5(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousDiamondLine = 1;
        int previousDiamondCol = 5;
        //movement to occupied tunnel
        this.board.rockfordGoTo(2,5);
        this.board.rockfordGoTo(3,5);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(3, nextLine);
        assertEquals(5, nextCol);
        //verifies diamond position
        //TODO diamond fall
        this.board.printMap();
    }

}