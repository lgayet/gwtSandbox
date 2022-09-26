package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Tache;

public class MoveContext  {

    private GSalarie salarie;
    private Tache tache = null;
    private int numCol;
    private Tache tacheInitiale;
    private double largCol;
    private int hDeb;
    private int mnDeb;
    private int hFin;
    private int mnFin;
    private int xRef;
    private int yRef;
//    valeurs de la tache pour le re-calcul



    void start(GSalarie salarie, Tache tache, int numCol, double largCol, int x, int y) {
        if (!isBusy()) {
            this.salarie = salarie;
            this.tache = tache;
            this.numCol = numCol;
            tacheInitiale = tache.copyTache();
            this.largCol = largCol;
            xRef = x;
            yRef = y;
            hDeb = tache.gethDeb();
            mnDeb = tache.getMnDeb();
            hFin = tache.gethFin();
            mnFin = tache.getMnFin();
        }
    }

    boolean move(int x, int y) {
        if (isBusy()) {
            int decalMn = (int)((x -xRef) * 1440 / largCol);
            int mnD = hDeb * 60 + mnDeb;
            int mnXD = mnD + decalMn;
            tache.setHDeb(mnXD / 60);
            tache.setMnDeb(mnXD % 60);
            int mnF = hFin * 60 + mnFin;
            int mnXF = mnF + decalMn;
            tache.setHFin(mnXF / 60);
            tache.setMnFin(mnXF % 60);
            return true;
        }
        return false;
    }

    void stop(int x, int y) {
        if (move(x, y)) clear();
    }

    int getNumCol(){
        return numCol;
    }

    GSalarie getSalarie(){
        return salarie;
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
