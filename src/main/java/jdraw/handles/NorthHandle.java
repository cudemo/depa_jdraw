package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class NorthHandle extends AbstractHandle {

    public NorthHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point location = getOwner().getBounds().getLocation();
        location.translate(getOwner().getBounds().width / 2, 0);
        return location;
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x, r.y + r.height);
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(getLocation().x + getOwner().getBounds().width / 2, y);
    }
}
