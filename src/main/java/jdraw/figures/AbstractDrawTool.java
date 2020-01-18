package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractDrawTool implements DrawTool {

    /**
     * the image resource path.
     */
    private static final String IMAGES = "/images/";
    /**
     * The context we use for drawing.
     */
    protected final DrawContext context;
    /**
     * Temporary variable.
     * During figure creation this variable refers to the point the
     * mouse was first pressed.
     */

    private final String name;
    /**
     * Temporary variable. During figure creation (during a
     * mouse down - mouse drag - mouse up cycle) this variable refers
     * to the new rectangle that is inserted.
     */
    protected Figure newFigure = null;
    private ImageIcon icon;
    /**
     * The context's view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    private DrawView view;
    /**
     * Temporary variable.
     * During figure creation this variable refers to the point the
     * mouse was first pressed.
     */
    private Point anchor = null;

    /**
     * Create a new figure tool for the given context.
     *
     * @param context a context to use this tool in.
     */

    protected AbstractDrawTool(DrawContext context, String name, String iconName) {
        this.context = context;
        this.name = name;
        this.view = context.getView();
        this.icon = new ImageIcon(getClass().getResource(IMAGES + iconName.toLowerCase()));
    }

    /**
     * implement in concrete class
     */
    abstract protected Figure createFigure(int x, int y);

    /**
     * Deactivates the current mode by resetting the cursor
     * and clearing the status bar.
     *
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Activates the Figure Mode. There will be a
     * specific menu added to the menu bar that provides settings for
     * Figure attributes
     */
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }


    @Override
    public final Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + this.name.toLowerCase() + ".png"));
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Cursor getCursor() {
        return null;
    }

    /**
     * Initializes a new Rectangle object by setting an anchor
     * point where the mouse was pressed. A new Rectangle is then
     * added to the model.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were pressed.
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
     */
    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newFigure != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        newFigure = createFigure(x, y);
        view.getModel().addFigure(newFigure);
    }

    /**
     * During a mouse drag, the Rectangle will be resized according to the mouse
     * position. The status bar shows the current size.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were
     *          pressed.
     * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newFigure.setBounds(anchor, new Point(x, y));
        Rectangle r = newFigure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * Initializes a new Rectangle object by setting an anchor
     * point where the mouse was pressed. A new Rectangle is then
     * added to the model.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were pressed.
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
     */

    /**
     * When the user releases the mouse, the Rectangle object is updated
     * according to the color and fill status settings.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were
     *          pressed.
     * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newFigure = null;
        anchor = null;
        this.context.showStatusText(this.name + " Mode");
    }
}

