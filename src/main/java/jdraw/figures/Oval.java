package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents Ovals in JDraw.
 *
 * @author Vito Cudemo
 */

public class Oval extends AbstractRectangularFigure {

    private Ellipse2D.Double oval;

    public Oval(int x, int y, int w, int h) {
        rectangle = new Rectangle(x, y, w, h);
    }

    public Oval(Oval o) {
        rectangle = new Rectangle(o.getBounds().x, o.getBounds().y, o.getBounds().width, o.getBounds().height);
    }

    /**
     * Draw the oval to the given graphics context.
     *
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        oval = new Ellipse2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        oval.setFrame(rectangle);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new Color(0, 200, 100));
        g2.fill(oval);
    }

    @Override
    public boolean contains(int x, int y) {
        return oval.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        Ellipse2D original = new Ellipse2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(oval)) {
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public Figure clone() {
        return new Oval(this);
    }
}
