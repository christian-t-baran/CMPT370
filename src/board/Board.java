package board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import board.Tile.tileType;
import containers.Pair;
import control.Controller;

/**
 * This class displays the hexagon grid on the panel. Referenced outside sources.
 * http://stackoverflow
 * .com/questions/20734438/algorithm-to-generate-a-hexagonal-grid-with-coordinate-system
 * 
 * @author Group B2
 */
public class Board extends JPanel {
    private static Board board = null;

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 700;
    private final int HEIGHT = 600;

    private Font font = new Font("Calibri", Font.PLAIN, 14);
    /** Allows the width and height of the font to be calculated */
    FontMetrics metrics;


    private Controller theController;
    private int boardSize;

    /**
     * Private constructor to build Board
     */
    private Board() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }

    /**
     * @return an instance of the board
     */
    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    /**
     * Initializes the board
     * 
     * @param size size of the board
     * @param c controller to get updates form
     */
    public void initBoard(int size, Controller c) {
        if (theController == null) {
            theController = c;
            this.boardSize = size;
        }
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // Where the origin will be places on the frame
        Point origin = new Point(WIDTH / 2, HEIGHT / 2);
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        drawHexGridLoop(g2d, origin, boardSize, 25, 9);
    }

    /**
     * Loop that draws the hexagon
     * 
     * @param g the graphics used for painting
     * @param origin (0,0)
     * @param size the most amount of hexagons going across
     * @param radius of the grid to be drawn
     * @param padding distance between each hexagon
     */
    private void drawHexGridLoop(Graphics g, Point origin, int size, int radius, int padding) {
        double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * (radius + padding);
        double yOff = Math.sin(ang30) * (radius + padding);
        int half = size / 2;

        for (int row = 0; row < size; row++) {
            int cols = size - java.lang.Math.abs(row - half);

            for (int col = 0; col < cols; col++) {
                // if row is less than half, then xLbl = col - row, otherwise xLbl = col - half
                int xLbl = row < half ? col - row : col - half;
                int yLbl = row - half;

                int x = (int) (origin.x + xOff * (col * 2 + 1 - cols));
                int y = (int) (origin.y + yOff * (row - half) * 3);

                drawHex(g, xLbl, yLbl, x, y, radius);
            }
        }
    }

    /**
     * Draws an individual hexagon
     * 
     * @param g the graphics used for painting
     * @param posX position of the current x coordinate
     * @param posY position of the current y coordinate
     * @param x coordinate position on the board it will be painted
     * @param y coordinate position on the board it will be painted
     * @param r radius of the grid being drawn
     */
    private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
        Graphics2D g2d = (Graphics2D) g;

        Hexagon hex = new Hexagon(x, y, r);
        // String text = String.format("%s : %s",posX, posY);

        // String text = tileText(posX, posY);
        Pair<Pair<Integer, Integer>, tileType> p = theController.drawBoardInfo(posX, posY);
        int numberOfRobots = p.left().left();
        int dominantTeam = p.left().right();
        tileType t = p.right();

        String text = "";
        if (numberOfRobots > 0) {
            text = String.format("%s", numberOfRobots);
        } 

        // Get the width and height of the text so we can get the calculations to center the text in
        // the hexagon
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();

        // Colour the hexagon


        int colour = 0;
        switch (dominantTeam) {
            case 1:
                // Set font colour to red for team 1
                colour = 0xFF0000;
                break;
            case 2:
                // Set font colour to blue for team 2
                colour = 0x0000FF;
                break;
            case 3:
                // Set font colour to yellow for team 3
                colour = 0xFFCC00;
                break;
            case 4:
                // Set font colour to green for team 4
                colour = 0x00CC00;
                break;
            case 5:
                // Set font colour to purple for team 5
                colour = 0x9900FF;
                break;
            case 6:
                // Set font colour to orange for team 6
                colour = 0xFF6600;
                break;
            case 7:
                // Set font colour to black if there are multiple teams on the tile
                colour = 0x000000;
                break;
            default:
                colour = 0xFFFFFF;
        }


        // Colour the borders for which type of space it is. Plain, Forest, Mountain, Water
        // plain (0xcc4400) brown
        // water (0x99ddff) blue
        // forest (0x4dff4d) green
        // mountain (0xadad85) grey
        switch (t) {
            case PLAIN: // Plain
                hex.draw(g2d, x, y, 9, 0xcc4400, false);
                break;
            case FOREST: // Forest
                hex.draw(g2d, x, y, 9, 0x4dff4d, false);
                break;
            case MOUNTAIN: // Mountain
                hex.draw(g2d, x, y, 9, 0xadad85, false);
                break;
            case WATER: // Water
                hex.draw(g2d, x, y, 9, 0x99ddff, false);
                break;
        }

        hex.draw(g2d, x, y, 0, colour, true);
        // Set font colour
        g.setColor(new Color(0x000000));


        // Place font on hexagon
        g.drawString(text, x - w / 2, y + h / 2);
    }

}
