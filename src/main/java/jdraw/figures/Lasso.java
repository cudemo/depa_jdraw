package jdraw.figures;

import jdraw.framework.*;

import java.awt.*;
import java.util.List;

public class Lasso extends AbstractRectangularFigure implements FigureListener, DrawModelListener {

    private List<Figure> parts;
    private Rectangle bounds;
    private DrawModel model;


    public Lasso(java.util.List<Figure> figures, DrawModel model) {
        if (figures == null) throw new NullPointerException("Keine Figuren ausgewählt");

        this.parts = figures;
        this.model = model;
        figures.forEach(f -> f.addFigureListener(this));
        model.addModelChangeListener(this);
        bounds = getBounds(figures);
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }


    @Override
    public void figureChanged(FigureEvent e) {
        bounds = getBounds(parts);
        notifyFigureListener(e);
    }

    @Override
    public Rectangle getBounds() {
        return bounds.getBounds();
    }

    private Rectangle getBounds(List<Figure> figures) {
        int xMin = figures.stream().mapToInt(f -> f.getBounds().x).min().orElse(0);
        int xMax = figures.stream().mapToInt(f -> f.getBounds().x + f.getBounds().width).max().orElse(0);
        int yMin = figures.stream().mapToInt(f -> f.getBounds().y).min().orElse(0);
        int yMax = figures.stream().mapToInt(f -> f.getBounds().y + f.getBounds().height).max().orElse(0);
        return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
    }

    @Override
    public void modelChanged(DrawModelEvent e) {
        if (e.getType() == DrawModelEvent.Type.FIGURE_REMOVED) {
            Figure f = e.getFigure();
            parts.remove(f);
            f.removeFigureListener(this);
            if (parts.size() == 0) {
                model.removeFigure(this);
                model.removeModelChangeListener(this);
            } else {
                bounds = getBounds(parts);
                notifyFigureListener(new FigureEvent(this));
            }
        }
    }
}
