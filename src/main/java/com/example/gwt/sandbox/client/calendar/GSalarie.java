package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Tache;

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
            GSalCol g =  new GSalCol(i, sc[i]);
            this.salCols[i] = g;
        }
    }


    public String getNomSal() {
        return salarie.getNomSal();
    }

    public GSalCol[] getGSalCols(){
        return salCols;
    }

    public Intersection getIntersection(Integer numInter){
        return salarie.getIntersection(numInter);
    }

    public void majIntersections(int colMin, int colMax, Tache tache){
        salarie.majIntersections(colMin, colMax, tache);
    }

    public void majSalCol(Tache tache, int precedColSelDeb, int precedColSelFin){
        GSalCol g;
//        LOGGER.info("GSalarie.mouvTacheSalCol tache.joursSelDeb= "+tache.getColSelDeb()+" tache.joursSelFin= "+tache.getColSelFin()+" precedJoursSelDeb= "+precedColSelDeb+" precedJoursSelFin= "+precedColSelFin);
        for(int i = Math.min(tache.getColSelDeb(), precedColSelDeb); i<= Math.max(tache.getColSelFin(), precedColSelFin); i++){
//            pour chaque iteration, je vérifie la presence précédante et pour la tache
            if(containsPrecedEtNonTache(i, tache.getColSelDeb(), tache.getColSelFin(), precedColSelDeb, precedColSelFin)){
//                 je dois donc supprimer de SalCol et GSalCol
                g = salCols[i];
//                LOGGER.info("   commenté: GSalarie.supprimeTacheCol numCol= "+i+" tache= "+tache.getNumTache()+"\n     "+tache+"\n     "+g.getSalCol().getStringTaches());
                g.supprimeTacheCol(tache);// je ne la supprime pas en tant que GTacheCol, je ne veux implement plus créer de Rectangle
            }
            if(containsTacheEtNonPreced(i, tache.getColSelDeb(), tache.getColSelFin(), precedColSelDeb, precedColSelFin)){
                g = salCols[i];
//                LOGGER.info("GSalarie.ajoutTacheCol numCol= "+i+" tache= "+tache.getNumTache()+"\n     "+tache+"\n     "+g.getSalCol().getStringTaches());
                g.ajoutTacheCol(salarie, tache,"mouvTacheSalCol" );
            }
        }

    }
    private boolean containsTacheEtNonPreced(int i,int tacheJD, int tachejF, int precedJD, int precedJF){
        if(i >= tacheJD && i <= tachejF && (i < precedJD || i > precedJF))return true;
        return false;
    }
    private boolean containsPrecedEtNonTache(int i,int tacheJD, int tachejF, int precedJD, int precedJF){
        if((i < tacheJD || i > tachejF) && i >= precedJD && i <= precedJF)return true;
        return false;
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

