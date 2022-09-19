package com.example.gwt.sandbox.shared;

import java.io.Serializable;

public class Dat  implements Serializable {
    private int anneDeb;
    private int moisDeb;
    private int jourDeb;
    private int hDeb;
    private int mnDeb;
    private int longDeb;
    private int anneFin;
    private int moisFin;
    private int jourFin;
    private int hFin;
    private int mnFin;
    private int longFin;

    public Dat(int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin) {
        this.anneDeb = anneDeb;
        this.moisDeb = moisDeb;
        this.jourDeb = jourDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.anneFin = anneFin;
        this.moisFin = moisFin;
        this.jourFin = jourFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
    }

    public int getAnneDeb() {
        return anneDeb;
    }

    public int getMoisDeb() {
        return moisDeb;
    }

    public int getJourDeb() {
        return jourDeb;
    }

    public int gethDeb() {
        return hDeb;
    }

    public int getMnDeb() {
        return mnDeb;
    }

    public int getLongDeb() {
        return longDeb;
    }

    public int getAnneFin() {
        return anneFin;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public int getJourFin() {
        return jourFin;
    }

    public int gethFin() {
        return hFin;
    }

    public int getMnFin() {
        return mnFin;
    }

    public int getLongFin() {
        return longFin;
    }
}
