package jdraw.figures;


import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;
import jdraw.handles.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRectangularFigure implements Figure {

    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
     */
    protected Rectangle rectangle;
    private final List<FigureListener> fl;
    private List<FigureHandle> handles;

    public AbstractRectangularFigure() {
        fl = new ArrayList<>();
    }

    @Override
    public void move(int dx, int dy) {
        if (!(dx == 0 && dy == 0)) {
            rectangle.translate(dx, dy);
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle);
        rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rectangle)) {
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public final void addFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        if (fl.contains(listener)) throw new IllegalStateException();

        fl.add(listener);
    }

    @Override
    public final void removeFigureListener(FigureListener listener) {
        fl.remove(listener);
    }


    @Override
    public List<FigureHandle> getHandles() {
        if (handles == null) {
            handles = new ArrayList<>();
            handles.add(new NorthWestHandle(this));
            handles.add(new SouthWestHandle(this));
            handles.add(new NorthEastHandle(this));
            handles.add(new SouthEastHandle(this));
            handles.add(new NorthHandle(this));
            handles.add(new WestHandle(this));
            handles.add(new SouthHandle(this));
            handles.add(new EastHandle(this));
        }
        return handles;
    }

    /**
     * final: Shall not be overwritten by subclasses
     */
    protected final void notifyFigureListener(FigureEvent e) {
        List<FigureListener> flc = new ArrayList<>(fl);
        flc.forEach(f -> f.figureChanged(e));
    }

    @Override
    public Figure clone() {

return null;

    }
}
