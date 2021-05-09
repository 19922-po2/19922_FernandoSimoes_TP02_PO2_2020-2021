import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.gui.BoulderdashBoard;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.Diamond;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private String mapFile = "src/resources/map_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        this.board = new Board(this.mapFile);
    }

    @Test
    /**
     * Tests rockford movement to a free position.
     * The positions of the two objects swamp.
     */
    void test1(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to free tunnel
        this.board.rockfordGoTo(previousLine + 1,previousCol +1);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        //verifies position after movement
        assertEquals(previousLine + 1, nextLine);
        assertEquals(previousCol +1, nextCol);
    }

    @Test
    /**
     * Tests rockford movement to a position with a Wall.
     * The positions of the two objects stay the same.
     */
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
    /**
     * Tests rockford movement to a position with a Rock.
     * The positions of the two objects stay the same.
     */
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
    /**
     * Tests rockford movement to a position with a Diamond.
     * The positions of the two objects swamp.
     * The score increases.
     */
    void test4(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to Diamond
        this.board.rockfordGoTo(previousLine,previousCol + 3);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol + 3, nextCol);
        //verifies score (diamond = 100 points)
        assertEquals(previousScore + 100, nextScore);
    }

    @Test
    /**
     * Tests rockford movement to a position that triggers Diamond fall.
     * The positions of the Diamond changes accordingly.
     */
    void test5(){
        //gets the diamond to check positions
        Diamond diamond =this.board.getDiamondList().get(0);
        //starting rockford position
        int previousDiamondLine = diamond.getLine();
        int previousDiamondCol = diamond.getCol();
        //movement to occupied tunnel
        this.board.rockfordGoTo(2,5);
        this.board.rockfordGoTo(2,6);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextDiamondLine = diamond.getLine();
        int nextDiamondCol = diamond.getCol();
        //verifies position after movement
        assertEquals(2, nextLine);
        assertEquals(6, nextCol);
        //verifies diamond position
        assertEquals(previousDiamondLine + 1, nextDiamondLine);
        assertEquals(previousDiamondCol, nextDiamondCol);
    }

    @Test
    /**
     * Tests rockford movement to a Gate.
     * The game ends.
     */
    void test6(){
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to diamond to trigger the gate
        this.board.rockfordGoTo(previousLine,previousCol + 3);
        //movement to gate
        this.board.rockfordGoTo(previousLine,previousCol + 10);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol + 10, nextCol);
        //verifies score (diamond = 100 points)
        assertEquals(previousScore + 100, nextScore);
        //verifies end game
        assertEquals(1, this.board.getEndLvl());
    }
}