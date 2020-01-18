package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class EastHandle extends AbstractHandle {

    public EastHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point location = getOwner().getBounds().getLocation();
        location.translate(getOwner().getBounds().width, getOwner().getBounds().height / 2);
        return location;
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x, r.y);
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(x, getLocation().y + getOwner().getBounds().height / 2);
    }
}
