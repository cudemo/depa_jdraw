/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Represents rectangles in JDraw.
 *
 * @author Christoph Denzler
 */
public class Rect extends AbstractRectangularFigure {
    private static final long serialVersionUID = 9120181044386552132L;

    /**
     * Create a new rectangle of the given dimension.
     *
     * @param x the x-coordinate of the upper left corner of the rectangle
     * @param y the y-coordinate of the upper left corner of the rectangle
     * @param w the rectangle's width
     * @param h the rectangle's height
     */
    public Rect(int x, int y, int w, int h) {
        rectangle = new Rectangle(x, y, w, h);
    }

    public Rect(Rect r) {
        rectangle = (Rectangle) r.rectangle.clone();
    }

    /**
     * Draw the rectangle to the given graphics context.
     *
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Figure clone() {
        return new Rect(this);
    }
}
