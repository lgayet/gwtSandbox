package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;

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
    private transient ArrayList<Tache> aTaches;
    private int numTache = 0;//pour numéroter les tâches

    public Selection(){

    }

    public Selection(int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
        this.anneeDebut = anneeDebut;
        this.moisDebut = moisDebut;
        this.anneeFin = anneeFin;
        this.moisFin = moisFin;
    }
    public Tache ajoutTache(int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, int joursSelDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, int joursSelFin, int numColDeb, int numColFin){
        if(aTaches == null)aTaches = new ArrayList<>();
        Tache t = new Tache(getAndIncrNumTache(), anneDeb, moisDeb, jourDeb, hDeb, mnDeb, joursSelDeb, anneFin, moisFin, jourFin, hFin, mnFin, joursSelFin, numColDeb, numColFin);
        aTaches.add(t);
        return t;
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

    public int getNbJours() {
        return nbJours;
    }

    public Colonne[] getTCols() {
        return tCols;
    }

    public Salarie[] getTSals() {
        return tSals;
    }

    public int getAndIncrNumTache(){
        int num = numTache;
        numTache ++;
        return num;
    }
}
