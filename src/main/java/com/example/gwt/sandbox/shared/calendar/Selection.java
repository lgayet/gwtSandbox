package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class Selection  implements Serializable {
    private static final long serialVersionUID = 294851328L;
//
    private int nbJours;
    private Colonne[] tCols;
    private Salarie[] tSals;
    private Tache[] tTache;
    private transient ArrayList<Tache> aTaches;
    private int numTache = 0;//pour numéroter les tâches

    public Selection(){

    }

    public Tache ajoutTache(int numSal, int joursSelDeb, int hDeb, int mnDeb, int joursSelFin, int hFin, int mnFin){
        if(aTaches == null)aTaches = new ArrayList<>();
        Tache t = new Tache(numSal, getAndIncrNumTache(), joursSelDeb, hDeb, mnDeb, joursSelFin, hFin, mnFin);
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
        return numTache ++;
    }
}
