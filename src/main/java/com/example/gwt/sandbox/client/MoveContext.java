package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.shared.Tache;

public class MoveContext {

    private int xRef;
    private int yRef;
    private Tache tache = null;

    void start(Tache tache, int x, int y) {
        if (!isBusy()) {
            this.tache = tache;
            xRef = x;
            yRef = y;
        }
    }

    boolean move(int x, int y) {
        if (isBusy()) {
            tache.setX(tache.getX() + (x - xRef));
            tache.setY(tache.getY() + (y - yRef));
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
        return tache != null;
    }

    private void clear() {
        xRef = 0;
        yRef = 0;
        tache = null;
    }
}
