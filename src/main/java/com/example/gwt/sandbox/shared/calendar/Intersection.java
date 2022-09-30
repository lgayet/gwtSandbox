package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Intersection implements Serializable {
    private int numSalarie;
    private int anumIntersec;
    private Niv[] nivs = new Niv[1];
    private List<Tache> aTache = new ArrayList<>();//pour recalculer l'intersection dans le même ordre (eviter le clignotement)

    public Intersection() {
    }

    public Intersection(int numSalarie, int numIntersec, Tache tache) {
        this.numSalarie = numSalarie;
        this.anumIntersec = numIntersec;
        nivs[0] = new Niv(numIntersec, 0, tache);
        aTache.add(tache);
    }

    public int getNumIntersec() {
        return anumIntersec;
    }

    public void setNumIntersec(int numIntersec) {
        this.anumIntersec = numIntersec;
    }

    public int getmaxNiv() {
        return nivs.length;
    }

    public void ajoutTache(Tache tache){
        for(Niv n: nivs){
            if(n.controleEtAjout(this, tache))return;
        }
        ajoutNiv(tache);
        aTache.add(tache);
    }

    public void ajoutArray(Tache tache){
        aTache.add(tache);
    }

    public boolean isEquivalent(Intersection inter, Tache tache){
        return inter.getSommeTachesIntersection(tache) == getSommeTachesIntersection(tache)
                && inter.getSommeNiv(tache) == getSommeNiv(tache);
    }

    public int getSommeNiv(Tache tache){
        int somme=0;
        int i = 1;
        for(Niv n: nivs){
            somme = somme +n.getTaches().length * i;
            i++;
        }
        return somme;
    }

    public int getSommeTachesIntersection(Tache tache){
        int somme=0;
        for(Niv n: nivs){
            for(Tache ta: n.getTaches()){
                if(tache.getMnSelDeb() < ta.getMnSelFin() && tache.getMnSelFin() > ta.getMnSelDeb()){
                    somme += ta.getNumTache();
                }
            }
        }
        return somme;
    }

    public void appliqueTaches(){
        int i=0;
        for(Niv n: nivs){
            for(Tache t: n.getTaches()){
                t.setNiveau(i);
                t.setNumIntersection(anumIntersec);
            }
            i++;
        }
    }
    public Tache[] getTaches2(){
        return aTache.toArray(new Tache[aTache.size()]);
    }

    public Tache[] getTaches(){
        int nbTache =0;
        for(Niv n: nivs)nbTache+=n.getTaches().length;
        Tache[]t = new Tache[nbTache];
        int i=0;
        for(Niv n: nivs){
            for(Tache ta: n.getTaches()){
                t[i]=ta;
                i++;
            }
        }
        return t;
    }

    private void ajoutNiv(Tache tache){
        Niv[] t = new Niv[nivs.length +1];
        for(int i = 0; i < nivs.length; i ++){
            t[i] = nivs[i];
        }
        Niv n = new Niv(anumIntersec, nivs.length, tache);
        t[nivs.length] = n;
        nivs = t;
    }

    public String toString(){
        return "Intersection "+numSalarie+":"+anumIntersec;
    }
}
