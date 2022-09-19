package com.example.gwt.sandbox.shared;

import java.io.Serializable;

public class Salarie implements Serializable {

    private String nomSal;
    private int nbJours;
    private Niveau[] niveaux = new Niveau[1];
//    pour l'affichage
    double hauteurSal;
    private int positionY;
    private int[] tNbNiv;

    public Salarie() {
    }

    public Salarie( String nomSal, int nbJours) {
        this.nomSal = nomSal;
        this.nbJours = nbJours;
        niveaux[0] = new Niveau(0, nbJours);
        tNbNiv = new int[nbJours];
        for(int i = 0; i < nbJours; i++){
            tNbNiv[i] = 0;
        }
    }

    public String getNomSal() {
        return nomSal;
    }


    public Niveau[] getNiveaux(){
        return niveaux;
    }

    public Niveau getNiveau(int niv){
        if(niv < niveaux.length)return niveaux[niv];
        else return ajoutNiveau();
    }

    private Niveau ajoutNiveau(){
        int newNbNiv = niveaux.length + 1 ;
        Niveau nouv = new Niveau(niveaux.length, nbJours);
        Niveau[] t = new Niveau[newNbNiv];
        for(int i = 0; i < niveaux.length; i++){
            t[i] = niveaux[i];
        }
        t[niveaux.length] = nouv;
        niveaux = t;
        return nouv;
    }

    public int[] getTNbNiv() {
        return tNbNiv;
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
        int hauteurNiveau = (int) (hauteurSal / niveaux.length);
        for(int i = 0; i< niveaux.length; i++){
            niveaux[i].setPositionYetHauteur(positionY + hauteurNiveau * i ,hauteurNiveau );
        }
    }

    public void ajoutTaches(Tache tache){
        boolean b = false;
        int i = 0;
        while( ! b ){
            b = getNiveau(i).essaiAjoutTache(tache);
            if(b && i +1 > tNbNiv[tache.getNumCol()])tNbNiv[tache.getNumCol()] = i +1;// on place le niveau maximun atteint pour cette colonne
            i++;
        }
    }




}
