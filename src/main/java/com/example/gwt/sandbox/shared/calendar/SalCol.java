package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SalCol implements Serializable {


    private int numCol;
    private Tache[]taches = new Tache[0];
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(SalCol.class.getName());


    public SalCol() {
    }

    public SalCol(int numCol) {
        this.numCol = numCol;
    }

    public Tache[] getTaches() {
        return taches;
    }

    public Tache[] supprimeTache(Tache tache){
//        la tache devrait exister et a précédemment été retirée de son intersection éventuelle
        if(existeTache(tache)) {
            ArrayList<Tache> a = new ArrayList<>();
            for(Tache t: taches)a.add(t);
            a.remove(tache);
            taches = a.toArray(new Tache[a.size()]);
        }
        return taches;

    }

    public void mouvTache(Salarie salarie){
        reorganise(salarie,"mouvTache");
    }

    public void ajoutTache(Salarie salarie, Tache tache, String methodeAppelante){// pour le moment, les taches sont nouvelles, donc sans intersection
        Tache[] t = new Tache[taches.length + 1];
        for(int i = 0; i < taches.length; i ++){
            t[i] = taches[i];
        }
        t[taches.length] = tache;
        taches = t;
        reorganise(salarie,"ajoutTache <== "+methodeAppelante);
    }



    private void reorganise(Salarie salarie, String methodeAppelante){
        Intersection intersect = null;
        LOGGER.info("SC:reorganise numCol=  "+numCol+" appelé par "+methodeAppelante+"  "+getStringTaches()+"\n     salarie.aInter.size= "+salarie.getAInter().size());
        for(Tache t1: taches){
            for(Tache t2: taches) {
                if (t2.getNumTache() != t1.getNumTache() && t1.getMnSelDeb() < t2.getMnSelFin() && t1.getMnSelFin() > t2.getMnSelDeb()) {
                    if(t1.isIntersection() && t2.isIntersection())t1.getIntersection().fusionne(t2.getIntersection().getTaches());
                    intersect = t1.getIntersection() != null ? t1.getIntersection() : (t2.getIntersection() != null ? t2.getIntersection() : null);
                    if (intersect == null) {
                        intersect = salarie.ajoutIntersection(t2);
                        LOGGER.info("      new  " + intersect + " avec " + t2);
                    }
                    intersect.ajoutTache(t1);
                    LOGGER.info("      ajoutTache  "+intersect+" pour "+t1);
                }
            }
        }
    }

    private boolean existeTache(Tache tache){
        for(Tache t: taches)if(t.getNumTache() == tache.getNumTache())return true;
        return false;
    }

    public String getStringTaches(){
        String s = "[";
        String sep = "";
        for(Tache t: taches){
            s = s+sep+t;
            sep=";";
        }
        s = s+"]";
        return s;
    }
}
