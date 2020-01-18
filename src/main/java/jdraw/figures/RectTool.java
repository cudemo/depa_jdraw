/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @author Christoph Denzler
 * @see jdraw.framework.Figure
 */
public class RectTool extends AbstractDrawTool {

    /**
     * Create a new rectangle tool for the given context.
     *
     * @param context a context to use this tool in.
     */
    public RectTool(DrawContext context, String name, String iconName) {
        super(context, name, iconName);
    }

    @Override
    protected Figure createFigure(int x, int y) {
        return new Rect(x, y, 0, 0);
    }

}
