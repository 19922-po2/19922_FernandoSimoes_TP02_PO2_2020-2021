package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Fernando Simões nº 19922
 * Model of the game, contains the logic part of the program.
 */
public class Board {
    private final int MAX_NUMBER_LEVELS = 3;
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
    private List<Enemy> enemyList;
    private int currentLvl = 1;
    private int endGame;
    private Timer timer;
    private int timerValue;
    private String playerName;

    public Board(String mapFile) {
        this.timer = new Timer();
        this.score = 0;
        this.endGame = 0;
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.rockList = new ArrayList<Rock>();
        this.enemyList = new ArrayList<Enemy>();
        this.board = createBoard(mapFile);
        printBoard();
    }

    /**
     * Triggers events according to the player's movement.
     *
     * @param line destination line.
     * @param col  destination column.
     */
    public void triggerEvents(int line, int col) {
        int points = this.board[line][col].increaseScore();
        triggerScore(points);
        if (points > 0) {
            removeDiamond(this.board[line][col]);
        }
        triggerEntityFall();
        triggerEnemyMovement();
        movementScorePenalty();
    }

    /**
     * Triggers the falling of objects in the game.
     */
    private void triggerEntityFall() {
        for (Rock rock : rockList) {
            rock.moveEntity(this.board, this.nLine, this.nCol, view);
        }
        for (Diamond diamond : diamondList) {
            diamond.moveEntity(this.board, this.nLine, this.nCol, view);
        }
    }

    /**
     * Triggers the enemy movement.
     * Checks if rockford is in the way, if it is rockford loses a life.
     */
    private void triggerEnemyMovement() {
        for (Enemy enemy : enemyList) {
            enemy.moveEntity(this.board, this.nLine, this.nCol, view);
        }
    }

    /**
     * Checks in rockford and enemy are in the same spot
     */
    public void checkEnemyCollision() {
        for (Enemy enemy : enemyList) {
            if (enemy.getLine() == this.rockford.getLine() && enemy.getCol() == this.rockford.getCol()) {
                this.rockford.setRockfordLives(this.rockford.getRockfordLives() - 1);
                this.view.setRockfordLivesCount();

                if (this.rockford.getRockfordLives() == 0) {
                    this.view.gameOver(this.score);
                    updateHighScoresFile();
                    resetGame("src/resources/map_1.txt");
                } else {
                    this.view.rockfordDied();
                    resetBoard("src/resources/map_" + currentLvl + ".txt");
                }
            }
        }
    }

    /**
     * Decreased the player score by 5 points each time it moves.
     */
    private void movementScorePenalty() {
        this.score -= 5;
        this.view.setGameScore();
    }

    /**
     * Updates the score.
     *
     * @param points points to add to the score.
     */
    private void triggerScore(int points) {
        if (points > 0) nDiamonds--;
        checkDiamondCount();
        this.score += points;
        this.view.setDiamondCount();
    }

    /**
     * Removes a diamond from the game after the player obtains it.
     *
     * @param diamond diamond to be removed from the list.
     */
    private void removeDiamond(AbstractPosition diamond) {
        diamondList.removeIf(d -> d.equals(diamond));
    }

    /**
     * Checks the amount of diamonds in the board.
     * If the number of diamonds is 0, the gate spawns.
     */
    private void checkDiamondCount() {
        if (nDiamonds == 0) {
            board[this.gate.getLine()][this.gate.getCol()] = new Gate(this.gate.getLine(), this.gate.getCol());
            nGates = 1;
            this.view.gateAppeared(this.gate);
        }
    }

    /**
     * Checks if the player won the game.
     * The game is over when all the levels are completed.
     * Updates and shows top 5 high scores.
     */
    public void checkWin() {
        if (nGates == 1 && this.gate.getLine() == this.rockford.getLine() && this.gate.getCol() == this.rockford.getCol()) {

            this.view.lvlWon(this.score);

            if (currentLvl < MAX_NUMBER_LEVELS) {
                currentLvl++;
                resetBoard("src/resources/map_" + currentLvl + ".txt");
                this.view.setDiamondCount();
                this.resetTimer();
                this.view.timerRefresh(timerValue);
            } else {
                this.endGame = 1;
                updateHighScoresFile(); //updates high scores file
                List<Score> nextHighScore = readHighScores(); //get the top 5
                Score score = new Score(this.playerName, String.valueOf(currentLvl), String.valueOf(this.score));
                if (nextHighScore.contains(score)) {
                    //System.out.println("FOUND: " + nextHighScore.indexOf(score));
                    nextHighScore.get(nextHighScore.indexOf(score)).setScore(score.getScore() + "***");
                }
                this.view.showScores(nextHighScore);
                this.view.gameOver(this.score);
            }
        }
    }

    /**
     * Reads high scores from text file. (format: Name Level Score)
     *
     * @return list containing the sorted top 5 scores.
     */
    private List<Score> readHighScores() {
        String date = "scores" + LocalDate.now().toString().replace("-", "") + ".txt";
        List<Score> highScores = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(date));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(" ");
            }
            highScores = new ArrayList<>();
            for (int k = 0; k < allData.length; k++) {
                highScores.add(new Score(allData[k][0], allData[k][1], allData[k][2]));
            }
            ScoreComparator scoreComparator = new ScoreComparator();
            Collections.sort(highScores, scoreComparator);
        } catch (IOException e) {
            String errorMessage = "Error reading file " + date;
            view.showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString());
        }
        highScores.subList(5, highScores.size()).clear(); // removes all except the first top 5
        System.out.println(highScores);
        return highScores;
    }

    /**
     * Updates the high scores file. (scoresAAAAMMDD.txt)
     * Name/Level/Score
     */
    private void updateHighScoresFile() {
        String date = "scores" + LocalDate.now().toString().replace("-", "") + ".txt";
        File highScores = new File(date);
        try {
            FileWriter myWriter = new FileWriter(highScores, true);
            myWriter.write(this.playerName + " " + this.currentLvl + " " + this.score + "\n");
            myWriter.close();
        } catch (IOException e) { //sends IO exception to the view
            String errorMessage = "Error creating file " + date;
            view.showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString());
        }
    }

    /**
     * Resets the game board.
     *
     * @param mapFile file containing the map information.
     */
    public void resetBoard(String mapFile) {
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.rockList = new ArrayList<Rock>();
        this.enemyList = new ArrayList<Enemy>();
        this.board = createBoard(mapFile);
        this.view.resetBoard(this);
    }

    /**
     * Resets the game back to the initial level.
     *
     * @param mapFile file containing the map information.
     */
    public void resetGame(String mapFile) {
        this.nGates = 0;
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.rockList = new ArrayList<Rock>();
        this.enemyList = new ArrayList<Enemy>();
        this.board = createBoard(mapFile);
        this.score = 0;
        this.currentLvl = 1;
        this.rockford.setRockfordLives(5);
        this.view.setGameScore();
        this.view.setRockfordLivesCount();
        this.view.setDiamondCount();
        this.view.resetBoard(this);
        this.resetTimer();
    }

    /**
     * Creates an array of arrays containing the games objects.
     *
     * @param mapFile file containing the map information.
     * @return arrays of arrays containing the games objects.
     */
    public AbstractPosition[][] createBoard(String mapFile) {
        this.nDiamonds = 0;
        String[][] linesArray = readFileToStringArray2D(mapFile, " ");
        this.nLine = Integer.parseInt(linesArray[0][0]);
        this.nCol = Integer.parseInt(linesArray[0][1]);
        AbstractPosition[][] board = new AbstractPosition[nLine][nCol];
        fillMap(nLine, nCol, linesArray, board);
        populate(nLine, linesArray, board);

        return board;
    }

    /**
     * Creates a rockford object or changes its position if it already exists, because rockford is a singleton.
     * In the map file, the "%" is always at position (3 + c * 2)
     *
     * @param counter    current counter of the column reading.
     * @param i          current line of the lines array.
     * @param linesArray arrays containing the information read from the map file.
     * @param board      array of arrays with the game's information.
     */
    private void createRockford(int counter, int i, String[][] linesArray, AbstractPosition[][] board) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        if (this.rockford == null) {
            this.rockford = Rockford.getInstance(line, col);
        } else {
            this.rockford.setLine(line);
            this.rockford.setCol(col);
        }
        board[line][col] = this.rockford;
    }

    /**
     * Creates a diamond object and adds it to a list of diamonds.
     * In the map file, the "%" is always at position (3 + c * 2)
     *
     * @param counter    current counter of the column reading.
     * @param i          current line of the lines array.
     * @param linesArray arrays containing the information read from the map file.
     * @param board      array of arrays with the game's information.
     */
    private void createDiamond(int counter, int i, String[][] linesArray, AbstractPosition[][] board) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        board[line][col] = new Diamond(line, col);
        this.diamondList.add((Diamond) board[line][col]); //saves diamonds in list
        this.nDiamonds++;
    }

    /**
     * Creates a Gate object.
     *
     * @param counter    current counter of the column reading.
     * @param i          current line of the lines array.
     * @param linesArray arrays containing the information read from the map file.
     */
    private void createGate(int counter, int i, String[][] linesArray) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        this.gate = new Gate(line, col);
    }

    /**
     * Creates an enemy object and adds it to a list of enemies.
     * In the map file, the "%" is always at position (3 + c * 2)
     *
     * @param counter    current counter of the column reading.
     * @param i          current line of the lines array.
     * @param linesArray arrays containing the information read from the map file.
     * @param board      array of arrays with the game's information.
     */
    private void createEnemy(int counter, int i, String[][] linesArray, AbstractPosition[][] board) {
        int line = Integer.parseInt(linesArray[i][3 + counter - 2]);
        int col = Integer.parseInt(linesArray[i][3 + counter - 1]);
        board[line][col] = new Enemy(line, col);
        this.enemyList.add((Enemy) board[line][col]); //saves enemy on list
    }

    /**
     * Populates the board with various entities.
     * Ignores the comments (everything after the '%' character).
     * The "%" is always at position 3 + c*2.
     *
     * @param nLines     number of lines.
     * @param linesArray arrays containing the information read from the map file.
     * @param board      array of arrays with the game's information.
     */
    private void populate(int nLines, String[][] linesArray, AbstractPosition[][] board) {
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
                case "I":
                    while (!linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createEnemy(counter, i, linesArray, board);
                        counter += 2;
                    }
                    if (linesArray[i][3 + counter].equals("%") && counter < linesArray[i].length) {
                        createEnemy(counter, i, linesArray, board);
                    }
                    break;
            }
            counter = 0;
        }
    }

    /**
     * Creates a map of the game.
     *
     * @param nLines     number of lines.
     * @param nCols      number of columns.
     * @param linesArray arrays containing the information read from the map file.
     * @param board      array of arrays with the game's information.
     */
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

    /**
     * Read a file line by line and saves it, into a 2D array, where each word is saved into a position.
     *
     * @param filename  name of the file to be read.
     * @param separator character to separate the lines.
     * @return array of arrays containing the information read from the file.
     */
    public String[][] readFileToStringArray2D(String filename, String separator) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separator);
            }
            return allData;
        } catch (IOException e) { //sends IO exception to the view
            String errorMessage = "Error reading file " + filename;
            view.showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString());
            return new String[0][];
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getnDiamonds() {
        return this.nDiamonds;
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

    public int getCurrentLvl() {
        return currentLvl;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Prints the board to the command line.
     */
    public void printBoard() {
        for (int i = 0; i < nLine; i++) {
            for (int j = 0; j < nCol; j++) {
                this.board[i][j].print();
            }
            System.out.println();
        }
    }

    /**
     * Creates a new timer and sets the timer count to zero
     * Code from fifteen game (Moodle).
     */
    public void resetTimer() {
        this.timerValue = -1;
        this.timer = new Timer();
    }

    /**
     * Starts timer
     * Code from fifteen game (Moodle).
     */
    public void startTimer() {
        this.resetTimer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timerValue++;
                view.timerRefresh(timerValue);
            }
        };
        this.timer.schedule(timerTask, 0, 1000);
    }

    /**
     * Stops the current timer
     * Code from fifteen game (Moodle).
     */
    public void stopTimer() {
        timer.cancel();
    }

    /**
     * Get current timer value
     * Code from fifteen game (Moodle).
     *
     * @return time in seconds
     */
    public int getTimerValue() {
        return this.timerValue;
    }
}
