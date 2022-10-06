package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;


public class Niv implements Serializable {

    private int indice;
    private Tache[]taches = new Tache[1];

    public Niv() {
    }

    public Niv(int indice, Tache tache) {
        this.indice = indice;
        tache.setNiveau(indice);
        taches[0] = tache;
    }


    public Tache[] getTaches() {
        return taches;
    }

    public boolean controleEtAjout(Tache tache){
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
        tache.setNiveau(indice);
        t[taches.length] = tache;
        taches = t;
        return true;
    }

}
