package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

public class OvalTool extends AbstractDrawTool {

    public OvalTool(DrawContext context, String name, String iconName) {
        super(context, name, iconName);
    }

    @Override
    protected Figure createFigure(int x, int y) {
        return new Oval(x, y, 0, 0);
    }
}
