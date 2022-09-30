package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Tache;

public class GSalCol  {
    private SalCol salCol;
    private GTacheCol[] tacheCols;
    private GTacheCol tacheASupprimer;

    public GSalCol() {
    }

    public GSalCol(SalCol salCol) {
        this.salCol = salCol;
        Tache[]t = salCol.getTaches();
        tacheCols = new GTacheCol[t.length];
    }

    public SalCol getSalCol() {
        return salCol;
    }

    public void ajoutTacheCol(Salarie salarie, Tache tache){
        salCol.ajoutTache(salarie, tache);
        Tache[]t = salCol.getTaches();
        tacheCols = new GTacheCol[t.length];
    }

    public void mouvTacheCol(Salarie salarie, Tache tache){
        salCol.mouvTache(salarie, tache);
    }

    public void supprimeTacheCol(Tache tache){
        tacheASupprimer = getTacheCol(tache.getNumTache());
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

}
