package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Tache implements Serializable {

    private TypTache typTache;
    private int aanumTache;
    private int anneDeb;
    private int moisDeb;
    private int jourDeb;
    private int hDeb;
    private int mnDeb;
    private int joursSelDeb;
    private int anneFin;
    private int moisFin;
    private int jourFin;
    private int hFin;
    private int mnFin;
    private int joursSelFin;
//
    public int numColDeb;
    public int numColFin;
    private int niveau;// l'indice du niveau de dépôt de la tâche
    private boolean move;
//    les taches et intersection
    private Integer numIntersection;
    private int oldNiv;
    private Integer oldNumIntersect;

    public Tache() {
    }

    public Tache(int numTache, int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, int joursSelDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, int joursSelFin, int numColDeb, int numColFin) {
        this.aanumTache = numTache;
        this.anneDeb = anneDeb;
        this.moisDeb = moisDeb;
        this.jourDeb = jourDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.joursSelDeb = joursSelDeb;
        this.anneFin = anneFin;
        this.moisFin = moisFin;
        this.jourFin = jourFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
        this.joursSelFin = joursSelFin;
        this.numColDeb = numColDeb;
        this.numColFin = numColFin;
    }

    public Tache copyTache(){
        return new Tache(aanumTache, anneDeb, moisDeb, jourDeb, hDeb, mnDeb, joursSelDeb, anneFin, moisFin, jourFin, hFin, mnFin, joursSelFin, numColDeb, numColFin);
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getAnneDeb() {
        return anneDeb;
    }

    public int getMoisDeb() {
        return moisDeb;
    }

    public int getJourDeb() {
        return jourDeb;
    }

    public int getAnneFin() {
        return anneFin;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public int getJourFin() {
        return jourFin;
    }

    public int getNumTache() {
        return aanumTache;
    }

    public int getNumColDeb() {
        return numColDeb;
    }

    public int getNumColFin() {
        return numColFin;
    }

    public int getMnSelDeb(){
        return joursSelDeb * 24 * 60 + hDeb * 60 + mnDeb;
    }

    public int getJoursSelDeb() {
        return joursSelDeb;
    }

    public int getMnSelFin(){
        return joursSelFin * 24 * 60 + hFin * 60 + mnFin;
    }

    public int getJoursSelFin() {
        return joursSelFin;
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

    public void setNumIntersection(Integer numIntersection) {
        this.numIntersection = numIntersection;
    }

    public boolean isIntersection(){
        return numIntersection != null;
    }

    public void removeIntersection(){
        niveau = 0;
        numIntersection = null;
    }

    public void restaureIntersect(){
        niveau = oldNiv;
        numIntersection = oldNumIntersect;
    }

    public void sauvIntersect(){
        oldNiv = niveau;
        oldNumIntersect = numIntersection;
        niveau = 0;
        numIntersection = null;
    }

    public int gethDeb() {
        return hDeb;
    }

    public int getMnDeb() {
        return mnDeb;
    }

    public int gethFin() {
        return hFin;
    }

    public int getMnFin() {
        return mnFin;
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



    public double getHDebDecim(){
        return hDeb + mnDeb / 60.0;
    }
    public double getHFinDecim(){
        return hFin +  mnFin / 60.0;
     }

     public String toString(){
        return "Tache "+aanumTache+"-"+anneDeb+"-"+moisDeb+"-"+jourDeb+" "+hDeb+":"+mnDeb+"<==>"+anneFin+"-"+moisFin+"-"+jourFin+" "+hFin+":"+mnFin+" numIntersec="+numIntersection+" niv= "+niveau;
     }

}
