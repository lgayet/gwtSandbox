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

    public void mouvTache(Salarie salarie, Tache tache){
        Tache[]tachesIntersect =  new Tache[0];
        if(tache.isIntersection()) tachesIntersect = tache.getIntersection().getTaches();
        ajoutIntersection(salarie);
//        for(Tache t: tachesIntersect)ajoutIntersection(salarie);
    }

    public void ajoutTache(Salarie salarie, Tache tache){// pour le moment, les taches sont nouvelles, donc sans intersection
        Tache[] t = new Tache[taches.length + 1];
        for(int i = 0; i < taches.length; i ++){
            t[i] = taches[i];
        }
        t[taches.length] = tache;
        taches = t;
        ajoutIntersection(salarie);
    }

    private void ajoutIntersection(Salarie salarie){
        Intersection intersect = null;
        LOGGER.info("SC:ajoutIntersection-0 "+getStringTaches()+"\n     salarie.aInter.size= "+salarie.getAInter().size());
        for(Tache t: taches){
            for(Tache tache: taches) {
                if (tache.getNumTache() != t.getNumTache() && t.getMnSelDeb() < tache.getMnSelFin() && t.getMnSelFin() > tache.getMnSelDeb()) {
                    intersect = t.getIntersection() != null ? t.getIntersection() : (tache.getIntersection() != null ? tache.getIntersection() : null);
                    if (t.getNumIntersection() == null) {
                        intersect = salarie.ajoutIntersection(t);
                        LOGGER.info("SC:ajoutIntersection new  "+intersect+" avec "+t);
                    } else {
                        intersect = salarie.getIntersection(t.getNumIntersection());
                    }
                    intersect.ajoutTache(tache);
                    LOGGER.info("SC:ajoutTache  "+intersect+" pour "+t);
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
