package com.example.gwt.sandbox.shared;

import com.example.gwt.sandbox.client.Colonne;

import java.io.Serializable;

public class Selection  implements Serializable {
    private static final long serialVersionUID = 294851328L;
    private int anneeDebut;
    private int moisDebut;
    private int anneeFin;
    private int moisFin;
//
    private int nbJours;
    private Colonne[] tCols;
    private Salarie[] tSals;
    private Tache[] tTache;
    private int numTache = 0;

    public Selection(){

    }

    public Selection(int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
        this.anneeDebut = anneeDebut;
        this.moisDebut = moisDebut;
        this.anneeFin = anneeFin;
        this.moisFin = moisFin;
    }

    public Selection(int anneeDebut, int moisDebut, int anneeFin, int moisFin, int nbJours, Colonne[] tCols, Salarie[] tSals) {
        this.anneeDebut = anneeDebut;
        this.moisDebut = moisDebut;
        this.anneeFin = anneeFin;
        this.moisFin = moisFin;
        this.nbJours = nbJours;
        this.tCols = tCols;
        this.tSals = tSals;
    }

    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    public void setTCols(Colonne[] tCols) {
        this.tCols = tCols;
    }

    public void setTSals(Salarie[] tSals) {
        this.tSals = tSals;
    }

    public Tache[] getTTache() {
        return tTache;
    }

    public void setTTache(Tache[] tTache) {
        this.tTache = tTache;
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

    public Salarie[] getTSals() {
        return tSals;
    }
     public void setTTaches(Tache[] taches){
        this.tTache = taches;
     }

    public int getAndIncrNumTache(){
        int num = numTache;
        numTache ++;
        return num;
    }
}
