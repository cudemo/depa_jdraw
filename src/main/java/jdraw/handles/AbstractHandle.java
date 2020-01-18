package jdraw.handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractHandle implements FigureHandle {

    private final Figure owner;
    protected Point corner;
    private Rectangle rect;

    private static final int size = 7;
    private static final int offset = 3;


    public AbstractHandle(Figure owner) {
        this.owner = owner;
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        rect = new Rectangle((loc.x - offset), (loc.y - offset), size, size);
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(rect.x, rect.y, size, size);
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle r = new Rectangle(getLocation().x, getLocation().y, size, size);
        return r.contains(x, y);
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    }


    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        corner = cornerPosition();
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Point pos = handlePosition(x, y);
        owner.setBounds(pos, corner);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        corner = null;
    }

    public abstract Point handlePosition(int x, int y);

    public abstract Point cornerPosition();

}
