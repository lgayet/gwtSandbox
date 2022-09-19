package com.example.gwt.sandbox.shared;

import java.io.Serializable;

public class NivCol implements Serializable {

    private int numCol;
    private Tache[] taches = new Tache[0];
    //    pour positionner les Taches



    public NivCol() {
    }

    public NivCol(int numCol) {
        this.numCol = numCol;

    }

    public int getNumCol() {
        return numCol;
    }

    public Tache[] getTaches() {
        return taches;
    }

    public boolean essaiAjoutTache(Tache tache, int indice){
        for(Tache t: taches){
            if(t.getLongDeb() < tache.getLongFin() && t.getLongFin() > tache.getLongDeb()) {
                t.ajoutTacheIntersect(tache);
                tache.ajoutTacheIntersect(t);
                return false;
            }
        }
       Tache[] t = new Tache[taches.length + 1];
       for(int i = 0; i < taches.length; i ++){
           t[i] = taches[i];
       }
       tache.setNiveau(indice);
       t[taches.length] = tache;
       taches = t;
       return true;
    }
}


