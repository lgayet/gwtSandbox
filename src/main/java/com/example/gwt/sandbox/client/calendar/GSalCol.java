package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Tache;

import java.util.logging.Logger;

public class GSalCol  {
    private int numCol;
    private SalCol salCol;
    private GTacheCol[] tacheCols = new GTacheCol[0];
    private GTacheCol tacheASupprimer;
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(GSalCol.class.getName());

    public GSalCol() {
    }

    public GSalCol(int numCol, SalCol salCol) {
        this.numCol=numCol;
        this.salCol = salCol;
        Tache[]t = salCol.getTaches();
        tacheCols = new GTacheCol[t.length];
    }

    public SalCol getSalCol() {
        return salCol;
    }

    public void ajoutTacheCol(Salarie salarie, Tache tache, String methodeAppelante){
        salCol.ajoutTache(salarie, tache,methodeAppelante);
        GTacheCol[]t = new GTacheCol[salCol.getTaches().length];
        for(int i = 0; i< tacheCols.length; i++)t[i]= tacheCols[i];
        tacheCols = t;
    }

    public void mouvTacheCol(Salarie salarie){
        salCol.mouvTache(salarie);
    }

    public void supprimeTacheCol(Tache tache){
//        LOGGER.info("GSalCol.supprimeTacheCol "+tache);
        tacheASupprimer = getTacheCol(tache.getNumTache());
        for(GTacheCol t: tacheCols)if(t!= null)t.remove();
        salCol.supprimeTache(tache);
        Tache[]t = salCol.getTaches();
        tacheCols = new GTacheCol[t.length];
    }

    public GTacheCol getTacheASupprimer() {
        return tacheASupprimer;
    }

    public GTacheCol getTacheCol(int numTache) {
        for(GTacheCol tc: tacheCols)if(tc.getNumTache() == numTache)return tc;
        return null;
    }



    public GTacheCol[] getTacheCols() {
        return tacheCols;
    }
    public String toString(){
        return "GSalCol "+numCol+" tacheCols.length= "+tacheCols.length;
    }

}
