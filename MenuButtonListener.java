import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JButton;

/**
 * The <code>MenuButtonListener</code> class contains listeners for the buttons in the main menu of SameGame.
 * Handles actions related to selecting the game mode (random or file/pattern), file searching, and game launching.
 * 
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class MenuButtonListener implements ActionListener {
    /** Reference to the menu window */
    private Menu menu;

    /**
     * Constructor for the menu listener.
     *
     * @param menu the main menu window of the game
     */
    public MenuButtonListener(Menu menu) {
        super();
        this.menu = menu;
    }
    
    /**
     * Method triggered when an action occurs (button or radio button click).
     * Handles the selection of grid generation mode (random or via pattern file),
     * starting a new game, and opening a file selector to load a pattern.
     *
     * @param e the element that was clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JRadioButton) {
            JRadioButton source = (JRadioButton) e.getSource();
            
            if (source == this.menu.getRandomMapRadioButton()) {
                this.menu.setRandomMode(true);
            } else if (source == this.menu.getSearchGameRadioButton()) {
                this.menu.setRandomMode(false);
            }
        
        } else if (e.getSource() instanceof JButton) {
            JButton source = (JButton)e.getSource();
            
            if (source == this.menu.getStartButton()) {
                this.menu.setNewGame();
            } else if (source == this.menu.getSelectFileButton()) {
                this.menu.choosefile();
            }
        }
    }
}