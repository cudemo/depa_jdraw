/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import jdraw.framework.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 *
 * @author Vito Cudemo
 * <p>
 * Model: Geschäftslogik - Verwaltet den Zustand von Objekten
 */
public class StdDrawModel implements DrawModel {

    private List<Figure> figures;
    private List<DrawModelListener> listeners;
    private FigureListener fl;
    /**
     * The draw command handler. Initialized here with a dummy implementation.
     */
    // TODO initialize with your implementation of the undo/redo-assignment.
    private DrawCommandHandler handler = new EmptyDrawCommandHandler();

    public StdDrawModel() {
        this.figures = new ArrayList<>();
        this.listeners = new ArrayList<>();
        fl = e -> notifyDrawModelListener(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED));

    }

    private final void notifyDrawModelListener(DrawModelEvent e) {
        // Make copy of listeners
        List<DrawModelListener> flc = new ArrayList<>(listeners);

        // Call modelChanged Method on each DrawModelListener with a DrawModelEvent (Figure_Added and co).
        flc.forEach(f -> f.modelChanged(e));
    }

    @Override
    public void addFigure(Figure f) {
        if (f != null && figures.add(f)) {

            f.addFigureListener(fl);

            notifyDrawModelListener(new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_ADDED));
        }
    }

    @Override
    public Stream<Figure> getFigures() {
        return figures.stream();
    }

    @Override
    public void removeFigure(Figure f) {
        if (f != null && figures.remove(f)) {
            f.removeFigureListener(fl);

            // Call notification on DrawModel - DrawView will listen
            notifyDrawModelListener(new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_REMOVED));
        }
    }

    @Override
    public void addModelChangeListener(DrawModelListener listener) {

        if (listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeModelChangeListener(DrawModelListener listener) {
        if (listener != null) {

            listeners.remove(listener);

        }
    }

    /**
     * Retrieve the draw command handler in use.
     *
     * @return the draw command handler.
     */
    @Override
    public DrawCommandHandler getDrawCommandHandler() {
        return handler;
    }

    @Override
    public void setFigureIndex(Figure f, int index) {

        int pos = figures.indexOf(f);

        if (pos < 0)
            throw new IllegalArgumentException();

        if (index >= figures.size() || index < 0)
            throw new IndexOutOfBoundsException();

        if (pos != index) {
            figures.remove(f);
            figures.add(index, f);
            notifyDrawModelListener(new DrawModelEvent(this, f, DrawModelEvent.Type.DRAWING_CHANGED));
        }
    }

    @Override
    public void removeAllFigures() {
        figures.forEach(f -> f.removeFigureListener(fl));
        figures.clear();
        notifyDrawModelListener(new DrawModelEvent(this, null, DrawModelEvent.Type.DRAWING_CLEARED));
    }
}
