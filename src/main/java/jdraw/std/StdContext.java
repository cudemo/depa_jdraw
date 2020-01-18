/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import jdraw.figures.Group;
import jdraw.figures.OvalTool;
import jdraw.figures.PolygonTool;
import jdraw.figures.RectTool;
import jdraw.framework.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Standard implementation of interface DrawContext.
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.6, 24.09.09
 * @see DrawView
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {

    private List<Figure> clipboard ;

    /**
     * Constructs a standard context with a default set of drawing tools.
     *
     * @param view the view that is displaying the actual drawing.
     */
    public StdContext(DrawView view) {
        super(view, null);
    }

    /**
     * Constructs a standard context. The drawing tools available can be
     * parameterized using <code>toolFactories</code>.
     *
     * @param view          the view that is displaying the actual drawing.
     * @param toolFactories a list of DrawToolFactories that are available to the user
     */
    public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
        super(view, toolFactories);
    }

    /**
     * Creates and initializes the "Edit" menu.
     *
     * @return the new "Edit" menu.
     */
    @Override
    protected JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        final JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        editMenu.add(undo);
        undo.addActionListener(e -> {
                    final DrawCommandHandler h = getModel().getDrawCommandHandler();
                    if (h.undoPossible()) {
                        h.undo();
                    }
                }
        );

        final JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        editMenu.add(redo);
        redo.addActionListener(e -> {
                    final DrawCommandHandler h = getModel().getDrawCommandHandler();
                    if (h.redoPossible()) {
                        h.redo();
                    }
                }
        );
        editMenu.addSeparator();

        JMenuItem sa = new JMenuItem("SelectAll");
        sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
        editMenu.add(sa);
        sa.addActionListener(e -> {
                    getModel().getFigures().forEachOrdered(f -> getView().addToSelection(f));
                    getView().repaint();
                }
        );

        editMenu.addSeparator();


        JMenuItem cut = new JMenuItem("Cut");
        editMenu.add(cut);
        cut.addActionListener(e -> {
            List<Figure> cutList = getView().getSelection();
            clipboard = new ArrayList<>(cutList);
            cutList.forEach(f -> getModel().removeFigure(f));
        });

        JMenuItem copy = new JMenuItem("Copy");
        editMenu.add(copy);
        copy.addActionListener(e -> {
            List<Figure> copyList = getView().getSelection();
            clipboard = new ArrayList<>();
            copyList.forEach(f -> {
                clipboard.add(f.clone());
            });
        });


        JMenuItem paste = new JMenuItem("Paste");
        editMenu.add(paste);
        paste.addActionListener(e -> {
            if (clipboard != null) {
                clipboard.forEach(f -> getModel().addFigure(f.clone()));
            }
        });

        editMenu.addSeparator();
        JMenuItem clear = new JMenuItem("Clear");
        editMenu.add(clear);
        clear.addActionListener(e -> {
            getModel().removeAllFigures();
        });

        editMenu.addSeparator();
        JMenuItem group = new JMenuItem("Group");
        group.addActionListener(e -> {
            List<Figure> selected = getView().getSelection();
            Figure groupedFigure = new Group(selected);
            getModel().addFigure(groupedFigure);
            selected.forEach(f -> {
                getModel().removeFigure(f);
                getView().addToSelection(groupedFigure);
            });
        });
        editMenu.add(group);

        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.addActionListener(e -> {
            List<Figure> selected = getView().getSelection();
            selected.forEach(f -> {
                if (f instanceof Group) {
                    Iterable<Figure> parts = ((Group) f).getFigureParts();
                    parts.forEach(figure -> {
                        getModel().addFigure(figure);
                        getView().addToSelection(figure);
                    });
                    getModel().removeFigure(f);
                }
            });
        });
        editMenu.add(ungroup);
        editMenu.addSeparator();

        JMenu orderMenu = new JMenu("Order...");
        JMenuItem frontItem = new JMenuItem("Bring To Front");
        frontItem.addActionListener(e -> {
            bringToFront(getView().getModel(), getView().getSelection());
        });
        orderMenu.add(frontItem);
        JMenuItem backItem = new JMenuItem("Send To Back");
        backItem.addActionListener(e -> {
            sendToBack(getView().getModel(), getView().getSelection());
        });
        orderMenu.add(backItem);
        editMenu.add(orderMenu);

        JMenu grid = new JMenu("Grid...");
        grid.add("Grid 1");
        grid.add("Grid 2");
        grid.add("Grid 3");
        editMenu.add(grid);

        return editMenu;
    }

    /**
     * Creates and initializes items in the file menu.
     *
     * @return the new "File" menu.
     */
    @Override
    protected JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke("control O"));
        open.addActionListener(e -> doOpen());

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke("control S"));
        fileMenu.add(save);
        save.addActionListener(e -> doSave());

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(e -> System.exit(0));

        return fileMenu;
    }

    @Override
    protected void doRegisterDrawTools() {
        DrawTool rectangleTool = new RectTool(this, "Rectangle", "rectangle.png");
        addTool(rectangleTool);
        DrawTool ovalTool = new OvalTool(this, "Oval", "oval.png");
        addTool(ovalTool);
        DrawTool polyTool = new PolygonTool(this, "Polygon", "polygon.png");
        addTool(polyTool);
    }

    /**
     * Changes the order of figures and moves the figures in the selection
     * to the front, i.e. moves them to the end of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to front
     */
    public void bringToFront(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f)).collect(Collectors.toList());
        Collections.reverse(orderedSelection);
        int pos = (int) model.getFigures().count();
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, --pos);
        }
    }

    /**
     * Changes the order of figures and moves the figures in the selection
     * to the back, i.e. moves them to the front of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to the back
     */
    public void sendToBack(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f)).collect(Collectors.toList());
        int pos = 0;
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, pos++);
        }
    }

    /**
     * Handles the saving of a drawing to a file.
     */
    private void doSave() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
        chooser.setDialogTitle("Save Graphic");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));

        int res = chooser.showSaveDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // save graphic
            File file = chooser.getSelectedFile();
            FileFilter filter = chooser.getFileFilter();
            if (filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
                file = new File(chooser.getCurrentDirectory(), file.getName() + "." + ((FileNameExtensionFilter) filter).getExtensions()[0]);
            }
            System.out.println("save current graphic to file " + file.getName() + " using format "
                    + ((FileNameExtensionFilter) filter).getExtensions()[0]);
        }
    }

    /**
     * Handles the opening of a new drawing from a file.
     */
    private void doOpen() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("")
                .getFile());
        chooser.setDialogTitle("Open Graphic");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public String getDescription() {
                return "JDraw Graphic (*.draw)";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".draw");
            }
        });
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // read jdraw graphic
            System.out.println("read file "
                    + chooser.getSelectedFile().getName());
        }
    }

}
