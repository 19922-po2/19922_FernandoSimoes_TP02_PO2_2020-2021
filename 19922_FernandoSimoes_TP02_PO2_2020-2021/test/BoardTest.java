import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.Diamond;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private AbstractPosition[][] arrayBoard;

    @BeforeEach
    void setUp() {
        String mapFile = "src/resources/map_test.txt";
        this.board = new Board(mapFile);
        this.arrayBoard = board.createBoard(mapFile);
    }

    @Test
    /**
     * Tests rockford movement to a free position.
     * The positions of the two objects swamp.
     */
    void testA() {
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to free tunnel
        this.board.getRockford().rockfordGoTo(previousLine + 1, previousCol + 1, this.board);
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        //verifies position after movement
        assertEquals(previousLine + 1, nextLine);
        assertEquals(previousCol + 1, nextCol);
    }

    @Test
    /**
     * Tests rockford movement to a position with a Wall.
     * The positions of the two objects stay the same.
     */
    void testB() {
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to wall
        this.board.getRockford().rockfordGoTo(2, 9, this.board);
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
    void testC() {
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to Rock
        this.board.getRockford().rockfordGoTo(0, 1, this.board);
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
    void testD() {
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to Diamond
        this.board.getRockford().rockfordGoTo(previousLine, previousCol + 3, this.board);
        //this.board.rockfordGoTo(1, 5);
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
    void testE() {
        //gets the diamond to check positions
        Diamond diamond = this.board.getDiamondList().get(0);
        //starting rockford position
        int previousDiamondLine = diamond.getLine();
        int previousDiamondCol = diamond.getCol();
        //movement to occupied tunnel
        this.board.getRockford().rockfordGoTo(2, 5, this.board);
        this.board.getRockford().rockfordGoTo(2, 6, this.board);
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
    void testF() {
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to diamond to trigger the gate
        this.board.getRockford().rockfordGoTo(previousLine, previousCol + 3, this.board);
        //movement to gate
        this.board.getRockford().rockfordGoTo(previousLine, previousCol + 10, this.board);
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