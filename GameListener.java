import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Point;

/**
 * The <code>GameListener</code> class is used to control mouse events for SameGame game cells.
 * Handles mouse clicks and hover events to trigger group deletion actions and group highlighting.
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class GameListener implements MouseListener {
    
    /** Game logic to manipulate the grid state */
    private GameLogic gameLogic;
    /** Game window used to update the display */
    private GameFrame gameFrame;
    private int temp;

    /**
     * Constructor for the listener.
     * @param gameLogic the game logic
     * @param gameFrame the game window associated with the display
     */
    public GameListener(GameLogic gameLogic, GameFrame gameFrame) {
        super();
        this.gameLogic = gameLogic;
        this.gameFrame=gameFrame;
    }

    /**
     * Handles clicks on a cell: if it belongs to a group of size > 1 (more than one cell),
     * the group is deleted, the score is updated, cells fall, and columns are updated.
     * If no action is possible, nothing happens.
     *
     * @param evenement the mouse click event
     */
    @Override
    public void mouseClicked(MouseEvent evenement) {
        GameCase Case = (GameCase) evenement.getSource();

        if (Case.getGemType() != 3) {
            GameCase[] groupCases = this.gameLogic.getNeighborsByGroupID(Case.getGroupID());

            if (groupCases.length > 1) {
                for (GameCase tcase : groupCases) {
                    tcase.setGemType(3);
                }
                this.gameFrame.updateScore(groupCases.length);
                this.gameLogic.updateCasesAvailable(groupCases.length);

                int[] affectedColumns = this.gameLogic.getAffectedColumns(groupCases);
                this.gameLogic.updateFallCase(affectedColumns);
                this.gameLogic.updateEmptyCol(affectedColumns);

                this.gameLogic.updateGroupCaseByID();
                this.gameLogic.setHighlightCaseGroup(Case, true);

                if (this.gameLogic.getGameEnd()) {
                    this.gameFrame.closeGame();
                }
            }
        }
    }       

    /**
     * Mouse hover: highlights all cells in the same group as the hovered cell.
     *
     * @param evenement the mouse hover event
     */
    @Override
    public void mouseEntered(MouseEvent evenement) {
        GameCase Case = (GameCase) evenement.getSource();
        this.gameLogic.setHighlightCaseGroup(Case, true);
    }
    
    /**
     * Mouse exit: removes highlighting from the group cells.
     *
     * @param evenement the mouse exit event
     */
    @Override
    public void mouseExited(MouseEvent evenement) {
        GameCase Case = (GameCase) evenement.getSource();
        this.gameLogic.setHighlightCaseGroup(Case, false);
    }
    
    /** Not used */
    @Override
    public void mousePressed(MouseEvent evenement) {}
    
    /** Not used */
    @Override
    public void mouseReleased(MouseEvent evenement) {

    }
}