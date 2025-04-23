import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The <code>PopUpFrame</code> class is used to create a window
 * displaying a message, with a button to close it.
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class PopUpFrame extends JFrame {
    /** The title of the window */
    private String title;
    /** Message displayed in the window */
    private String message;
    /** The parent game window */
    private GameFrame parent; 

    /**
     * Constructor for a pop-up window with a title and a message.
     * @param title the title of the window
     * @param message the displayed message
     */
    public PopUpFrame(String title, String message) {
        super(title);
        this.title = title;
        this.message = message;
        this.setFrame();
    }

    /**
     * Constructor for a pop-up window with a title, a message, and a reference to the parent game window.
     * @param title the title of the window
     * @param message the displayed message
     * @param gameFrame the parent game window
     */
    public PopUpFrame(String title, String message, GameFrame gameFrame) {
        super(title);
        this.title = title;
        this.message = message;
        this.parent = gameFrame;
        this.setFrame();
    }

    /**
     * Configures the appearance and content of the pop-up window.
     * Adds a centered message and a close button at the bottom.
     */
    public void setFrame() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 150);
        this.setLocationRelativeTo(null);

        JLabel label = new JLabel(this.message, SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new PopUpListener(this));
        buttonPanel.add(closeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Returns the title of the window
     * @return the title of the pop-up
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the parent game window
     * @return the parent of the pop-up window
     */
    public GameFrame getGameFrameParent() {
        return this.parent;
    }
}