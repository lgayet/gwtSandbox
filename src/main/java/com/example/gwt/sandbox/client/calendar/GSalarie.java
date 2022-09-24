package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.SalCol;
import com.example.gwt.sandbox.shared.calendar.Salarie;

public class GSalarie {
    private Salarie salarie;
    private GSalCol[] salCols;
//    informations d'affichage
    private int positionY;
    double hauteurSal;


    public GSalarie(Salarie salarie) {
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

