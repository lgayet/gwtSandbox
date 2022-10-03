package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.*;

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
            GSalCol g =  new GSalCol(i, sc[i]);
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
        for(int i = ta.getJoursSelDeb(); i <= ta.getJoursSelFin(); i++){
            tc.add(salCols[i].getTacheCol(numTache));
        }
        return tc.toArray(new GTacheCol[tc.size()]);
    }

    public Intersection getIntersection(Integer numInter){
        return salarie.getIntersection(numInter);
    }

    public void mouvTacheSalCol(Tache tache, int precedJourSelDeb, int precedJourSelFin){
        GSalCol g;
        LOGGER.info("GSalarie.mouvTacheSalCol tache.joursSelDeb= "+tache.getJoursSelDeb()+" tache.joursSelFin= "+tache.getJoursSelFin()+" precedJoursSelDeb= "+precedJourSelDeb+" precedJoursSelFin= "+precedJourSelFin);
        for(int i = Math.min(tache.getJoursSelDeb(), precedJourSelDeb); i<= Math.max(tache.getJoursSelFin(), precedJourSelFin); i++){
//            pour chaque iteration, je vérifie la presence précédante et pour la tache
            if(appatientPrecedEtNonTache(i, tache.getJoursSelDeb(), tache.getJoursSelFin(), precedJourSelDeb, precedJourSelFin)){
//                 je dois donc supprimer de SalCol et GSalCol
                g = salCols[i];
                LOGGER.info("   commenté: GSalarie.supprimeTacheCol numCol= "+i+" tache= "+tache.getNumTache()+"\n     "+tache+"\n     "+g.getSalCol().getStringTaches());
                g.supprimeTacheCol(tache);// je ne la supprime pas en tant que GTacheCol, je ne veux implement plus créer de Rectangle
            }
            if(appatientTacheEtNonPreced(i, tache.getJoursSelDeb(), tache.getJoursSelFin(), precedJourSelDeb, precedJourSelFin)){
                g = salCols[i];
                LOGGER.info("GSalarie.ajoutTacheCol numCol= "+i+" tache= "+tache.getNumTache()+"\n     "+tache+"\n     "+g.getSalCol().getStringTaches());
                g.ajoutTacheCol(salarie, tache);
            }
            if(appatientTacheEtPreced(i, tache.getJoursSelDeb(), tache.getJoursSelFin(), precedJourSelDeb, precedJourSelFin)){
                g = salCols[i];
                LOGGER.info("GSalarie.mouvTacheCol numCol= "+i+" tache= "+tache.getNumTache()+"\n     "+tache+"\n     "+g.getSalCol().getStringTaches());
                g.mouvTacheCol(salarie, tache);
            }
        }
    }
    private boolean appatientTacheEtPreced(int i,int tacheJD, int tachejF, int precedJD, int precedJF){
        if(i >= tacheJD && i <= tachejF && i >= precedJD && i <= precedJF)return true;
        return false;
    }
    private boolean appatientTacheEtNonPreced(int i,int tacheJD, int tachejF, int precedJD, int precedJF){
        if(i >= tacheJD && i <= tachejF && (i < precedJD || i > precedJF))return true;
        return false;
    }
    private boolean appatientPrecedEtNonTache(int i,int tacheJD, int tachejF, int precedJD, int precedJF){
        if((i < tacheJD || i > tachejF) && i >= precedJD && i <= precedJF)return true;
        return false;
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
        LOGGER.info("mouvTacheIntersect "+tache.getNumTache()+" numMov= "+numMove+" pour "+tache);
        if(tache.isIntersection()){
            Intersection interBefore = salarie.getIntersection(tache.getNumIntersection());
            LOGGER.info("GSalarie.mouvTacheIntersect interBefore= "+interBefore);
            Tache[] tachesIntersect = interBefore.getTaches();
            for(Tache t: tachesIntersect)t.sauvIntersect();
            Intersection intersect = null;
            for(Tache t1: tachesIntersect){
                for(Tache t2: tachesIntersect){
                    if(t1.getNumTache() != t2.getNumTache()){
                        if(t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()){
                            Integer numIntersection = t1.isIntersection() ? t1.getNumIntersection() : t2.isIntersection() ? t2.getNumIntersection() : null;
                            if(numIntersection == null){
                                intersect = salarie.ajoutIntersectionTemporaire(t2);
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
            if(salarie.getAInterTempo().size() >= 1){
                for(Tache t: tachesIntersect)t.removeIntersection();
                for(Intersection i: salarie.getAInterTempo()){
//                    LOGGER.finest("applique "+i);
                    i.setNumIntersec(salarie.getAInter().size());
                    i.setTypIntersection(TypIntersection.STANDARD);
                    i.appliqueTaches();
                    salarie.getAInterTempo().remove(i);
                    salarie.getAInter().add(i);
                };
            }
            else{
                if(tachesIntersect.length== 2)for(Tache t: tachesIntersect)t.removeIntersection();
                else for(Tache t: tachesIntersect)t.restaureIntersect();
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

