import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.*;

/**
 * The <code>Menu</code> class is used to create a main menu for the SameGame.
 * It manages user interactions to choose between a random map or a map loaded from a file.
 *  
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class Menu extends JFrame {

    /** Main panel containing all interface elements */
    private JPanel panel;
    
    /** Label displaying the game title */
    private JLabel welcomeLabel;
    /** Label displaying an error message when there's a file loading issue */
    private JLabel errorLabel;
    
    /** Button group for selecting the game mode */
    private ButtonGroup modeGroup;
    /** Radio button to select the game mode with loading from a file */
    private JRadioButton searchGameRadioButton;
    /** Radio button to select the game mode with a randomly generated map */
    private JRadioButton randomMapRadioButton;
    /** Indicates if random mode is selected (true) or file mode (false) */
    private boolean isRandomMode;
    
    /** Panel containing elements related to file loading */
    private JPanel panelGameFileSet;
    /** Button to select a file containing a pattern */
    private JButton selectFileButton;
    /** Indicates if the loaded file is valid */
    private Boolean goodFile;
    /** Array representing the pattern loaded from the file */
    private static int[][] filePattern;
    
    /** Button to start a new game */
    private JButton startButton;
    
    /** Event listener for menu button interactions */
    private MenuButtonListener MenuButtonListener;

    /**
     * Menu window constructor.
     * Initializes default attributes (random mode enabled, no valid file loaded)
     * then configures the main menu's graphical interface via the setFrame() method.
     */
    public Menu() {
        this.goodFile = false;
        this.isRandomMode = true;
        this.setFrame();
    	System.out.println(this.panel.getLayout());
    }
    
    /**
     * Configures the graphical interface of the main menu.
     * Creates and positions all graphical components (buttons, labels, panels) and configures their event listeners.
     */
    public void setFrame() {
        this.setTitle("SameGame");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.panel = new JPanel(new GridLayout(4, 1, 10, 20));
        this.panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        this.welcomeLabel = new JLabel("The SameGame", SwingConstants.CENTER);
        this.welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.panel.add(this.welcomeLabel);
        
        this.panelGameFileSet = new JPanel(new GridLayout(1, 2));
        
        this.searchGameRadioButton = new JRadioButton("Search Game Set", false);
        this.selectFileButton = new JButton("Select File");
        this.selectFileButton.setFont(new Font("Arial", Font.PLAIN, 14));
        this.selectFileButton.setBackground(new Color(200,200,200));
        this.randomMapRadioButton = new JRadioButton("Random Map", true);

        this.modeGroup = new ButtonGroup();
        this.modeGroup.add(this.searchGameRadioButton);
        this.modeGroup.add(this.randomMapRadioButton);
        
        this.MenuButtonListener = new MenuButtonListener(this);
        this.searchGameRadioButton.addActionListener(this.MenuButtonListener);
        this.randomMapRadioButton.addActionListener(this.MenuButtonListener);
        this.selectFileButton.addActionListener(this.MenuButtonListener);
        
        this.panelGameFileSet.add(this.searchGameRadioButton);
        this.panelGameFileSet.add(this.selectFileButton);
        this.panel.add(this.panelGameFileSet);
        this.panel.add(this.randomMapRadioButton);
        
        this.startButton = new JButton("Start Game");
        this.startButton.setFont(new Font("Arial", Font.BOLD, 16));
        this.startButton.setBackground(new Color(50, 180, 50));
        this.startButton.setForeground(Color.WHITE);
        this.startButton.addActionListener(this.MenuButtonListener);
        this.panel.add(this.startButton);
        
        this.errorLabel = new JLabel("Wrong file", SwingConstants.CENTER);
        this.errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.errorLabel.setForeground(Color.RED);
        this.errorLabel.setVisible(false);
        
        this.add(this.panel);
        this.add(this.errorLabel, BorderLayout.SOUTH);
    }

    /**
     * Returns the instance of this frame.
     * @return The reference to this frame
     */
    public JFrame getFrame() {
        return this;
    }

    /**
     * Returns the radio button for the file loading mode.
     * @return The radio button to select loading from a file
     */
    public JRadioButton getSearchGameRadioButton() {
        return this.searchGameRadioButton;
    }

    /**
     * Returns the radio button for the random game mode.
     * @return The radio button to select random map generation
     */
    public JRadioButton getRandomMapRadioButton() {
        return this.randomMapRadioButton;
    }

    /**
     * Returns the button for selecting a pattern file.
     * @return The button to choose a pattern file
     */
    public JButton getSelectFileButton() {
        return this.selectFileButton;
    }

    /**
     * Returns the button that starts a new game.
     * @return The button to start the game
     */
    public JButton getStartButton() {
        return this.startButton;
    }

    /**
     * Returns the array of the game configuration loaded from a file.
     * @return The 2D array representing the game pattern
     */
    public int[][] getFilePattern() {
        return this.filePattern;
    }

    /**
     * Sets the map generation mode.
     * @param mode true for random mode, false for file mode
     */
    public void setRandomMode(boolean mode) {
        this.isRandomMode = mode;
    }

    /**
     * Checks if random mode is enabled.
     * @return true if random mode is enabled, false otherwise
     */
    public boolean isRandomMode() {
        return this.isRandomMode;
    }

    /**
     * Changes the color of the file selection button.
     * @param c The new color to apply to the button
     */
    public void setChooseFileButtonColor(Color c) {
        this.selectFileButton.setBackground(c);
    }

    /**
     * Sets whether the loaded file is valid.
     * @param bool true if the file is valid, false otherwise
     */
    public void setGoodFile(boolean bool) {
        this.goodFile = bool;
    }

    /**
     * Sets the game pattern array from the loaded file.
     * @param tab The 2D array containing the game configuration
     */
    public void setFilePattern(int[][] tab) {
        this.filePattern = tab;
    }

    /**
     * Checks if the loaded file is valid to start a game.
     * @return true if the file is valid, false otherwise
     */
    public boolean isGoodFile() {
        return this.goodFile;
    }
    
    /**
     * Displays the error label when trying to start a game but the chosen file is not correct.
     * Updates the frame layout to display the label correctly.
     */
    public void showErrorLabel() {
        this.errorLabel.setVisible(true);
        this.revalidate(); // recalculates the frame layout
        this.repaint();
    }

    /**
     * Hides the error label when the chosen file is correct.
     */
    public void hideErrorLabel() {
        this.errorLabel.setVisible(false);
        this.revalidate();
        this.repaint();
    }

    /**
     * Creates a new instance of GameFrame according to the chosen mode (random or with pattern).
     * If the file in pattern mode is not correct, the GameFrame instance will not be created and the error label will be displayed.
     */
    public void setNewGame() {
        if (this.isRandomMode()) {
            
            this.dispose();
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);

        } else if (!this.isRandomMode() && this.isGoodFile()) {

            int[][] gamePattern = this.getFilePattern();
            this.dispose();
            GameFrame gameFrame = new GameFrame(gamePattern);
            gameFrame.setVisible(true);

        } else if (!this.isRandomMode() && !this.isGoodFile()) {

            this.showErrorLabel();
        }
    }

    /**
     * Opens a JFileChooser instance to select a file (pattern).
     * Changes the color of the file choice button based on the file's validity.
     */
    public void choosefile() {
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setCurrentDirectory(new File(".").getAbsoluteFile()); // game directory 

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("file cancel");
            setChooseFileButtonColor(new Color(200,50,100));
            setGoodFile(false);
        } else if (result == JFileChooser.ERROR_OPTION) {
            System.out.println("file error");
            setChooseFileButtonColor(new Color(200,50,100));
            setGoodFile(false);
        }else if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();

            try {
                boolean FileVerif = validateFile(selectedFile);

                if (FileVerif) {
                    setChooseFileButtonColor(new Color(50,200,100));
                    setGoodFile(true);
                    hideErrorLabel();
                } else {
                    setChooseFileButtonColor(new Color(200,50,100));
                    setFilePattern(null);
                    setGoodFile(false);
                }
                
            } catch (IOException ex) {
                System.out.println("File not found.");
                setChooseFileButtonColor(new Color(200, 50, 100));
                setGoodFile(false);
                setFilePattern(null);
            }
            
        }
    }

    /**
     * Returns true if the file parameter is validated according to the game standards, false otherwise.
     * @return true if the file is valid, false otherwise.
     * @param file file to validate
     */
    public boolean validateFile(File file) throws IOException {
        String line;
        int linecpt = 0;

        int[][] tempPattern = new int[10][15];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
        
            try {
                while ((line = reader.readLine()) != null) {
                    linecpt++;

                    if (line.length() != 15) {
                        return false;
                    }

                    for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        
                        if (c != 'R' && c != 'V' && c != 'B') {
                            return false;
                        }

                        if (c == 'R') {
                            tempPattern[linecpt-1][i] = 0;
                        } else if (c == 'V') {
                            tempPattern[linecpt-1][i] = 1;
                        } else {
                            tempPattern[linecpt-1][i] = 2;
                        }
                    }

                    if (linecpt > 10) {
                        return false;
                    }
                }
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the export file.");    
                    return false;    
                }
            } catch (IOException e) {
                System.out.println("Error reading the export file.");
                return false;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error reading the export file.");
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the export file.");
            return false;
        }
        
        if (linecpt == 10) {
            setFilePattern(tempPattern);
            return true;
        }
        
        return false;
    }

    /**
     * Returns a string describing the current state of the menu (the chosen game mode, file validity)
     * @return a string representation of the menu
     */
    @Override
    public String toString() {
        String mode = this.isRandomMode ? "Random" : "Pattern";
        String fileStatus = this.goodFile ? "true" : "false";
        
        return "Menu [mode=" + mode 
               + ", fileValid=" + fileStatus
               + "]";
    }
}
