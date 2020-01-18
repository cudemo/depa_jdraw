package jdraw.handles;

import jdraw.framework.Figure;

import java.awt.*;

public class NorthEastHandle extends AbstractHandle {

    public NorthEastHandle(Figure owner) {
        super(owner);
    }


    @Override
    public Point getLocation() {
        Point location = getOwner().getBounds().getLocation();
        location.translate(getOwner().getBounds().width, 0);
        return location;
    }

    @Override
    public Point handlePosition(int x, int y) {
        return new Point(x, y);
    }

    @Override
    public Point cornerPosition() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x, r.y + r.height);
    }
}
