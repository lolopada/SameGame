import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The <code>ExportButtonListener</code> class contains the listener for the file search button of the SameGame game window.
 * It handles the click action on the export pattern button of the game window to export the grid to the exportPattern.txt file.
 * 
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class ExportButtonListener implements ActionListener {
    /** reference to the game window containing the grid to export */
    private GameFrame gameFrame;

    /** 
     * Constructor of the export button listener associated with a given game window.
     * @param gameFrame the game window whose grid must be exported
     */ 
    public ExportButtonListener(GameFrame gameFrame) {
        super();
        this.gameFrame = gameFrame;
    }
    
    /** 
     * Method triggered when the button is clicked.
     * Initiates the export of the current grid to a file named exportPattern.txt.
     * @param e the element that was clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.gameFrame.writePattern("exportPattern.txt");
    }
}