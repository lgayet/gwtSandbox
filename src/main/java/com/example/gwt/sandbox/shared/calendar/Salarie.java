package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
        for(int i = tache.getColSelDeb(); i <= tache.getColSelFin(); i++) {
            salCols[i].ajoutTache(this, tache,methodeAppelante);
            reorganise(salCols[i].getTaches());
        }
    }
    public void mouvTaches(int colMin, int colMax, Tache tache){
        List<Tache> a = new ArrayList<>();
        for(int i = colMin; i<= colMax; i++){
            for(Tache t: salCols[i].getTaches()){
                if( ! a.contains(t))a.add(t);
            }
        }
        mouvTacheIntersect(a.toArray(new Tache[a.size()]) , tache);
    }
    public void reorganise(Tache[] taches){
        Intersection intersect = null;
        for(Tache t1: taches){
            for(Tache t2: taches) {
                if (t2.getNumTache() != t1.getNumTache() && t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()) {
                    if(t1.hasIntersection() && t2.hasIntersection())t1.getIntersection().fusionne(t2.getIntersection().getTaches());
                    intersect = t1.getIntersection() != null ? t1.getIntersection() : (t2.getIntersection() != null ? t2.getIntersection() : null);
                    if (intersect == null) {
                        intersect = ajoutIntersection(t2);
                                ajoutIntersection(t2);
//                        LOGGER.info("      new  " + intersect + " avec " + t2);
                    }
                    intersect.ajoutTache(t1);
//                    LOGGER.info("      ajoutTache  "+intersect+" pour "+t1);
                }
            }
        }
    }

    public void mouvTacheIntersect(Tache[] taches, Tache tachMouv){
        /*
            Cette methode gère l'impact d'un déplacement de tache avec intersection dans son intersection
            MouvTache devrait créer une intersectionProvisoire sans impact sur les tâches(NumIntersection et niv) et retourner cette intersection
            c'est seulement si cette intersection est différente de l'intersection initiale qu'on procèdera à la prise en compte:
                    copy et ajout de l'intersection dans salarie
                    typage de l'intersection en move
                    ajout de l'intersection initiale dans arrayMove
                    modif des taches
         */
//        LOGGER.info("debut mouvTacheIntersect "+tache.getNumTache()+" pour "+tache);

            for(Tache t: taches)t.sauvIntersect();
            Intersection intersect = null;
            for(Tache t1: taches){
                for(Tache t2: taches){
                    if(t1.getNumTache() != t2.getNumTache()){
                        if(t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()){
                            if(t1.hasIntersection() && t2.hasIntersection() && (int)t1.getNumIntersection() != (int)t2.getNumIntersection()){
//                                LOGGER.info("fusion I "+t1.getNumIntersection()+" <== I "+t2.getNumIntersection()+" sur "+t1.getIntersection()+" de "+t2.getIntersection());
                                t1.getIntersection().fusionne(t2.getIntersection().getTaches());
//                                LOGGER.info(" apres fusion");
                                t2.getIntersection().setSupprimee();
                            }
                            else {
                                Integer numIntersection = t1.hasIntersection() ? t1.getNumIntersection() : (t2.hasIntersection() ? t2.getNumIntersection() : null);
//                                LOGGER.info("boucle t1="+t1+" t2= "+t2);
                                if (numIntersection == null) {
                                    intersect = ajoutIntersectionTemporaire(t2);
//                                    LOGGER.info(" new Intersection= " + intersect + " " + t2);
                                } else {
                                    intersect = getIntersectionTempo(numIntersection);
                                }
                                intersect.ajoutTache(t1);
//                                LOGGER.info("ajoutTache  Intersection= " + intersect + " " + t1);
                            }
                        }
                    }
                }
            }
            if(aInterTempo.size() >= 1){
                for(Tache t: taches){
                    t.removeIntersection();
                }
                for(Intersection i: aInterTempo){
                    if( ! i.isSupprimee()) {
//                        LOGGER.finest("applique " + i);
                        i.setNumIntersec(getNumInter());
                        i.setTypIntersection(TypIntersection.STANDARD);
                        i.appliqueTaches();
                        aInterTempo.remove(i);
                        getAInter().add(i);
                    }
                }
                aInterTempo.clear();
            }
            else{// la tâche n'est plus en intersection
//                LOGGER.info("sortie Intersection pour "+tache+"\n         interBefor= "+interBefore );
                if(taches.length== 2)for(Tache t: taches)t.removeIntersection();
                else for(Tache t: taches){
                    if(t.getNumTache() == tachMouv.getNumTache())t.removeIntersection();
                    else t.restaureIntersect();
                }
            }
//        String s ="fin MovTacheIntersect";
    }

    public int getNumInter(){
        return numInter++;
    }
    private int getNumInterTempo(){
        return numInterTempo ++;
    }



}
