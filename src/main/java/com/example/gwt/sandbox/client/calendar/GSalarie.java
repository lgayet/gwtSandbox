package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Tache;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GSalarie  {
    private GCalendar calendar;
    private Salarie salarie;
    private GSalCol[] salCols;
//    informations d'affichage
    private int positionY;
    double hauteurSal;
    int numMove = 0;

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(GSalarie.class.getName());


    public GSalarie(GCalendar calendar, Salarie salarie) {
        this.calendar = calendar;
        this.salarie = salarie;
        SalCol[] sc = salarie.getSalCols();
        this.salCols = new GSalCol[sc.length];
        for(int i =0; i< sc.length; i ++){
            GSalCol g =  new GSalCol(sc[i]);
            this.salCols[i] = g;
        }
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public String getNomSal() {
        return salarie.getNomSal();
    }

    public GSalCol[] getGSalCols(){
        return salCols;
    }

    public SalCol[] getSalCols() {
        return salarie.getSalCols();
    }

    public GTacheCol[] getTachesCol(int numTache){
        Tache ta = calendar.getTaches()[numTache];
        List<GTacheCol> tc = new ArrayList<>();
        for(int i = ta.getNumColDeb(); i <= ta.getNumColFin(); i++){
            tc.add(salCols[i].getTacheCol(numTache));
        }
        return tc.toArray(new GTacheCol[tc.size()]);
    }

    public Intersection getIntersection(Integer numInter){
        return salarie.getIntersection(numInter);
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
        numMove++;
        LOGGER.info("mouvTache "+tache.getNumTache()+" numMov= "+numMove+" pour "+this);
        if(tache.isIntersection()){
            Intersection interBefore = salarie.getIntersection(tache.getNumIntersection());
            int sommeTachesIntersect= interBefore.getSommeTachesIntersection(tache);
            Tache[] tachesIntersect = interBefore.getTaches();
            for(Tache t: tachesIntersect)t.sauvIntersect();
            Intersection intersect = null;
            for(Tache t1: tachesIntersect){
                for(Tache t2: tachesIntersect){
                    if(t1.getNumTache() != t2.getNumTache()){
                        if(t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()){
                            Integer numIntersection = t1.isIntersection() ? t1.getNumIntersection() : t2.isIntersection() ? t2.getNumIntersection() : null;
                            if(numIntersection == null){
                                intersect = salarie.ajoutIntersection(t2, true);
                                LOGGER.info(" new Intersection= "+intersect+" "+t2);
                            }
                            else {
                                intersect = salarie.getIntersectionTempo(numIntersection);
                            }
                            intersect.ajoutTache(t1);
                            LOGGER.info("ajoutTache  Intersection= "+intersect+" "+t1);
                        }
                    }
                }
            }
            Integer somm1 = intersect != null ? intersect.getSommeTachesIntersection(tache) : null;
            Integer somm2= salarie.getAInterTempo().size() == 1 ? salarie.getAInterTempo().get(0).getSommeTachesIntersection(tache): null;
            LOGGER.finest("size= "+salarie.getAInterTempo().size()+" sommeTachesIntersect= "+sommeTachesIntersect+"  somm1= "+somm1+"  somm2= "+somm2);
            if(salarie.getAInterTempo().size() == 1 && salarie.getAInterTempo().get(0).getSommeTachesIntersection(tache) == sommeTachesIntersect){
//                dans ce cas, je n'ai rien à faire
                for(Tache t: tachesIntersect)t.restaureIntersect();
                salarie.getAInterTempo().clear();
            }
            else{
                for(Tache t: tachesIntersect)t.removeIntersection();
                for(Intersection i: salarie.getAInterTempo()){
                    LOGGER.finest("applique "+i);
                    i.setNumIntersec(salarie.getAInter().size());
                    i.appliqueTaches();
                    salarie.getAInterTempo().remove(i);
                    salarie.getAInter().add(i);
                };
            }
        }
        String s ="finMovTache";
    }


    public int getPositionY() {
        return positionY;
    }

    public double getHauteurSal() {
        return hauteurSal;
    }

    public void setPositionY(int positionY, double hauteurSal) {
        this.positionY = positionY;
        this.hauteurSal = hauteurSal;
    }

    public String toString(){
        return "GSalarie "+salarie.getNomSal();
    }
}

