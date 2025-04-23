import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * The class <code>PopUpListener</code> is used to perform actions on the pop-up window
 * @version 1.1
 * @author Loic Sainton et Mathis Pauron
 */
public class PopUpListener implements ActionListener {
    /** game over window */
    private PopUpFrame pupopFrame;
    
    /**
     * Constructor for the game over window.
     * Initializes the pupopFrame attribute as an object of the PopUpFrame class.
     * @param pupopFrame pop-up window
     */ 
    public PopUpListener(PopUpFrame pupopFrame) {
        super();
        this.pupopFrame = pupopFrame;
    }
    
    /**
     * Method triggered when closing a pop-up. If the closed pop-up is the game over window,
     * then it also closes the game window.
     * @param e indicates the element that was clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.pupopFrame.dispose();

        if(this.pupopFrame.getTitle().equals("Game over")) {
            this.pupopFrame.getGameFrameParent().closeGameFrame();
        }
    }
}