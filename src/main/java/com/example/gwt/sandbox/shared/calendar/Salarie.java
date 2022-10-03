package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Salarie implements Serializable {

    private int numSal;
    private String nomSal;
    private int nbJours;
    private Intersection[]tInter;//TODO tableau transitoire servant à restaurer les liens à l'arrivée sur GCalendar
    private ArrayList<Intersection> aInter = new ArrayList<>();//TODO suppression du transient
    private ArrayList<Intersection> aInterTempo = new ArrayList<>();// créées temporairement (non appliquée si impact non significatif)
    private SalCol[] salCols;
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(Salarie.class.getName());



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

    public void rattache(){
        for(Intersection i: getTIntersection()){
            i.rattache();
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


    public Intersection[] getTIntersection(){
        if(tInter == null)tInter = aInter.toArray(new Intersection[aInter.size()]);
        return tInter;
    }

    public ArrayList<Intersection> getAInter() {
        return aInter;
    }

    public ArrayList<Intersection> getAInterTempo() {
        return aInterTempo;
    }

    public Intersection ajoutIntersection(Tache tache){
        Intersection i = new Intersection(numSal, aInter.size(), TypIntersection.STANDARD, tache);
        aInter.add(i);
        LOGGER.info("Salarie apres ajoutIntersection aInter.size = "+aInter.size());
        return i;
    }

    public Intersection ajoutIntersectionTemporaire(Tache tache){
        Intersection i = new Intersection(numSal, aInterTempo.size()+100 , TypIntersection.TEMPORAIRE, tache);
        aInterTempo.add(i);
        return i;
    }


    public void ajoutTaches(Tache tache){
        for(int i = tache.getJoursSelDeb(); i <= tache.getJoursSelFin(); i++) {
            salCols[i].ajoutTache(this, tache);
        }
    }






}
