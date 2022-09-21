package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class Salarie implements Serializable {

    private String nomSal;
    private int nbJours;
    private int numInter= 0;
    private transient ArrayList<Intersection> aInter = new ArrayList<>();
    private Intersection[] intersections;
    private SalCol[] salCols;
//    pour l'affichage
    double hauteurSal;
    private int positionY;


    public Salarie() {
    }

    public Salarie( String nomSal, int nbJours) {
        this.nomSal = nomSal;
        this.nbJours = nbJours;
        salCols = new SalCol[nbJours];
        for(int i = 0; i< nbJours; i++){
            salCols[i] = new SalCol();
        }
    }

    public String getNomSal() {
        return nomSal;
    }

    public SalCol[] getSalCols() {
        return salCols;
    }


    public Intersection getIntersection(Integer numInter){
        if(intersections != null)return intersections[numInter];// on est sur GWT
        for(Intersection i: aInter){// on est en création des tâches
            if(i.getNumIntersec() == numInter)return i;
        }
        return null;
    }

    public void ajoutIntersection(Intersection intersection){
        aInter.add(intersection);
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
    public void ajoutTaches(Tache tache){
        salCols[tache.getNumColDeb()].ajoutTache(this, tache);
        if(tache.getNumColDeb() != tache.getNumColFin())salCols[tache.getNumColFin()].ajoutTache(this, tache);
    }

    public void setTInterSal(){
        intersections = aInter.toArray(new Intersection[aInter.size()]);
    }



    public int getNumInter(){
        int n = numInter;
        numInter++;
        return n;
    }




}