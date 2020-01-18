package jdraw.test;

import jdraw.figures.Oval;
import jdraw.figures.Rect;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OvalTest extends AbstractFigureTest {

    @Override
    protected Figure createFigure() {
        return new Oval(1, 1, 20, 10);
    }
}
