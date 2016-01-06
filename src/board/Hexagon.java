package board;

import java.awt.*;

/**
 * This class is used in drawing of the grid. Referenced outside sources. http://stackoverflow
 * .com/questions/20734438/algorithm-to-generate-a-hexagonal-grid-with-coordinate-system
 * 
 * @author Group B2
 */
public class Hexagon extends Polygon {

    private static final long serialVersionUID = 1L;

    /** 6 sides because it's a hexagon */
    public static final int SIDES = 6;

    private Point[] points = new Point[SIDES];
    private Point center = new Point(0, 0);
    private int radius;
    private int rotation = 90;

    /**
     * Initializes a new hexagon
     * 
     * @param center is the coordinates of where the hexagon is
     * @param radius of the grid
     */
    public Hexagon(Point center, int radius) {
        npoints = SIDES;
        xpoints = new int[SIDES];
        ypoints = new int[SIDES];

        this.center = center;
        this.radius = radius;

        updatePoints();
    }

    /**
     * Gets coordinates of where the hexagon will go, and the radius of the board, then calls a more
     * detailed method that will initialize the hexagon
     * 
     * @param x coordinate where the hexagon lies
     * @param y coordinate where the hexagon lies
     * @param radius of the grid
     */
    public Hexagon(int x, int y, int radius) {
        this(new Point(x, y), radius);
    }

    /**
     * Finds the angles on the hexagon
     * 
     * @param fraction
     * @return a double representation of the angle
     */
    private double findAngle(double fraction) {
        return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
    }

    /**
     * Find the point where the middle of the hexagon will be drawn
     * 
     * @param angle
     * @return the center point of hexagon
     */
    private Point findPoint(double angle) {
        int x = (int) (center.x + Math.cos(angle) * radius);
        int y = (int) (center.y + Math.sin(angle) * radius);

        return new Point(x, y);
    }

    /**
     * Update the points of the hexagon being drawn
     */
    protected void updatePoints() {
        for (int p = 0; p < SIDES; p++) {
            double angle = findAngle((double) p / SIDES);
            Point point = findPoint(angle);
            xpoints[p] = (int) point.x;
            ypoints[p] = (int) point.y;
            points[p] = point;
        }
    }

    /**
     * 
     * @param g the graphics used for painting
     * @param x coordniate
     * @param y coordniate
     * @param border width
     * @param colorValue of what colour you want the hexagon to be drawn as
     * @param filled boolean value which is used if it's already filled with colour
     */
    public void draw(Graphics2D g, int x, int y, int border, int colorValue, boolean filled) {
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        // set the colour of the inside of the hexagon
        g.setColor(new Color(colorValue));
        // set the border of the hexagon
        g.setStroke(new BasicStroke(border, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        if (filled) {
            g.fillPolygon(xpoints, ypoints, npoints);
        } else {
            g.drawPolygon(xpoints, ypoints, npoints);
        }

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }
}
