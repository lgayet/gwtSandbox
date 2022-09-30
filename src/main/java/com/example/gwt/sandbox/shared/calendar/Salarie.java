package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class Salarie implements Serializable {

    private int numSal;
    private String nomSal;
    private int nbJours;
    private ArrayList<Intersection> aInter = new ArrayList<>();//TODO suppression du transient
    private ArrayList<Intersection> aInterTempo = new ArrayList<>();// créées temporairement (non appliquée si impact non significatif)

    private SalCol[] salCols;



    public Salarie() {
    }

    public Salarie(int numSal, String nomSal, int nbJours) {
        this.numSal = numSal;
        this.nomSal = nomSal;
        this.nbJours = nbJours;
        salCols = new SalCol[nbJours];
        for(int i = 0; i< nbJours; i++){
            salCols[i] = new SalCol(i);
        }
    }

    public int getNumSal() {
        return numSal;
    }

    public String getNomSal() {
        return nomSal;
    }

    public SalCol[] getSalCols() {
        return salCols;
    }


    public Intersection getIntersection(Integer numInter){
//        if(intersections != null)return intersections[numInter];// on est sur GWT
        for(Intersection i: aInter){// on est en création des tâches
            if(i.getNumIntersec() == numInter)return i;
        }
        return null;
    }

    public Intersection getIntersectionTempo(Integer numInter){
//        if(intersections != null)return intersections[numInter];// on est sur GWT
        for(Intersection i: aInterTempo){// on est en création des tâches
            if(i.getNumIntersec() == numInter)return i;
        }
        return null;
    }

    public void removeIntersection(Intersection intersection){
        aInter.remove(intersection);
    }

    public ArrayList<Intersection> getAInter() {
        return aInter;
    }

    public ArrayList<Intersection> getAInterTempo() {
        return aInterTempo;
    }

    public Intersection ajoutIntersection(Tache tache, boolean move){
        Intersection i = new Intersection(numSal, move ? aInterTempo.size()+100 : aInter.size(), tache);
        if(move)aInterTempo.add(i);
        else aInter.add(i);
        return i;
    }


    public void ajoutTaches(Tache tache){
        for(int i = tache.getJoursSelDeb(); i <= tache.getJoursSelFin(); i++) {
            salCols[i].ajoutTache(this, tache);
        }
    }






}
