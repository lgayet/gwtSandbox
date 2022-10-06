package com.example.gwt.sandbox.shared.calendar;

import javax.annotation.Nullable;
import java.io.Serializable;

public class Tache implements Serializable {

    public final static int MINUTES_PER_HOUR = 60;
    public final static int MINUTES_PER_DAY = MINUTES_PER_HOUR * 24;

    private TypTache typTache;
    private int numSal;
    private int aanumTache;
    private int colSelDeb;
    private int hDeb;
    private int mnDeb;
    private int colSelFin;
    private int hFin;
    private int mnFin;
//
    private int niveau;// l'indice du niveau de dépôt de la tâche
    private boolean move;
//    les taches et intersection
    private transient Salarie salarie;
    @Nullable
    private transient Intersection intersection;
    @Nullable
    private Integer numIntersection;
    private int oldNiv;
    private Integer oldNumIntersect;
    private Intersection oldIntersection;
    // La tache initale sert à garder l'état pour les calcul de decalage lors du déplacement engendré par MoveContext
    private Tache tacheInitiale;

    public Tache() {
    }

    public Tache(int numSal, int numTache, int colSelDeb, int hDeb, int mnDeb, int colSelFin, int hFin, int mnFin) {
        this.numSal = numSal;
        this.aanumTache = numTache;
        this.colSelDeb = colSelDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.colSelFin = colSelFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
    }

    public void createInitale(){
        tacheInitiale = new Tache(numSal, aanumTache, colSelDeb, hDeb, mnDeb, colSelFin, hFin, mnFin);
    }

    public void rattache(Salarie[] tSals){
        salarie = tSals[numSal];
        if(numIntersection != null)intersection = salarie.getTIntersection()[numIntersection];
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getNumTache() {
        return aanumTache;
    }

    public void addDecalageMinutes (int minutes){

        int totMnDeb = tacheInitiale.getMnSelDeb() + minutes;
        colSelDeb = totMnDeb / MINUTES_PER_DAY;
        hDeb = totMnDeb % MINUTES_PER_DAY / MINUTES_PER_HOUR;
        mnDeb = totMnDeb % MINUTES_PER_DAY % MINUTES_PER_HOUR;

        int totMnFin = tacheInitiale.getMnSelFin() + minutes;
        colSelFin = totMnFin / MINUTES_PER_DAY;
        hFin = totMnFin % MINUTES_PER_DAY / MINUTES_PER_HOUR;
        mnFin = totMnFin % MINUTES_PER_DAY % MINUTES_PER_HOUR;
    }

    public int getMnSelDeb(){
        return colSelDeb * MINUTES_PER_DAY + hDeb * MINUTES_PER_HOUR + mnDeb;
    }

    public int getColSelDeb() {
        return colSelDeb;
    }

    public int getMnSelFin(){
        return colSelFin * MINUTES_PER_DAY + hFin * MINUTES_PER_HOUR + mnFin;
    }

    public int getColSelFin() {
        return colSelFin;
    }

    public int getMnDeb() {
        return hDeb * MINUTES_PER_HOUR + mnDeb;
    }

    public int getMnFin() {
        return hFin * MINUTES_PER_HOUR + mnFin;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public Integer getNumIntersection(){
        return numIntersection;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection){
        this.intersection = intersection;
            numIntersection = intersection.getNumIntersec();
    }

    public boolean hasIntersection(){
        return numIntersection != null;
    }

    public void removeIntersection(){
        niveau = 0;
        numIntersection = null;
        intersection= null;
        oldNiv = 0;
        oldNumIntersect = null;
        oldIntersection = null;
    }

    public void restaureIntersect(){
        niveau = oldNiv;
        numIntersection = oldNumIntersect;
        intersection = oldIntersection;
    }

    public void sauvIntersect(){
        oldNiv = niveau;
        oldNumIntersect = numIntersection;
        oldIntersection = intersection;
        niveau = 0;
        numIntersection = null;
        intersection = null;
    }

    public String getText(){
        return numIntersection !=null ? aanumTache+"-"+numIntersection : aanumTache+"";
    }

     public String toString(){
        return "Tache "+aanumTache+" "+colSelDeb+":"+hDeb+":"+mnDeb+"<==>"+" "+colSelFin+":"+hFin+":"+mnFin+" numIntersec="+numIntersection+" niv= "+niveau;
     }

}
