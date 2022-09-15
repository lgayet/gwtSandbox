package com.example.gwt.sandbox.shared;

import com.example.gwt.sandbox.client.Colonne;

import java.io.Serializable;

public class Selection  implements Serializable {
    private int anneeDebut;
    private int moisDebut;
    private int anneeFin;
    private int moisFin;
//
    private int nbJours;
    private Colonne[] tCols;

    public Selection(){

    }

    public Selection(int anneeDebut, int moisDebut, int anneeFin, int moisFin, int nbJours, Colonne[] tCols) {
        this.anneeDebut = anneeDebut;
        this.moisDebut = moisDebut;
        this.anneeFin = anneeFin;
        this.moisFin = moisFin;
        this.nbJours = nbJours;
        this.tCols = tCols;
    }


    public int getAnneeDebut() {
        return anneeDebut;
    }

    public int getMoisDebut() {
        return moisDebut;
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public int getNbJours() {
        return nbJours;
    }

    public Colonne[] getTCols() {
        return tCols;
    }
}
