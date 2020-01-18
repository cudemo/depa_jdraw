package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

public class PolygonTool extends AbstractDrawTool {

    public PolygonTool(DrawContext context, String name, String iconName) {
        super(context, name, iconName);
    }

    @Override
    protected Figure createFigure(int x, int y) {
        return new Poly(x, y, 0, 0);
    }
}
