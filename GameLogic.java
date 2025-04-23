import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

/**
 * The <code>GameLogic</code> class contains the main logic of the SameGame.
 * It manages interactions with the grid, group detection,
 * column updates, and game end verification.
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class GameLogic {
    
    /** Number of columns in the grid */
    private final int NB_COLS;
    
    /** Number of rows in the grid */
    private final int NB_ROWS;
    
    /** Number of non-empty columns remaining */
    private int NbColumnsAvailable;
    
    /** Number of remaining cells on the grid */
    private int cptCasesAvailable;
    
    /** Grid containing GameCase objects */
    private GameCase[][] casesGrid;
    
    /** Array containing groups of adjacent cells of the same type */
    private GameCase[][] neighborGroups;

    /**
     * GameLogic constructor
     * 
     * @param casesGrid initial grid
     * @param NB_ROWS number of rows
     * @param NB_COLS number of columns
     */
    public GameLogic(GameCase[][] casesGrid, int NB_ROWS, int NB_COLS) {
        this.casesGrid = casesGrid;
        this.NB_ROWS = NB_ROWS;
        this.NB_COLS = NB_COLS;
        this.NbColumnsAvailable = NB_COLS;
        this.cptCasesAvailable = NB_ROWS * NB_COLS;
        this.neighborGroups = new GameCase[NB_ROWS * NB_COLS][];
    }

    /**
     * Returns all cells connected to a given cell by the same gem type.
     *
     * @param startCase Starting cell.
     * @return Array of connected cells.
     */
    public GameCase[] getNeighbors(GameCase startCase) {
        if (startCase == null) {
            return new GameCase[0];
        }
        
        int gemType = startCase.getGemType();

        if (gemType == 3) {
            return new GameCase[0];
        }
        
        GameCase[] connectedCases = new GameCase[NB_ROWS * NB_COLS]; // Maximum number of connected cells possible
        connectedCases[0] = startCase;
        int connectedcpt = 1;
 
        int currentIndex = 0;
        
        while (currentIndex < connectedcpt) {
            GameCase currentCase = connectedCases[currentIndex];
            
            GameCase[] directNeighbors = findDirectNeighbors(currentCase, gemType);
            
            for (int i = 0; i < directNeighbors.length; i++) {
                GameCase tempCase = directNeighbors[i];
                boolean inConnectedCases = false;
                
                for (int j = 0; j < connectedcpt; j++) {
                    if (connectedCases[j] == tempCase) {
                        inConnectedCases = true;
                    }
                }
                
                if (!inConnectedCases) {
                    connectedCases[connectedcpt] = tempCase;
                    connectedcpt += 1;
                }
            }
            currentIndex++;
        }
        
        GameCase[] finalConnectedCases = new GameCase[connectedcpt];
        for (int i = 0; i < connectedcpt; i++) {
            finalConnectedCases[i] = connectedCases[i];
        }
        return finalConnectedCases;
    }

    /**
     * Returns the direct neighbors (up, down, left, right) of the same type.
     *
     * @param caseJeu Starting cell.
     * @param Type    Type of gem being sought.
     * @return Array of direct neighbors.
     */
    public GameCase[] findDirectNeighbors(GameCase caseJeu, int Type) {
        GameCase[] tab = new GameCase[4];
        int cpt = 0;
        
        int x = caseJeu.getPositionX();
        int y = caseJeu.getPositionY();

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            
            if (newX >= 0 && newX < this.NB_COLS && newY >= 0 && newY < this.NB_ROWS) {
                GameCase tempCase = getCaseAt(newX, newY);
                if (tempCase != null && tempCase.getGemType() == Type) {
                    tab[cpt] = tempCase;
                    cpt += 1;
                }
            }
        }

        GameCase[] finalTab = new GameCase[cpt];
        for (int i = 0; i < cpt; i++) {
            finalTab[i] = tab[i];
        }
        return finalTab;
    }

    /**
     * Returns the cell located at the given coordinates.
     *
     * @param x Column.
     * @param y Row.
     * @return The cell or null if outside the grid.
     */
    public GameCase getCaseAt(int x, int y) {
        if (x >= 0 && x < this.NB_COLS && y >= 0 && y < this.NB_ROWS) {
            return casesGrid[y][x];
        }
        return null;
    }
    
    /**
     * Exchanges the gems between two cells.
     *
     * @param source Source cell.
     * @param target Target cell.
     */
    public void moveCase(GameCase source, GameCase target) {
        int sourceGemType = source.getGemType();
        int targetGemType = target.getGemType();
        
        source.setGemType(targetGemType);
        target.setGemType(sourceGemType);
    }

    /**
     * Returns an array containing the indices of columns affected by an action.
     *
     * @param Cases Involved cells.
     * @return Affected columns.
     */
    public int[] getAffectedColumns(GameCase[] Cases) {
        int[] tab = new int[NB_COLS];
        int cpt = 0;

        for (GameCase caseJeu : Cases) {
            if (caseJeu != null) {
                int col = caseJeu.getPositionX();
                boolean alreadyAdded = false;

                for (int i = 0; i < cpt; i++) {
                    if (tab[i] == col) {
                        alreadyAdded = true;
                    }
                }

                if (!alreadyAdded) {
                    tab[cpt] = col;
                    cpt++;
                }
            }
        }

        int[] finalTab = new int[cpt];
        for (int i = 0; i < cpt; i++) {
            finalTab[i] = tab[i];
        }
        return finalTab;
    }

    /**
     * Updates the grid by causing cells to fall downward.
     *
     * @param affectedColumns Columns where cells have been removed.
     */
    public void updateFallCase(int[] affectedColumns) {
        for (int col : affectedColumns) {
            for (int y = NB_ROWS - 1; y >= 0; y--) {    
                GameCase bottomCase = getCaseAt(col, y);

                if (bottomCase.getGemType() == 3) {
                    int y2 = y;
                    boolean temp = false;
                    
                    while (y2 >= 0 && !temp) {
                        GameCase topCase = getCaseAt(col, y2);

                        if (topCase.getGemType() != 3) {
                            moveCase(bottomCase, topCase);
                            temp = true;
                        }
                        y2 -= 1;
                    }
                }
            }
        }
    }

    /**
     * Checks if a column is completely empty.
     *
     * @param nbColumn Index of the column.
     * @return true if empty, false otherwise.
     */
    public boolean isEmptyColumns(int nbColumn) {
        boolean isEmptyColumn = true;
        for (int i = 0; i < NB_ROWS; i++) {
            if (getCaseAt(nbColumn, i).getGemType() != 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps the contents of two columns.
     *
     * @param col1 First column.
     * @param col2 Second column.
     */
    public void inverseColumn(int col1, int col2) {
        for (int row = 0; row < NB_ROWS; row++) {
            GameCase case1 = getCaseAt(col1, row);
            GameCase case2 = getCaseAt(col2, row);
            moveCase(case1, case2);
        }
    }

    /**
     * Updates the grid by removing columns that have become empty.
     *
     * @param affectedColumns Columns to check.
     */
    public void updateEmptyCol(int[] affectedColumns) {
        Arrays.sort(affectedColumns);
        
        int index = 0;
        
        for (int column : affectedColumns) {
            int reelColumn = column - index;
            
            if (reelColumn < NbColumnsAvailable && isEmptyColumns(reelColumn)) {
                for (int j = reelColumn; j < NbColumnsAvailable - 1; j++) {
                    inverseColumn(j, j + 1);
                }

                NbColumnsAvailable -= 1;
                index += 1;
            }
        }
    }

    /**
     * Reassigns group identifiers to each connected cell.
     */
    public void updateGroupCaseByID() {
        GameCase[][] tempCasesNotVisited = new GameCase[this.NB_ROWS][this.NB_COLS];
        for (int i = 0; i < this.NB_ROWS; i++) {
            for (int j = 0; j < this.NB_COLS; j++) {
                tempCasesNotVisited[i][j] = this.casesGrid[i][j];
            }
        }
        
        GameCase[][] tempNeighborGroups = new GameCase[NB_ROWS * NB_COLS][];
        int CptGroupID = 0;
        
        for (int i = 0; i < this.NB_ROWS; i++) {
            for (int j = 0; j < this.NB_COLS; j++) {
                GameCase currentCase = tempCasesNotVisited[i][j];
                
                if (currentCase != null && currentCase.getGemType() != 3) {
                    GameCase[] group = getNeighbors(currentCase);
                    
                    tempNeighborGroups[CptGroupID] = group;
                    
                    for (GameCase caseTemp : group) {
                        caseTemp.setGroupID(CptGroupID);
                        int x = caseTemp.getPositionX();
                        int y = caseTemp.getPositionY();
                        tempCasesNotVisited[y][x] = null;
                    }
                    
                    CptGroupID++;
                } else if (currentCase != null && currentCase.getGemType() == 3) {
                    currentCase.setGroupID(-1);
                    tempCasesNotVisited[i][j] = null;
                }
            }
        }

        GameCase[][] finalNeighborGroups = new GameCase[CptGroupID][];
        for (int i = 0; i < CptGroupID; i++) {
            finalNeighborGroups[i] = tempNeighborGroups[i];
        }
        
        this.neighborGroups = finalNeighborGroups;
    }

    /**
     * Returns a group of cells based on their group identifier.
     *
     * @param groupID Group identifier.
     * @return Array of cells belonging to the group.
     */
    public GameCase[] getNeighborsByGroupID(int groupID) {
        return this.neighborGroups[groupID];
    }

    /**
     * Checks if the game is over (no group with more than one cell).
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean getGameEnd() {
        for (GameCase[] group : this.neighborGroups) {
            if (group.length > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the number of remaining cells after removal.
     *
     * @param CasesDeleted Number of removed cells.
     */
    public void updateCasesAvailable(int CasesDeleted) {
        this.cptCasesAvailable -= CasesDeleted;
    }

    /**
     * Returns the number of remaining cells.
     *
     * @return Number of remaining cells.
     */
    public int getCptCasesAvailable() {
        return this.cptCasesAvailable;
    }

    /**
     * Activates or deactivates the highlighting of a group of cells.
     *
     * @param caseJeu Reference cell.
     * @param bool    true to highlight, false to deactivate.
     */
    public void setHighlightCaseGroup(GameCase caseJeu, boolean bool) {
        if (caseJeu.getGemType() != 3) {
            GameCase[] groupCases = getNeighborsByGroupID(caseJeu.getGroupID());
            for (GameCase tcase : groupCases) {
                tcase.setHighlighted(bool);
            }
        }
    }
}