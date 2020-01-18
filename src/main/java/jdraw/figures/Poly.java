package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;

import java.awt.*;

/**
 * Represents Polygons in JDraw.
 *
 * @author Vito Cudemo
 */

public class Poly extends AbstractRectangularFigure {

    private final Polygon poly;

    /**
     * Create a new polygon inside a rectangle of the given dimension.
     *
     * @param x the x-coordinate of the upper left corner of the rectangle
     * @param y the y-coordinate of the upper left corner of the rectangle
     */

    public Poly(int x, int y, int w, int h) {
        poly = new Polygon();
        setPoly(new Rectangle(x, y, w, h));
    }

    public Poly(Poly p) {
        poly = new Polygon();
        setPoly(new Rectangle(p.getBounds().x, p.getBounds().y, p.getBounds().width, p.getBounds().height));
    }

    private void setPoly(Rectangle rectangle) {
        poly.reset();
        poly.addPoint(rectangle.x, rectangle.y + rectangle.height * 2 / 3);
        poly.addPoint(rectangle.x, rectangle.y + rectangle.height * 1 / 3);
        poly.addPoint(rectangle.x + rectangle.width * 1 / 3, rectangle.y);
        poly.addPoint(rectangle.x + rectangle.width * 2 / 3, rectangle.y);
        poly.addPoint(rectangle.x + rectangle.width, rectangle.y + rectangle.height * 1 / 3);
        poly.addPoint(rectangle.x + rectangle.width, rectangle.y + rectangle.height * 2 / 3);
        poly.addPoint(rectangle.x + rectangle.width * 2 / 3, rectangle.y + rectangle.height);
        poly.addPoint(rectangle.x + rectangle.width * 1 / 3, rectangle.y + rectangle.height);
    }

    /**
     * Draw the polygon to the given graphics context.
     *
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawPolygon(poly);
    }

    @Override
    public void move(int dx, int dy) {
        if (!(dx == 0 && dy == 0)) {
            poly.translate(dx, dy);
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return poly.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        java.awt.Rectangle original = new java.awt.Rectangle(poly.getBounds());
        Rectangle rect = new Rectangle();
        rect.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rect)) {
            setPoly(rect);
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public Rectangle getBounds() {
        return poly.getBounds();
    }

    @Override
    public Figure clone() {
        return new Poly(this);
    }


}
