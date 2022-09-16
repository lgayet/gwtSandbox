package com.example.gwt.sandbox.client;

/*
    TODO Marc 12/9/2022: quelque soit le choix d'affichage (JOUR,SEMAINE,MOIS,...), une colonne correspond à 1 jour
        Les tâches se positionnent par rapport à positionX et largeur, ce qui fait que l'affichage doit toujours être correct (sans vide ni débordement)
 */

import java.io.Serializable;

public class Colonne implements Serializable {
    private int numCol;
    public int positionX;
    public double largeur;//TODO Marc 12/9/2022: A voir: si problèmes d'affichage: la largeur n'est pas issue du calcul largeurPanneau/nbCol mais de PositionCol[numCol+1]- positionX
    private int annee;
    private int numMois;
    private int numJourMois;
    private int numJourSem;//TODO pour accès direct à la colonne: indice relatif au début de la selection?(à approfondir)
    private boolean affichage0_24 = false;//TODO: l'affichage par défaut est un paramètre Entreprise ==> Utisateur

//
    private transient Test test;

    public Colonne() {
    }

    public Colonne(int numCol, int annee, int numMois, int numJourMois, int numJourSem) {
        this.numCol = numCol;
        this.annee = annee;
        this.numMois = numMois;
        this.numJourMois = numJourMois;
        this.numJourSem = numJourSem;
    }

    public int getNumCol() {
        return numCol;
    }

    public int getAnnee() {
        return annee;
    }

    public int getNumMois() {
        return numMois;
    }

    public int getNumJourMois() {
        return numJourMois;
    }

    public int getNumJourSem() {
        return numJourSem;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public boolean isAffichage0_24() {
        return affichage0_24;
    }

    public void setAffichage0_24(boolean affichage0_24) {
        this.affichage0_24 = affichage0_24;
    }
}
