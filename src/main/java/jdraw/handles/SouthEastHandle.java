package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class SouthEastHandle extends AbstractHandle {

    public SouthEastHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point location = getOwner().getBounds().getLocation();
        location.translate(getOwner().getBounds().width, getOwner().getBounds().height);
        return location;
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x, r.y);
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(x, y);
    }
}
