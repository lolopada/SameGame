import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The <code>GameCase</code> class represents a game tile containing a gem (Ruby, Emerald, Diamond).
 * Each tile has coordinates, a gem type, a boolean to determine if the tile is highlighted, and a group identifier to which it belongs.
 * 
 * @version 1.1
 * @author Loic Sainton and Mathis Pauron
 */
public class GameCase extends JComponent {
    
    /** type of the gem */
    private int gemType; // 0: ruby, 1: emerald, 2: diamond, 3: empty
    /** coordinates of the tile */
    private Point coordinates;
    /** indicator to know if the tile is highlighted */
    private boolean highlighted;
    /** group identifier of the tile */
    private int groupID; // groupID>=0, -1: no group
    
    /** Colors corresponding to gem types */
    public static final Color[] GEM_COLORS = {
        new Color(224, 17, 95),  // 0
        new Color(80, 200, 120), // 1
        new Color(65, 105, 225)  // 2
    };

    /**
     * Default constructor that creates a tile with a random gem type.
     */
    public GameCase() {
        Random rand = new Random();
        this.gemType = rand.nextInt(3);
        this.coordinates = new Point(0, 0);
        this.highlighted = false;
        this.groupID = -1;
    }

    /**
     * Constructor that creates a tile with a random gem type.
     * @param x X position in the grid
     * @param y Y position in the grid
     */
    public GameCase(int x, int y) {
        this();
        this.coordinates = new Point(x, y);
    }

    /**
     * Constructor specifying the gem type of the tile.
     * @param x X position in the grid.
     * @param y Y position in the grid.
     * @param type Type of gem.
     */
    public GameCase(int x, int y, int type) {
        this.gemType = type;
        this.coordinates = new Point(x, y);
        this.highlighted = false;
        this.groupID = -1;
    }

    /**
     * Returns the coordinates of the tile.
     * @return the coordinates of the tile
     */
    public Point getCoordinates() {
        return this.coordinates;
    }

    /**
     * Modifies the coordinates of the tile.
     * @param x New X position.
     * @param y New Y position.
     */
    public void setCoordinates(int x, int y) {
        this.coordinates = new Point(x, y);
    }

    /**
     * Returns the X position of the tile.
     * @return the X position of the tile
     */
    public int getPositionX() {
        return this.coordinates.x;
    }

    /**
     * Returns the Y position of the tile.
     * @return the Y position of the tile
     */
    public int getPositionY() {
        return this.coordinates.y;
    }

    /**
     * Defines if the tile is highlighted.
     * @param highlighted true if the tile is highlighted, false otherwise
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        repaint(); 
    }

    /**
     * Returns the group identifier of the tile.
     * @return the groupID of the tile
     */
    public int getGroupID() {
        return this.groupID;
    }

    /**
     * Sets the group identifier of the tile.
     * @param groupID the group ID to assign
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * Redraws the tile with its colors and gem based on its type.
     * @param g the graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = getWidth();
        int height = getHeight();
        
        int padding;
        if (width < height) {
            padding = width / 8;
        } else {
            padding = height / 8;
        }
        
        if (this.highlighted) {
            g.setColor(new Color(255, 255, 0, 140)); 
        } else {
            g.setColor(new Color(240, 240, 240)); 
        }
        g.fillRect(0, 0, width, height);
        
        if (this.gemType == 0) {
            drawRuby(g, width, height, padding);
        } else if (this.gemType == 1) {
            drawEmerald(g, width, height, padding);
        } else if (this.gemType == 2) {
            drawDiamond(g, width, height, padding);
        }
    }
    
    /**
     * Draws a ruby.
     * @param g graphics object
     * @param width width of the tile
     * @param height height of the tile
     * @param padding inner space between the ruby and the tile border
     */
    public void drawRuby(Graphics g, int width, int height, int padding) {
        
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];

        int left = padding;
        int right = width - padding;
        int top = padding;
        int bottom = height - padding;
        int cutSize = Math.min(width, height) / 3;
        
        xPoints[0] = left + cutSize;
        yPoints[0] = top;
        
        xPoints[1] = right - cutSize;
        yPoints[1] = top;
        
        xPoints[2] = right;
        yPoints[2] = top + cutSize;
        
        xPoints[3] = right;
        yPoints[3] = bottom - cutSize;
        
        xPoints[4] = right - cutSize;
        yPoints[4] = bottom;
        
        xPoints[5] = left + cutSize;
        yPoints[5] = bottom;
        
        xPoints[6] = left;
        yPoints[6] = bottom - cutSize;
        
        xPoints[7] = left;
        yPoints[7] = top + cutSize;
        
        g.setColor(GEM_COLORS[this.gemType]);
        g.fillPolygon(xPoints, yPoints, 8);
    }
    
    /**
     * Draws an emerald.
     * @param g graphics object
     * @param width width of the tile
     * @param height height of the tile
     * @param padding inner space between the emerald and the tile border
     */
    public void drawEmerald(Graphics g, int width, int height, int padding) {
        
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];

        int left = padding;
        int right = width - padding;
        int top = padding;
        int bottom = height - padding;
        int cutSize = Math.min(width, height) / 6;
        
        xPoints[0] = left + cutSize;
        yPoints[0] = top;
        
        xPoints[1] = right - cutSize;
        yPoints[1] = top;
        
        xPoints[2] = right;
        yPoints[2] = top + cutSize;
        
        xPoints[3] = right;
        yPoints[3] = bottom - cutSize;
        
        xPoints[4] = right - cutSize;
        yPoints[4] = bottom;
        
        xPoints[5] = left + cutSize;
        yPoints[5] = bottom;
        
        xPoints[6] = left;
        yPoints[6] = bottom - cutSize;
        
        xPoints[7] = left;
        yPoints[7] = top + cutSize;
        
        g.setColor(GEM_COLORS[this.gemType]);
        g.fillPolygon(xPoints, yPoints, 8);
    }
    
    /**
     * Draws a diamond.
     * @param g graphics object
     * @param width width of the tile
     * @param height height of the tile
     * @param padding inner space between the diamond and the tile border
     */
    public void drawDiamond(Graphics g, int width, int height, int padding) {
        
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];
        
        int centerX = width / 2;
        int centerY = height / 2;
        int horizontalRadius = (width - 2 * padding) / 2;
        int verticalRadius = (height - 2 * padding) / 2;
        
        xPoints[0] = centerX;
        yPoints[0] = padding;
        
        xPoints[1] = centerX + horizontalRadius / 2;
        yPoints[1] = centerY - verticalRadius / 3;
        
        xPoints[2] = centerX + horizontalRadius;
        yPoints[2] = centerY;
        
        xPoints[3] = centerX + horizontalRadius / 2;
        yPoints[3] = centerY + verticalRadius / 3;
        
        xPoints[4] = centerX;
        yPoints[4] = height - padding;

        xPoints[5] = centerX - horizontalRadius / 2;
        yPoints[5] = centerY + verticalRadius / 3;

        xPoints[6] = centerX - horizontalRadius;
        yPoints[6] = centerY;
        
        xPoints[7] = centerX - horizontalRadius / 2;
        yPoints[7] = centerY - verticalRadius / 3;
        
        g.setColor(GEM_COLORS[this.gemType]);
        g.fillPolygon(xPoints, yPoints, 8);
    }
    
    /**
     * Returns the color associated with the gem type.
     * @return the color of the gem.
     */
    public Color getColor() {
        return GEM_COLORS[this.gemType];
    }
    
    /**
     * Returns the gem type.
     * @return an integer among 0: ruby, 1: emerald, 2: diamond, 3: empty
     */
    public int getGemType() {
        return this.gemType;
    }

    /**
     * Sets the gem type and resets the highlighting and triggers a repaint.
     * @param i the new gem type.
     */
    public void setGemType(int i) {
        this.gemType=i;
        this.highlighted=false;
        this.repaint();
    }

    /**
     * Returns a textual representation of the tile,
     * including the gem type, its coordinates, and the group ID.
     * @return a string describing the tile.
     */
    @Override
    public String toString() {
        String gemName = "";
        if (this.gemType == 0) {
            gemName = "Ruby";
        } else if (this.gemType == 1) {
            gemName = "Emerald";
        } else if (this.gemType == 2) {
            gemName = "Diamond";
        }
        
        String groupInfo;
        if (this.groupID == -1) {
            groupInfo = "Not assigned";
        } else {
            groupInfo = String.valueOf(this.groupID);
        }
        
        return "GameCase[type=" + gemName + ", position=(" + this.coordinates.x + "," + this.coordinates.y 
               + "), groupID=" + groupInfo + "]";
    }
}
