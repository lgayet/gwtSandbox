package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;


public class Niv implements Serializable {

    private int numIntersec;
    private int indice;
    private Tache[]taches = new Tache[1];
//
    private transient Intersection intersection;




    public Niv() {
    }

    public Niv(Intersection intersection, int numIntersec, int indice, Tache tache) {
        this.intersection = intersection;
        this.numIntersec = numIntersec;
        this.indice = indice;
        tache.setIntersection(intersection);
        tache.setNiveau(indice);
        taches[0] = tache;
    }


    public Tache[] getTaches() {
        return taches;
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
        tache.setIntersection(intersection);
        tache.setNiveau(indice);
        t[taches.length] = tache;
        taches = t;
        return true;
    }
    public void setIntersection(Intersection intersection){
        this.intersection = intersection;
    }

}
