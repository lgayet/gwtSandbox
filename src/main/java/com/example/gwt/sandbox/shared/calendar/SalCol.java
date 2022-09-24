package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class SalCol implements Serializable {


    private Tache[]taches = new Tache[0];

    public Tache[] getTaches() {
        return taches;
    }

    public void ajoutTache(Salarie salarie, Tache tache){// pour le moment, les taches sont nouvelles, donc sans intersection
        for(Tache t: taches){
            if(t.getLongDeb() < tache.getLongFin() && t.getLongFin() > tache.getLongDeb()) {
                Intersection intersect;
                if(t.getNumIntersection() == null){
                    intersect = new Intersection(salarie.getNumInter(), t);//j'initialise l'intervention avec t
                    salarie.ajoutIntersection(intersect);
                }
                else {
                    intersect = salarie.getIntersection(t.getNumIntersection());
                }
                intersect.ajoutTache(tache);


        }
        }
        Tache[] t = new Tache[taches.length + 1];
        for(int i = 0; i < taches.length; i ++){
            t[i] = taches[i];
        }
        t[taches.length] = tache;
        taches = t;
    }
}
