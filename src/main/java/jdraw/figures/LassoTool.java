package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

import java.awt.event.MouseEvent;

public class LassoTool extends AbstractDrawTool {

    /**
     * Create a new figure tool for the given context.
     *
     * @param context  a context to use this tool in.
     * @param name
     * @param iconName
     */
    public LassoTool(DrawContext context, String name, String iconName) {
        super(context, name, iconName);
    }

    @Override
    protected Figure createFigure(int x, int y) {
        return new Lasso(context.getView().getSelection(), context.getModel());
    }

    @Override
    public void activate() {
        super.activate();
        newFigure = this.createFigure(0, 0);
        context.getModel().addFigure(newFigure);
    }

    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
    }

    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
    }

    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
    }
}
