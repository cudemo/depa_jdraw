package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class NorthWestHandle extends AbstractHandle {

    public NorthWestHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        return getOwner().getBounds().getLocation();
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(x, y);
    }
}
