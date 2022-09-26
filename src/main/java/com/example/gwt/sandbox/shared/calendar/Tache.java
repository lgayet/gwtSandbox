package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Tache implements Serializable {

    private TypTache typTache;
    private int numTache;
    private int anneDeb;
    private int moisDeb;
    private int jourDeb;
    private int hDeb;
    private int mnDeb;
    private long longDeb;
    private int anneFin;
    private int moisFin;
    private int jourFin;
    private int hFin;
    private int mnFin;
    private long longFin;
//
    public int numColDeb;
    public int numColFin;
    private int niveau;// l'indice du niveau de dépôt de la tâche


//    les taches et intersection
    private Integer numIntersection;

    public Tache() {
    }

    public Tache(int numTache, int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, long longDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, long longFin, int numColDeb, int numColFin) {
        this.numTache = numTache;
        this.anneDeb = anneDeb;
        this.moisDeb = moisDeb;
        this.jourDeb = jourDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.longDeb = longDeb;
        this.anneFin = anneFin;
        this.moisFin = moisFin;
        this.jourFin = jourFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
        this.longFin = longFin;
        this.numColDeb = numColDeb;
        this.numColFin = numColFin;
    }

    public Tache copyTache(){
        return new Tache(numTache, anneDeb, moisDeb, jourDeb, hDeb, mnDeb, longDeb, anneFin, moisFin, jourFin, hFin, mnFin, longFin, numColDeb, numColFin);
    }




    public int getNumTache() {
        return numTache;
    }

    public int getNumColDeb() {
        return numColDeb;
    }

    public int getNumColFin() {
        return numColFin;
    }

    public long getLongDeb() {
        return longDeb;
    }

    public long getLongFin() {
        return longFin;
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

}
