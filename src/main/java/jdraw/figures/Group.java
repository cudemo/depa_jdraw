package jdraw.figures;

import jdraw.framework.*;
import jdraw.handles.NorthEastHandle;
import jdraw.handles.NorthWestHandle;
import jdraw.handles.SouthEastHandle;
import jdraw.handles.SouthWestHandle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Figure, FigureGroup {

    private List<FigureHandle> handles;
    private List<Figure> groupedFigures;
    private Rectangle rect;

    private List<FigureListener> fl;

    // Modell muss mitgegeben werden, damit sortiert gruppiert wird, ansonsten verändert die Gruppierung die Reihenfolge

    public Group(List<Figure> groupedFigures) {
        if (groupedFigures == null || groupedFigures.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.groupedFigures = groupedFigures;
        fl = new ArrayList<>();
    }

    // sollte mit Java Cloning implementiert sein
    public Group(Group g) {
        groupedFigures = new ArrayList<>();
        fl = new ArrayList<>();
        g.groupedFigures.forEach(f -> {
            this.groupedFigures.add(f.clone());
        });
    }

    @Override
    public void draw(Graphics g) {
        for (Figure f : groupedFigures) {
            f.draw(g);
        }
    }

    @Override
    public void move(int dx, int dy) {
        if (!(dx == 0 && dy == 0)) {
            for (Figure f : groupedFigures) {
                f.move(dx, dy);
            }
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return rect.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rect);
        groupedFigures.forEach(f -> f.setBounds(origin, corner));
        rect.getBounds();
        if (!original.equals(rect)) {
            notifyFigureListener(new FigureEvent(this));
        }
    }

    @Override
    public Rectangle getBounds() {
        rect = groupedFigures.get(0).getBounds();
        getFigureParts().forEach(f -> rect.add(f.getBounds()));
        return rect.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        if (handles == null) {
            handles = new ArrayList<>();
            handles.add(new NorthWestHandle(this));
            handles.add(new SouthWestHandle(this));
            handles.add(new NorthEastHandle(this));
            handles.add(new SouthEastHandle(this));
        }
        return handles;
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

    protected final void notifyFigureListener(FigureEvent e) {
        List<FigureListener> flc = new ArrayList<>(fl);
        flc.forEach(f -> f.figureChanged(e));
    }

    @Override
    public Figure clone() {
        return new Group(this);
    }

    @Override
    public Iterable<Figure> getFigureParts() {
        return Collections.unmodifiableList(groupedFigures);
    }
}
