package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Tache;

import java.util.logging.Logger;

import static com.example.gwt.sandbox.shared.calendar.Tache.MINUTES_PER_DAY;

public class MoveContext  {

    private GSalarie salarie;
    private Tache tache = null;
    private int numCol;
    private double largCol;
    private int xRef;
    private int yRef;
//    pour le calcul des colonnes à afficher
    private int indicePremiereColonne;
    private int nbJoursAffiches;
    private int minCol;
    private int maxCol;
    private int iter = 0;
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(MoveContext.class.getName());
//    valeurs de la tache pour le re-calcul



    void start(GSalarie salarie, Tache tache, int numCol, double largCol, int indicePremiereColonne, int nbJoursAffiches, int x, int y) {
        if (!isBusy()) {
            LOGGER.info("start "+tache);
            this.salarie = salarie;
            this.tache = tache;
            tache.setMove(true);
            this.numCol = numCol;
            tache.createInitale();
            this.largCol = largCol;
            this.indicePremiereColonne = indicePremiereColonne;
            this.nbJoursAffiches = nbJoursAffiches;
            xRef = x;
            yRef = y;
            minCol = tache.getColSelDeb();
            maxCol = tache.getColSelFin();
        }
    }

    boolean move(int x, int y) {
        if (isBusy()) {
            /*
                TODO: les calculs de décalage se font en minutes
                      joursSelDeb et fin correspondent aux indices du tableau tCols
             */
//            LOGGER.info("MC.move "+tache);
            int precedColSelDeb = tache.getColSelDeb();
            int precedColSelFin = tache.getColSelFin();
            int decalMn = (int)((x -xRef) * MINUTES_PER_DAY / largCol);
            tache.addDecalageMinutes(decalMn);
//            LOGGER.info("MC.move jourSelDeb= "+tache.getColSelDeb()+" jourSelFin= "+tache.getColSelFin()+" precedJourSelDeb= "+precedColSelDeb+" precedJourSelFin= "+precedColSelFin+" pour numTache="+tache.getNumTache()+" iter= "+iter+"\n      "+tache);
            minCol = tache.hasIntersection() ? Math.max(indicePremiereColonne, Math.min(precedColSelDeb, tache.getIntersection().getNumJourMin())) : precedColSelDeb;
            maxCol = tache.hasIntersection() ? Math.min(indicePremiereColonne + nbJoursAffiches -1, Math.max(precedColSelFin, tache.getIntersection().getNumJourMax())) : precedColSelFin;
            salarie.majSalCol(tache, precedColSelDeb, precedColSelFin);
            minCol =indicePremiereColonne;
            maxCol = indicePremiereColonne+nbJoursAffiches-1;
            salarie.majIntersections(minCol,maxCol, tache);
            iter ++;
            return true;
        }
        return false;
    }

    void stop(int x, int y) {
        tache.setMove(false);
        if (move(x, y)) clear();
    }


    int getPremColAff(){
//        if(tache == null)LOGGER.info("getPremColAff avec tache null");
        return minCol;
    }

    int getDernColAff(){
        return maxCol;
    }

    GSalarie getSalarie(){
        return salarie;
    }

    boolean isBusy() {
        return tache != null;
    }

    private void clear() {
        xRef = 0;
        yRef = 0;
        tache = null;
    }
}
