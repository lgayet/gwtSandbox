package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Tache;

import java.util.logging.Logger;

public class MoveContext  {

    private GSalarie salarie;
    private Tache tache = null;
    private int numCol;
    private Tache tacheInitiale;
    private double largCol;
    private int xRef;
    private int yRef;
//    pour le calcul des colonnes à afficher
    private int indicePremiereColonne;
    private int nbJoursAffiches;
    private int precedJourSelDeb;
    private int precedJourSelFin;
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
            tacheInitiale = tache.copyTache();
            this.largCol = largCol;
            this.indicePremiereColonne = indicePremiereColonne;
            this.nbJoursAffiches = nbJoursAffiches;
            xRef = x;
            yRef = y;
            precedJourSelDeb = tache.getJoursSelDeb();
            precedJourSelFin = tache.getJoursSelFin();
            minCol= precedJourSelDeb;
            maxCol = precedJourSelFin;
        }
    }

    boolean move(int x, int y) {
        if (isBusy()) {
            LOGGER.info("MC.move "+tache);
            precedJourSelDeb = tache.getJoursSelDeb();
            precedJourSelFin = tache.getJoursSelFin();
            int decalMn = (int)((x -xRef) * 1440 / largCol);// TODO on calcule toujours le décalage par rapport à la position initiale
            int mnSelDeb = tacheInitiale.getMnSelDeb() + decalMn;
            tache.setJoursSelDeb(mnSelDeb / 1440);
            tache.setHDeb(mnSelDeb % 1440 / 60 );
            tache.setMnDeb(mnSelDeb % 1440 % 60 );
            int mnSelFin = tacheInitiale.getMnSelFin() + decalMn;
            tache.setJoursSelFin(mnSelFin / 1440);
            tache.setHFin(mnSelFin % 1440 / 60 );
            tache.setMnFin(mnSelFin % 1440 % 60);
            salarie.mouvTacheIntersect(tache);
            LOGGER.info("MC.move jourSelDeb= "+tache.getJoursSelDeb()+" jourSelFin= "+tache.getJoursSelFin()+" precedJourSelDeb= "+precedJourSelDeb+" precedJourSelFin= "+precedJourSelFin+" pour numTache="+tache.getNumTache()+" iter= "+iter+"\n      "+tache);
            minCol = tache.isIntersection() ? Math.max(indicePremiereColonne, Math.min(precedJourSelDeb, tache.getIntersection().getNumJourMin())) : precedJourSelDeb;
            maxCol = tache.isIntersection() ? Math.min(indicePremiereColonne + nbJoursAffiches -1, Math.max(precedJourSelFin, tache.getIntersection().getNumJourMax())) : precedJourSelFin;
            minCol = indicePremiereColonne;
            maxCol = indicePremiereColonne + nbJoursAffiches -1;
            salarie.mouvTacheSalCol(tache, precedJourSelDeb, precedJourSelFin);
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
