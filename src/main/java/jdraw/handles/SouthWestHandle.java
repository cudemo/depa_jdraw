package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class SouthWestHandle extends AbstractHandle {

    public SouthWestHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point location = getOwner().getBounds().getLocation();
        location.translate(0, getOwner().getBounds().height);
        return location;
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x + r.width, r.y);
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(x, y);
    }

}
