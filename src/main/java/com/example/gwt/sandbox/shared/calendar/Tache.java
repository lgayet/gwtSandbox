package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Tache implements Serializable {

    private TypTache typTache;
    private int numSal;
    private int aanumTache;
    private int joursSelDeb;
    private int hDeb;
    private int mnDeb;
    private int joursSelFin;
    private int hFin;
    private int mnFin;
//
    private int niveau;// l'indice du niveau de dépôt de la tâche
    private boolean move;
//    les taches et intersection
    private transient Salarie salarie;
    private transient Intersection intersection;
    private Integer numIntersection;
    private int oldNiv;
    private Integer oldNumIntersect;
    private Intersection oldIntersection;

    public Tache() {
    }

    public Tache(int numSal, int numTache, int joursSelDeb, int hDeb, int mnDeb, int joursSelFin, int hFin, int mnFin) {
        this.numSal = numSal;
        this.aanumTache = numTache;
        this.joursSelDeb = joursSelDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.joursSelFin = joursSelFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
    }

    public Tache copyTache(){
        return new Tache(numSal, aanumTache, joursSelDeb, hDeb, mnDeb, joursSelFin, hFin, mnFin);
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


    public int getMnSelDeb(){
        return joursSelDeb * 24 * 60 + hDeb * 60 + mnDeb;
    }

    public int getJoursSelDeb() {
        return joursSelDeb;
    }

    public void setJoursSelDeb(int joursSelDeb) {
        this.joursSelDeb = joursSelDeb;
    }

    public int getMnSelFin(){
        return joursSelFin * 24 * 60 + hFin * 60 + mnFin;
    }

    public int getJoursSelFin() {
        return joursSelFin;
    }

    public void setJoursSelFin(int joursSelFin) {
        this.joursSelFin = joursSelFin;
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

    public boolean isIntersection(){
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

    public int getMnDeb() {
        return hDeb * 60 + mnDeb;
    }

    public int getMnFin() {
        return hFin * 60 + mnFin;
    }

    public void setHDeb(int hDeb) {
        this.hDeb = hDeb;
    }

    public void setMnDeb(int mnDeb) {
        this.mnDeb = mnDeb;
    }

    public void setHFin(int hFin) {
        this.hFin = hFin;
    }

    public void setMnFin(int mnFin) {
        this.mnFin = mnFin;
    }

    public String getText(){
        return numIntersection !=null ? aanumTache+"-"+numIntersection : aanumTache+"";
    }

     public String toString(){
        return "Tache "+aanumTache+" "+joursSelDeb+":"+hDeb+":"+mnDeb+"<==>"+" "+joursSelFin+":"+hFin+":"+mnFin+" numIntersec="+numIntersection+" niv= "+niveau;
     }

}
