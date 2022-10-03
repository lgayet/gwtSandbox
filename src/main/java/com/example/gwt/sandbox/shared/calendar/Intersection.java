package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Intersection implements Serializable {
    private int numSalarie;
    private int anumIntersec;//TODO j'ai ajouté le a pour voir cette information en premier en debug
    private TypIntersection typIntersection;
    private Niv[] nivs = new Niv[1];

    public Intersection() {
    }

    public Intersection(int numSalarie, int numIntersec, TypIntersection typIntersection, Tache tache) {
        this.numSalarie = numSalarie;
        this.anumIntersec = numIntersec;
        this.typIntersection = typIntersection;
        nivs[0] = new Niv(this, numIntersec, 0, tache);
    }

    public void rattache(){
        for(Niv n: nivs){
            n.setIntersection(this);
        }
    }

    public int getNumIntersec() {
        return anumIntersec;
    }

    public void setNumIntersec(int numIntersec) {
        this.anumIntersec = numIntersec;
    }

    public TypIntersection getTypIntersection() {
        return typIntersection;
    }

    public void setTypIntersection(TypIntersection typIntersection) {
        this.typIntersection = typIntersection;
    }

    public int getmaxNiv() {
        return nivs.length;
    }

    public void ajoutTache(Tache tache){
        for(Niv n: nivs){
            if(n.controleEtAjout(this, tache))return;
        }
        ajoutNiv(tache);
    }

    public void appliqueTaches(){
        int i=0;
        for(Niv n: nivs){
            for(Tache t: n.getTaches()){
                t.setNiveau(i);
                t.setIntersection(this);
            }
            i++;
        }
    }

    public Tache[] getTaches(){
        int nbTache =0;
        for(Niv n: nivs)nbTache+=n.getTaches().length;
        Tache[]t = new Tache[nbTache];
        int i=0;
        for(Niv n: nivs){
            for(Tache ta: n.getTaches()){
                if(i == 0)t[1]=ta;
                else if(i == 1)t[0]=ta;
                else t[i]=ta;
                i++;
            }
        }
        return t;
    }
    public Intersection fusionne(Tache[] tachesAFusionner){
        for(Tache t: tachesAFusionner){
            ajoutTache(t);
        }
        return this;
    }

    public int getNumJourMin(){
        int jourMin = 9999;
        for(Tache t: getTaches())jourMin = Math.min(jourMin, t.getJoursSelDeb());
        return jourMin;
    }

    public int getNumJourMax(){
        int jourMax = 0;
        for(Tache t: getTaches())jourMax = Math.max(jourMax, t.getJoursSelFin());
        return jourMax;
    }

    public String getStringTaches(){
        String s =" [";
        String sep ="";
        for(Niv n: nivs){
            for(Tache t: n.getTaches()){
                s = s+sep+t.getNumTache();
                sep=";";
            }
        }
        return s+"]";
    }

    private void ajoutNiv(Tache tache){
        Niv[] t = new Niv[nivs.length +1];
        for(int i = 0; i < nivs.length; i ++){
            t[i] = nivs[i];
        }
        Niv n = new Niv(this, anumIntersec, nivs.length, tache);
        t[nivs.length] = n;
        nivs = t;
    }

    public String toString(){
        return "Intersection "+typIntersection+" "+anumIntersec+getStringTaches();
    }
}
