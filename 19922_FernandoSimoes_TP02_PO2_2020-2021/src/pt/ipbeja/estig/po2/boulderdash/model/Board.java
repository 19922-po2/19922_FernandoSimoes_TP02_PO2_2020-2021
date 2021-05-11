package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Simões nº 19922
 */

public class Board {
    private int nLine;
    private int nCol;
    private int score;
    private int nDiamonds;
    private int nGates;
    private Rockford rockford;
    private AbstractPosition[][] board;
    private View view;
    private Gate gate;
    private List<Diamond> diamondList;
    private List<Rock> rockList;
    private int endLvl = 0;

    public Board(String mapFile) {
        this.score = 0;
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.rockList = new ArrayList<Rock>();
        this.board = createBoard(mapFile);
    }

    public void triggerUp() {
        int points = this.board[this.rockford.getLine() - 1][this.rockford.getCol()].increaseScore();
        triggerScore(points);
        if (points > 0) {
            removeDiamond(this.board[this.rockford.getLine() - 1][this.rockford.getCol()]);
        }
        for (Rock rock : rockList) {
            rock.triggerRockFall(this.board, this.nLine, view);
        }
        for (Diamond diamond : diamondList) {
            diamond.triggerDiamondFall(this.board, this.nLine, view);
        }

    }

    public void triggerDown() {
        int points = this.board[this.rockford.getLine() + 1][this.rockford.getCol()].increaseScore();
        triggerScore(points);
        if (points > 0) {
            removeDiamond(this.board[this.rockford.getLine() + 1][this.rockford.getCol()]);
        }
        for (Rock rock : rockList) {
            rock.triggerRockFall(this.board, this.nLine, view);
        }
        for (Diamond diamond : diamondList) {
            diamond.triggerDiamondFall(this.board, this.nLine, view);
        }
    }

    //public-private
    public void triggerRight() {
        int points = this.board[this.rockford.getLine()][this.rockford.getCol() + 1].increaseScore();
        triggerScore(points);
        if (points > 0) {
            removeDiamond(this.board[this.rockford.getLine()][this.rockford.getCol() + 1]);
        }
        for (Rock rock : rockList) {
            rock.triggerRockFall(this.board, this.nLine, view);
        }
        for (Diamond diamond : diamondList) {
            diamond.triggerDiamondFall(this.board, this.nLine, view);
        }
    }

    public void triggerLeft() {
        int points = this.board[this.rockford.getLine()][this.rockford.getCol() - 1].increaseScore();
        triggerScore(points);
        if (points > 0) {
            removeDiamond(this.board[this.rockford.getLine()][this.rockford.getCol() - 1]);
        }
        for (Rock rock : rockList) {
            rock.triggerRockFall(this.board, this.nLine, view);
        }
        for (Diamond diamond : diamondList) {
            diamond.triggerDiamondFall(this.board, this.nLine, view);
        }
    }

    private void triggerScore(int points) {
        if (points > 0) nDiamonds--;
        checkDiamondCount();
        this.score += points;
    }

    private void removeDiamond(AbstractPosition fallingObject) {
        diamondList.removeIf(diamond -> diamond.equals(fallingObject));
    }

    private void checkDiamondCount() {
        if (nDiamonds == 0) {
            board[this.gate.getLine()][this.gate.getCol()] = new Gate(this.gate.getLine(), this.gate.getCol());
            nGates = 1;
            this.view.gateAppeared(this.gate);
        }
    }

    //public->private
    public void checkWin() {
        if (nGates == 1 && this.gate.getLine() == this.rockford.getLine() && this.gate.getCol() == this.rockford.getCol()) {
            this.view.gameWon(this.score);
            this.endLvl = 1;
            //TODO reset board after a win
            resetBoard("src/resources/map_2.txt");
        }
    }

    public void resetBoard(String mapFile) {    // TODO test reset
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.rockList = new ArrayList<Rock>();
        this.board = createBoard(mapFile);
        this.view.resetBoard(this);
    }

    public AbstractPosition[][] createBoard(String mapFile) {
        String[][] linesArray = readFileToStringArray2D(mapFile, " ");
        this.nLine = Integer.parseInt(linesArray[0][0]);
        this.nCol = Integer.parseInt(linesArray[0][1]);
        AbstractPosition[][] board = new AbstractPosition[nLine][nCol];
        fillMap(nLine, nCol, linesArray, board);
        populate(nLine, linesArray, board);

        return board;
    }

    private void createRockford(int counter, int i, String[][] linesArray, AbstractPosition[][] board) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        this.rockford = new Rockford(line, col);
        board[line][col] = this.rockford;
    }

    private void createDiamond(int counter, int i, String[][] linesArray, AbstractPosition[][] board) {
        // the "%" is always at position 3 + c*2
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        board[line][col] = new Diamond(line, col);
        this.diamondList.add((Diamond) board[line][col]); //saves diamonds in list
        this.nDiamonds++;
    }

    private void createGate(int counter, int i, String[][] linesArray) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        this.gate = new Gate(line, col);
    }

    private void populate(int nLines, String[][] linesArray, AbstractPosition[][] board) {
        // the "%" is always at position 3 + c*2
        int counter = 0;
        for (int i = nLines; i < linesArray.length; i++) {
            switch (linesArray[i][0]) {
                case "J":
                    while (!linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createRockford(counter, i, linesArray, board);
                        counter += 2;
                    }
                    if (linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createRockford(counter, i, linesArray, board);
                    }
                    break;
                case "D":
                    while (!linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createDiamond(counter, i, linesArray, board);
                        counter += 2;
                    }
                    if (linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createDiamond(counter, i, linesArray, board);
                    }
                    break;
                case "G":
                    while (!linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createGate(counter, i, linesArray);
                        counter += 2;
                    }
                    if (linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createGate(counter, i, linesArray);
                    }
                    break;
            }
            counter = 0;
        }
    }

    private void fillMap(int nLines, int nCols, String[][] linesArray, AbstractPosition[][] board) {
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nCols; j++) {
                switch (linesArray[i + 1][0].charAt(j)) {
                    case 'W':
                        board[i][j] = new Wall(i, j);
                        break;
                    case 'O':
                        board[i][j] = new OccupiedTunnel(i, j);
                        break;
                    case 'L':
                        board[i][j] = new FreeTunnel(i, j);
                        break;
                    case 'P':
                        board[i][j] = new Rock(i, j);
                        this.rockList.add((Rock) board[i][j]); //saves Rock in list
                        break;
                }
            }
        }
    }

    public static String[][] readFileToStringArray2D(String filename, String separator) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separator);
            }
            return allData;
        } catch (IOException e) {
            String errorMessage = "Error reading file " + filename;
            showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString());
            return new String[0][];
        }
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int getScore() {
        return this.score;
    }

    public Rockford getRockford() {
        return this.rockford;
    }

    public List<Diamond> getDiamondList() {
        return this.diamondList;
    }

    public int getnLine() {
        return nLine;
    }

    public int getnCol() {
        return nCol;
    }

    public int getEndLvl() {
        return this.endLvl;
    }

    public AbstractPosition[][] getBoard() {
        return board;
    }

    public AbstractPosition getEntity(int line, int col) {
        return this.board[line][col];
    }

    public void setView(View view) {
        this.view = view;
    }

    public void rockfordGoTo(int line, int col) {
        if (this.board[line][col].possibleMoveTo()) {
            //swap rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triggers
            int points = this.board[line][col].increaseScore();
            if (points > 0) nDiamonds--;
            if (nDiamonds == 0) {
                board[this.gate.getLine()][this.gate.getCol()] = new Gate(this.gate.getLine(), this.gate.getCol());
                nGates = 1;
            }
            this.score += points;
            for (Diamond diamond : diamondList) {
                if (diamond.getLine() + 1 < nLine && this.board[diamond.getLine() + 1][diamond.getCol()].canReceiveFallingObject()) {
                    this.board[diamond.getLine()][diamond.getCol()] = new FreeTunnel(diamond.getLine(), diamond.getCol());
                    diamond.setLine(diamond.getLine() + 1);
                    this.board[diamond.getLine()][diamond.getCol()] = diamond;
                }
            }
            //swaps target with rockford
            this.board[line][col] = this.rockford;
            this.rockford.setLine(line);
            this.rockford.setCol(col);
            //checks win
            if (nGates == 1 && this.gate.getLine() == this.rockford.getLine() && this.gate.getCol() == this.rockford.getCol()) {
                this.endLvl = 1;
            }
        }
    }
}
