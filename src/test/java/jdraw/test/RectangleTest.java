package jdraw.test;

import jdraw.figures.Rect;
import jdraw.framework.Figure;

public class RectangleTest extends AbstractFigureTest{

    @Override
    protected Figure createFigure() {
        return new Rect(1, 1, 20, 10);
    }
}
