package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Salarie implements Serializable {

    private int numSal;
    private String nomSal;
    private int nbJours;
    private int numInter = 0;
    private int numInterTempo = 1000;
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
        Intersection i = new Intersection(numSal, getNumInter(), TypIntersection.STANDARD, tache);
        aInter.add(i);
        return i;
    }

    public Intersection ajoutIntersectionTemporaire(Tache tache){
        Intersection i = new Intersection(numSal, getNumInterTempo() , TypIntersection.TEMPORAIRE, tache);
        aInterTempo.add(i);
        return i;
    }


    public void ajoutTaches(Tache tache, String methodeAppelante){
        for(int i = tache.getJoursSelDeb(); i <= tache.getJoursSelFin(); i++) {
            salCols[i].ajoutTache(this, tache,methodeAppelante);
        }
    }

    public void mouvTacheIntersect(Tache tache){
        /*
            Cette methode gère l'impact d'un déplacement de tache avec intersection dans son intersection
            MouvTache devrait créer une intersectionProvisoire sans impact sur les tâches(NumIntersection et niv) et retourner cette intersection
            c'est seulement si cette intersection est différente de l'intersection initiale qu'on procèdera à la prise en compte:
                    copy et ajout de l'intersection dans salarie
                    typage de l'intersection en move
                    ajout de l'intersection initiale dans arrayMove
                    modif des taches
         */
        LOGGER.info("mouvTacheIntersect "+tache.getNumTache()+" pour "+tache);
        if(tache.isIntersection()){
            Intersection interBefore = getIntersection(tache.getNumIntersection());
            LOGGER.info("Salarie.mouvTacheIntersect interBefore= "+interBefore);
            Tache[] tachesIntersect = interBefore.getTaches();
            for(Tache t: tachesIntersect)t.sauvIntersect();
            Intersection intersect = null;
            for(Tache t1: tachesIntersect){
                for(Tache t2: tachesIntersect){
                    if(t1.getNumTache() != t2.getNumTache()){
                        if(t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()){
                            if(t1.isIntersection() && t2.isIntersection())t1.getIntersection().fusionne(t2.getIntersection().getTaches());
                            Integer numIntersection = t1.isIntersection() ? t1.getNumIntersection() : t2.isIntersection() ? t2.getNumIntersection() : null;
                            if(numIntersection == null){
                                intersect = ajoutIntersectionTemporaire(t2);
                                LOGGER.info(" new Intersection= "+intersect+" "+t2);
                            }
                            else {
                                intersect = getIntersectionTempo(numIntersection);
                            }
                            intersect.ajoutTache(t1);
                            LOGGER.info("ajoutTache  Intersection= "+intersect+" "+t1);
                        }
                    }
                }
            }
            if(getAInterTempo().size() >= 1){
                for(Tache t: tachesIntersect)t.removeIntersection();
                for(Intersection i: getAInterTempo()){
//                    LOGGER.finest("applique "+i);
                    i.setNumIntersec(getNumInter());
                    i.setTypIntersection(TypIntersection.STANDARD);
                    i.appliqueTaches();
                    getAInterTempo().remove(i);
                    getAInter().add(i);
                }
            }
            else{// la tâche n'est plus en intersection
                LOGGER.info("sortie Intersection pour "+tache+"\n         interBefor= "+interBefore );
                if(tachesIntersect.length== 2)for(Tache t: tachesIntersect)t.removeIntersection();
                else for(Tache t: tachesIntersect){
                    if(t.getNumTache() == tache.getNumTache())t.removeIntersection();
                    else t.restaureIntersect();
                }
            }
        }
        String s ="finMovTache";
    }

    public int getNumInter(){
        return numInter++;
    }
    private int getNumInterTempo(){
        return numInterTempo ++;
    }



}
