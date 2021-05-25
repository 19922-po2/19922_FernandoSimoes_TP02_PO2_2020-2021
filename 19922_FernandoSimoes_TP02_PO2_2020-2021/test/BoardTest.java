import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.model.*;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private View view;

    @BeforeEach
    void setUp() {
        String mapFile = "src/resources/map_test.txt";
        this.board = new Board(mapFile);
        this.view = new DummyView();    // dummy view for model testing
        this.board.setView(this.view);
        board.createBoard(mapFile);
    }

    @Test
    /**
     * Tests rockford movement to a free position.
     * The positions of the two objects swamp.
     */
    void testA() {
        System.out.println("TEST A");
        this.board.printBoard();
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to free tunnel
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,1, 1); // line+1, col+1
        System.out.println("MOVE");
        this.board.printBoard();
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
        System.out.println("TEST B");
        this.board.printBoard();
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to wall
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,-1, 0); // line-1, col
        System.out.println("MOVE");
        this.board.printBoard();
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
        System.out.println("TEST C");
        this.board.printBoard();
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        //movement to Rock
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,0, -2); // line, col-2
        System.out.println("MOVE");
        this.board.printBoard();
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
        System.out.println("TEST D");
        this.board.printBoard();
        //starting rockford position
        int previousLine = this.board.getRockford().getLine();
        int previousCol = this.board.getRockford().getCol();
        int previousScore = this.board.getScore();
        //movement to Diamond
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,0, +3); // line, col+3
        System.out.println("MOVE");
        this.board.printBoard();
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        assertEquals(previousCol + 3, nextCol);
        //verifies score (diamond = 100 points)
        assertEquals(previousScore + 100 - 5, nextScore); //expected +100 from diamond and -5 from move penalty
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
        //movement to occupied tunnel with a diamond above, then movement to the adjacent tile to make the diamond fall
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,1, 3); // line+1, col+3
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,0, 1); // line, col+1
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
        this.board.printBoard();
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
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,0, +3); // line, col+3
        //movement to gate
        this.board.getRockford().rockfordMove(this.board, this.board.getnLine(), this.board.getnCol(), this.view,0, +7); // line, col+7
        //next rockford position
        int nextLine = this.board.getRockford().getLine();
        int nextCol = this.board.getRockford().getCol();
        int nextScore = this.board.getScore();
        //verifies position after movement
        assertEquals(previousLine, nextLine);
        //assertEquals(previousCol + 10, nextCol);
        //verifies score (diamond = 100 points, movement = -5 points)
        assertEquals(previousScore + 100 - 5 - 5, nextScore);
        //verifies end game
        this.board.printBoard();
        assertEquals(2, this.board.getCurrentLvl());
    }
}