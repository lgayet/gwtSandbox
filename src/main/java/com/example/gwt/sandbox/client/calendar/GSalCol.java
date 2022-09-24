package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Tache;

public class GSalCol {
    private SalCol salCol;
    private GPartieTache[] partieTaches;

    public GSalCol() {
    }

    public GSalCol(SalCol salCol) {
        this.salCol = salCol;
        Tache[]t = salCol.getTaches();
        partieTaches = new GPartieTache[t.length];
    }


    public GPartieTache[] getPartieTaches() {
        return partieTaches;
    }

    public void setPartieTaches(GPartieTache[] partieTaches) {
        this.partieTaches = partieTaches;
    }
}
