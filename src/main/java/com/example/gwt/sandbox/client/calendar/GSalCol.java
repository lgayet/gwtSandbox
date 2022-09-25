package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Tache;

public class GSalCol {
    private SalCol salCol;
    private GTacheCol[] tacheCols;

    public GSalCol() {
    }

    public GSalCol(SalCol salCol) {
        this.salCol = salCol;
        Tache[]t = salCol.getTaches();
        tacheCols = new GTacheCol[t.length];
    }


    public GTacheCol getTacheCol(int numTache) {
        for(GTacheCol tc: tacheCols)if(tc.getNumTache() == numTache)return tc;
        return null;
    }

    public GTacheCol[] getTacheCols() {
        return tacheCols;
    }

    public void setTacheCols(GTacheCol[] tacheCols) {
        this.tacheCols = tacheCols;
    }
}
