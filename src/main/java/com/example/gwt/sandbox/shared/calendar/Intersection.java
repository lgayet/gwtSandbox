package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Intersection implements Serializable {
    private int numIntersec;
    private Niv[] nivs = new Niv[1];

    public Intersection() {
    }

    public Intersection(int numIntersec, Tache tache) {
        this.numIntersec = numIntersec;
        nivs[0] = new Niv(numIntersec, 0, tache);
    }

    public int getNumIntersec() {
        return numIntersec;
    }

    public int getmaxNiv() {
        return nivs.length;
    }

    public void ajoutTache(Tache tache){
        for(Niv n: nivs){
            if(n.controleEtAjout(tache))return;
        }
        ajoutNiv(tache);

    }

    private void ajoutNiv(Tache tache){
        Niv[] t = new Niv[nivs.length +1];
        for(int i = 0; i < nivs.length; i ++){
            t[i] = nivs[i];
        }
        Niv n = new Niv(numIntersec, nivs.length, tache);
        t[nivs.length] = n;
        nivs = t;
    }
}
