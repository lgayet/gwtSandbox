package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;


public class Niv implements Serializable {

    private int numIntersec;
    private int indice;
    private Tache[]taches = new Tache[1];

    public Tache[] getTaches() {
        return taches;
    }

    public Niv() {
    }

    public Niv(int numIntersec, int indice, Tache tache) {
        this.numIntersec = numIntersec;
        this.indice = indice;
        tache.setNumIntersection(numIntersec);
        tache.setNiveau(indice);
        taches[0] = tache;
    }

    public boolean controleEtAjout(Intersection inter, Tache tache){
        for(Tache t: taches){
            if(tache == t)return true;// j'ai déjà déposé cette tâche, je ne fais rien
        }
        for(Tache t: taches){
            if(t.getMnSelDeb() < tache.getMnSelFin() && t.getMnSelFin() > tache.getMnSelDeb()) {// je suis en intersection: je ne peux pas déposer cette tache sur ce niveau
                return false;
            }
        }
        Tache[] t = new Tache[taches.length + 1];
        for(int i = 0; i < taches.length; i ++){
            t[i] = taches[i];
        }
        tache.setNumIntersection(numIntersec);
        tache.setNiveau(indice);
        t[taches.length] = tache;
        taches = t;
        inter.ajoutArray(tache);
        return true;
    }

}
