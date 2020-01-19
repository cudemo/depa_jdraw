package jdraw.decorator;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.List;

public class BorderDecorator implements Figure {

    private final Figure inner;

    public BorderDecorator(Figure f) {
        this.inner = f;


    }

    @Override
    public void draw(Graphics g) {
        inner.draw(g);
        Rectangle r = getBounds();
        g.setColor(Color.WHITE);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.setColor(Color.GRAY);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
    }

    @Override
    public void move(int dx, int dy) {
        inner.move(dx, dy);
    }

    @Override
    public boolean contains(int x, int y) {
        return inner.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        inner.setBounds(origin, corner);

    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = inner.getBounds();
        r.grow(4, 4);
        return r;
    }

    @Override
    public List<FigureHandle> getHandles() {
        return inner.getHandles();
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        inner.addFigureListener(listener);
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        inner.removeFigureListener(listener);
    }

    @Override
    public Figure clone() {
        return inner.clone();
    }
}
