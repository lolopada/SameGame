import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * The <code>GameFrame</code> class represents the main window of the SameGame game.
 * It manages the graphical interface, score display, game grid, and
 * allows exporting the current grid as a text file.
 *
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class GameFrame extends JFrame {
        
    /**
     * Main panel containing the game grid.
     */
    private JPanel gamePanel;
    /**
     * Top panel containing information such as score and export button.
     */
    private JPanel topPanel;
    /**
     * Panel containing only the score.
     */
    private JPanel scorePanel;
    /**
     * Label displaying the current score.
     */
    private JLabel scoreLabel;
    /**
     * Panel containing the export button.
     */
    private JPanel buttonPanel;
    /**
     * Button allowing export of the current grid.
     */
    private JButton importButton;
    /**
     * Current player's score.
     */
    private int score;
    /**
     * Grid of cases displayed on screen.
     */
    private GameCase[][] casesGrid;
    /**
     * Current representation of the grid in characters (R, V, B, E).
     */
    private static char[][] patternMapCurrentGame;
    /**
     * Game logic, used for group detection and grid management.
     */
    private GameLogic gameLogic;
    /**
     * Listener managing clicks on game cells.
     */
    private GameListener gameListener;

    private final int SCORE_PANEL_HEIGHT = 55;
    private final int CASE_SIZE = 70;
    private final int NB_COLS = 15;
    private final int NB_ROWS = 10;

    /**
     * Default constructor, creates a random grid
     */
    public GameFrame() {
        this.casesGrid = new GameCase[this.NB_ROWS][this.NB_COLS];
        this.gameLogic = new GameLogic(this.casesGrid, this.NB_ROWS, this.NB_COLS);
        this.gameListener = new GameListener(this.gameLogic,this);

        this.setFrame();

        this.setRandomGameGrid();
        this.patternMapCurrentGame = getCurrentMap();
    }

    /**
     * Constructor with a defined map.
     * @param map predefined grid containing integers representing colors
     */
    public GameFrame(int[][] map) {
        this.casesGrid = new GameCase[this.NB_ROWS][this.NB_COLS];
        this.gameLogic = new GameLogic(this.casesGrid, this.NB_ROWS, this.NB_COLS);
        this.gameListener = new GameListener(this.gameLogic,this);

        this.setFrame();

        this.setGridFromMap(map);
        this.patternMapCurrentGame = getCurrentMap();
    }

    /**
     * Initializes the window with graphical components (grid, score, button...).
     */
    public void setFrame() {
        this.setTitle("SameGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.NB_COLS * this.CASE_SIZE, this.NB_ROWS * this.CASE_SIZE + this.SCORE_PANEL_HEIGHT);

        this.setLocationRelativeTo(null);
        
        // main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // top panel
        this.topPanel = new JPanel();
        this.topPanel.setPreferredSize(new Dimension(this.NB_COLS * this.CASE_SIZE, this.SCORE_PANEL_HEIGHT));
        this.topPanel.setLayout(new BorderLayout());
        
        // score 
        this.scorePanel = new JPanel();
        this.scorePanel.setBackground(new Color(210, 210, 210));
        this.scoreLabel = new JLabel("Score: " + this.score);
        this.scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        this.scorePanel.add(this.scoreLabel);
        this.topPanel.add(this.scorePanel, BorderLayout.CENTER);

        this.buttonPanel = new JPanel(new GridBagLayout());
        this.buttonPanel.setPreferredSize(new Dimension((int)(this.NB_COLS * this.CASE_SIZE * 0.3), this.SCORE_PANEL_HEIGHT)); // 30% of topPanel
        this.buttonPanel.setBackground(new Color(210, 210, 210));
        this.importButton = new JButton("export current map");
        buttonPanel.add(this.importButton);
        this.topPanel.add(buttonPanel, BorderLayout.EAST);

        
        ExportButtonListener exportListener = new ExportButtonListener(this);
        this.importButton.addActionListener(exportListener);
        
        // game panel
        this.gamePanel = new JPanel(new GridLayout(this.NB_ROWS, this.NB_COLS));
        

        contentPanel.add(this.topPanel, BorderLayout.NORTH);
        contentPanel.add(this.gamePanel, BorderLayout.CENTER);
        this.add(contentPanel);
    }

    /**
     * Generates a random game grid.
     */
    public void setRandomGameGrid() {
        for (int row = 0; row < this.NB_ROWS; row++) {
            for (int col = 0; col < this.NB_COLS; col++) {
                GameCase newGameCase = new GameCase(col, row);
                newGameCase.addMouseListener(this.gameListener);
                
                this.casesGrid[row][col] = newGameCase;
                this.gamePanel.add(newGameCase);
            }
        }
        this.gameLogic.updateGroupCaseByID();
    }

    /**
     * Fills the grid with a given map.
     * @param map Map defining colors for each cell.
     */
    public void setGridFromMap(int[][] map) {
        for (int row = 0; row < this.NB_ROWS; row++) {
            for (int col = 0; col < this.NB_COLS; col++) {
                GameCase newGameCase = new GameCase(col, row, map[row][col]);
                newGameCase.addMouseListener(this.gameListener);
                
                this.casesGrid[row][col] = newGameCase;
                this.gamePanel.add(newGameCase);
            }
        }
        this.gameLogic.updateGroupCaseByID();
    }

    /**
     * Modifies the player's score based on the number of deleted cells.
     * @param nbrCase number of cells deleted in a single move
     */
    public void updateScore(int nbrCase) {
        int points;
        if (nbrCase > 2) {
            points = (nbrCase - 2) * (nbrCase - 2);
        } else {
            points = 0;
        }
        this.score += points;
        this.scoreLabel.setText("Score: " + this.score);
    }

    /**
     * Returns the current score.
     * @return player's score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the current map of the grid as an array of characters.
     * R = red, V = green, B = blue, E = empty.
     * @return 2D character array representing the current state of the grid
     */
    public char[][] getCurrentMap() {
        char[][] map = new char[this.NB_ROWS][this.NB_COLS];
        
        for (int row = 0; row < this.NB_ROWS; row++) {
            for (int col = 0; col < this.NB_COLS; col++) {
                GameCase currentCase = this.gameLogic.getCaseAt(col, row);
                if (currentCase != null) {
                    int gemType = currentCase.getGemType();
                    
                    if (gemType == 0) {
                        map[row][col] = 'R';
                    } else if (gemType == 1) {
                        map[row][col] = 'V';
                    } else if (gemType == 2) {
                        map[row][col] = 'B';
                    } else {
                        map[row][col] = 'E';
                    }
                } else {
                    map[row][col] = 'E';
                }
            }
        }
        
        return map;
    }

    /**
     * Returns the last saved map.
     * @return patternMapCurrentGame
     */
    public char[][] getPatternMapCurrentGame() {
        return this.patternMapCurrentGame;
    }
    
    /**
     * Exports the current map to a given text file.
     * @param exportFile path of the file to which to export the grid
     */
    public void writePattern(String exportFile) {
        char[][] patternMapCurrentGame = getPatternMapCurrentGame();
        
        try {
            FileWriter writer = new FileWriter(exportFile);

            try {
                for (int i = 0; i < patternMapCurrentGame.length; i++) {
                    for (int j = 0; j < patternMapCurrentGame[i].length; j++) {
                        writer.write(patternMapCurrentGame[i][j]);
                    }
                writer.write('\n');
                }

                try {
                    writer.close();
                    PopUpFrame ExportFrame = new PopUpFrame("Export file", "Pattern export at ~/exportPattern.txt");
                    ExportFrame.setVisible(true);
                } catch (IOException ex) {
                    System.out.println("err close export file");
                }
                
            } catch (IOException ex) {
                System.out.println("err write pattern file");
            }

        } catch (IOException ex) {
            System.out.println("err open pattern file");
        }
    }

    /**
     * Returns the game panel (grid).
     * @return gamePanel
     */
    public JPanel getGamePanel() {
        return this.gamePanel;
    }

    /**
     * Closes the game window.
     */
    public void closeGameFrame() {
        this.dispose();
    }

    /**
     * Displays a pop-up window at the end of the game with the final score and the number of remaining cells.
     */
    public void closeGame() {
        String message = "score: " + getScore() + ", remaining case : " + this.gameLogic.getCptCasesAvailable();
        PopUpFrame end = new PopUpFrame("Game over", message, this);
        end.setVisible(true);
    }

}