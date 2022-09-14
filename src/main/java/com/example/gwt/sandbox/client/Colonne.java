package com.example.gwt.sandbox.client;

public class Colonne {
    private int numCol;
    private Test test;
    public int positionX;
    public int largeur;//TODO Marc 12/9/2022: la largeur n'est pas issue du calcul largeurPanneau/nbCol mais de PositionCol[numCol+1]- positionX
    private int numJourSem;//TODO pour accès direct à la colonne: indice relatif au début de la selection?(à approfondir)
    private int numJourMois;
    private int numMois;
    private int annee;
//
    private boolean affichage0_24;//TODO: l'affichage par défaut est un paramètre Entreprise ==> Utisateur
    /*
        TODO Marc 12/9/2022: quelque soit le choix d'affichage (JOUR,SEMAINE,MOIS,...), une colonne correspond à 1 jour
            Les tâches se positionnent par rapport à positionX et largeur, ce qui fait que l'affichage doit toujours être correct (sans vide ni débordement)
     */

}
