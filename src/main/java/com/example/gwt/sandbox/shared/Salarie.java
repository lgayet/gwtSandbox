package com.example.gwt.sandbox.shared;

import com.example.gwt.sandbox.client.Test;

import java.io.Serializable;

public class Salarie implements Serializable {
    private int numSal;
    private String nomSal;
    private int nbJours;
    private transient Test test;
    private Niveau[] niveaux = new Niveau[1];
    private int positionY;
    private transient Selection selection;

    public Salarie() {
    }

    public Salarie(Selection selection, int numSal, String nomSal, int nbJours) {
        this.selection = selection;
        this.numSal = numSal;
        this.nomSal = nomSal;
        this.nbJours = nbJours;
        niveaux[0] = new Niveau(this, 0, nbJours);
    }

    public String getNomSal() {
        return nomSal;
    }

    public int getNumSal() {
        return numSal;
    }

    public Niveau[] getNiveaux(){
        return niveaux;
    }

    public Niveau getNiveau(int niv){
        if(niv <= niveaux.length)return niveaux[niv];
        else return ajoutNiveau();
    }

    private Niveau ajoutNiveau(){
        Niveau nouv = new Niveau(this, niveaux.length, nbJours);
        Niveau[] t = new Niveau[niveaux.length + 1];
        for(int i = 0; i < niveaux.length; i++){
            t[i] = niveaux[i];
        }
        t[niveaux.length] = nouv;
        niveaux = t;
        return nouv;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void ajoutTaches(Tache tache){
        niveaux[0].ajoutTache(tache);
    }

    public void creTTaches(){
        for(Niveau n: niveaux){
            n.creTTache();
        }
    }



}
