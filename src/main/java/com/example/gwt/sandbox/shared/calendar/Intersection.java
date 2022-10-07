package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Intersection implements Serializable {
    private int numSalarie;
    private int anumIntersec;//TODO j'ai ajouté le a pour voir cette information en premier en debug
    private TypIntersection typIntersection;
    private Niv[] nivs = new Niv[1];
    private boolean supprimee;
    private List<Tache>tachesOrdonnees = new ArrayList<>();

    public Intersection() {
    }

    public Intersection(int numSalarie, int numIntersec, TypIntersection typIntersection, Tache tache) {
        this.numSalarie = numSalarie;
        this.anumIntersec = numIntersec;
        this.typIntersection = typIntersection;
        nivs[0] = new Niv(this, 0, tache);
        tache.setIntersection(this);
        tachesOrdonnees.add(tache);
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
        tache.setIntersection(this);
        for(Niv n: nivs){
            if(n.controleEtAjout(tache))return;
        }
        ajoutNiv(tache);
        tachesOrdonnees.add(tache);
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
    public Tache[] getTachesOrdonnees(){
        return tachesOrdonnees.toArray(new Tache[tachesOrdonnees.size()]);
    }

    public Tache[] getTaches(){
        if(supprimee)return new Tache[0];
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


    public Intersection fusionne(Tache[] tachesAFusionner){
        for(Tache t: tachesAFusionner){
            ajoutTache(t);
        }
        return this;
    }

    public Tache getTacheMin(){
        Tache tacheMin = nivs[0].getTaches()[0] ;//pour avoir une valeur de départ
        for(Tache t: getTaches())tacheMin = t.getMnSelDeb() < tacheMin.getMnSelDeb() ? t : tacheMin;
        return tacheMin;
    }
    public Tache getTacheMax(){
        Tache tacheMax = nivs[0].getTaches()[0] ;//pour avoir une valeur de départ
        for(Tache t: getTaches())tacheMax = t.getMnSelFin() > tacheMax.getMnSelFin() ? t : tacheMax;
        return tacheMax;
    }

    public int getNumJourMin(){
        int jourMin = 9999;
        for(Tache t: getTaches())jourMin = Math.min(jourMin, t.getColSelDeb());
        return jourMin;
    }

    public int getNumJourMax(){
        int jourMax = 0;
        for(Tache t: getTaches())jourMax = Math.max(jourMax, t.getColSelFin());
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
        Niv n = new Niv(this, nivs.length, tache);
        t[nivs.length] = n;
        nivs = t;
    }

    public boolean isSupprimee() {
        return supprimee;
    }

    public void setSupprimee() {
        this.supprimee = true;
    }

    public String toString(){
        return "Intersection "+typIntersection+" "+(supprimee ? " supprimée ": "")+anumIntersec+getStringTaches();
    }
}
