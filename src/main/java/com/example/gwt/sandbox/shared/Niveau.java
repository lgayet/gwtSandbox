package com.example.gwt.sandbox.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Niveau implements Serializable {
    private int numSal;
    private int numNiv;// indice
    private transient Salarie salarie;
    private NivCol[] nivCols = new NivCol[0];
    private Integer[] taches;
    private transient ArrayList<Integer> aNumTaches = new ArrayList<>();
    /*
        TODO: le niveau est géré par le Salarié, à partir de la liste de selection
            son indice est local au Salarié
     */

    public Niveau() {
    }

    public Niveau(Salarie salarie, int numNiv, int nbJours){
        this.salarie = salarie;
        numSal = salarie.getNumSal();
        this.numNiv = numNiv;
        nivCols = new NivCol[nbJours];
    }

    public NivCol[] getNivCols() {
        return nivCols;
    }

    public void ajoutTache(Tache tache){
        aNumTaches.add(tache.getNumTache());
        int numCol = tache.getNumCol();
        if(nivCols[numCol] == null)nivCols[numCol] = new NivCol(numCol);
        nivCols[numCol].ajoutTache(tache.getNumTache());
    }

    public void creTTache(){
        taches = aNumTaches.toArray(new Integer[aNumTaches.size()]);
    }

}
