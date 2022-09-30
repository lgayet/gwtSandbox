package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class SalCol implements Serializable {


    private int numCol;
    private Tache[]taches = new Tache[0];


    public SalCol() {
    }

    public SalCol(int numCol) {
        this.numCol = numCol;
    }

    public Tache[] getTaches() {
        return taches;
    }

    public Tache[] supprimeTache(Tache tache){
//        la tache devrait exister et a précédemment été retirée de son intersection éventuelle
        if(existeTache(tache)) {
            ArrayList<Tache> a = new ArrayList<>();
            for(Tache t: taches)a.add(t);
            a.remove(tache);
            taches = a.toArray(new Tache[a.size()]);
        }
        return taches;

    }

    public void mouvTache(Salarie salarie, Tache tache){
        ajoutIntersection(salarie, tache);
    }

    public void ajoutTache(Salarie salarie, Tache tache){// pour le moment, les taches sont nouvelles, donc sans intersection
        ajoutIntersection(salarie, tache);
        Tache[] t = new Tache[taches.length + 1];
        for(int i = 0; i < taches.length; i ++){
            t[i] = taches[i];
        }
        t[taches.length] = tache;
        taches = t;
    }

    private void ajoutIntersection(Salarie salarie, Tache tache){
        for(Tache t: taches){
            if(t.getMnSelDeb() < tache.getMnSelFin() && t.getMnSelFin() > tache.getMnSelDeb()) {
                Intersection intersect;
                if(t.getNumIntersection() == null){
                    intersect = salarie.ajoutIntersection(t, false);
                }
                else {
                    intersect = salarie.getIntersection(t.getNumIntersection());
                }
                intersect.ajoutTache(tache);
//TODO si la tâche est intersection, ajouter les tâches de son intersection

            }
        }
    }

    private boolean existeTache(Tache tache){
        for(Tache t: taches)if(t.getNumTache() == tache.getNumTache())return true;
        return false;
    }
}
