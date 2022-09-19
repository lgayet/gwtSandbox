package com.example.gwt.sandbox.shared;

import java.io.Serializable;


public class Niveau implements Serializable {

    private int indice;// indice
    private NivCol[] nivCols = new NivCol[0];
//    pour positionner les Taches lorsque les nivCols n'ont pas de chevauchement
    private int positionY;
    private int hauteurNiveau;
    /*
        TODO: le niveau est géré par le Salarié, à partir de la liste de selection
            son indice est local au Salarié
     */

    public Niveau() {
    }

    public Niveau(int indice, int nbJours){
        this.indice = indice;
        nivCols = new NivCol[nbJours];
    }

    public NivCol[] getNivCols() {
        return nivCols;
    }

    public boolean essaiAjoutTache( Tache tache){
        int numCol = tache.getNumCol();
        if(nivCols[numCol] == null)nivCols[numCol] = new NivCol(numCol);
        if( ! nivCols[numCol].essaiAjoutTache(tache, indice))return false;
        return true;
    }


    public int getIndice() {
        return indice;
    }

    public void setPositionYetHauteur(int positionY, int hauteurNiveau) {
        this.positionY = positionY;
        this.hauteurNiveau = hauteurNiveau;
    }

    public int getHauteurNiveau() {
        return hauteurNiveau;
    }
}
