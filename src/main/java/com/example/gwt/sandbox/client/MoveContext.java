package com.example.gwt.sandbox.client;

import org.vaadin.gwtgraphics.client.Positionable;

public class MoveContext {

    private int xRef;
    private int yRef;
    private Positionable positionable = null;

    void start(Positionable positionable, int x, int y) {
        if (!isBusy()) {
            this.positionable = positionable;
            xRef = x;
            yRef = y;
        }
    }

    boolean move(int x, int y) {
        if (isBusy()) {
            positionable.setX(positionable.getX() + (x - xRef));
            positionable.setY(positionable.getY() + (y - yRef));
            this.xRef = x;
            this.yRef = y;
            return true;
        }
        return false;
    }

    void stop(int x, int y) {
        if (move(x, y)) clear();
    }

    boolean isBusy() {
        return positionable != null;
    }

    private void clear() {
        xRef = 0;
        yRef = 0;
        positionable = null;
    }
}
