package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Tache;

import java.util.ArrayList;
import java.util.List;

public class GSalarie {
    private GCalendar calendar;
    private Salarie salarie;
    private GSalCol[] salCols;
//    informations d'affichage
    private int positionY;
    double hauteurSal;


    public GSalarie(GCalendar calendar, Salarie salarie) {
        this.calendar = calendar;
        this.salarie = salarie;
        SalCol[] sc = salarie.getSalCols();
        this.salCols = new GSalCol[sc.length];
        for(int i =0; i< sc.length; i ++){
            GSalCol g =  new GSalCol(sc[i]);
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
        for(int i = ta.getNumColDeb(); i <= ta.getNumColFin(); i++){
            tc.add(salCols[i].getTacheCol(numTache));
        }
        return tc.toArray(new GTacheCol[tc.size()]);
    }

    public Intersection getIntersection(Integer numInter){
        return salarie.getIntersection(numInter);
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
}

