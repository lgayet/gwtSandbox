package com.example.gwt.sandbox.shared.calendar;

import java.io.Serializable;

public class Tache implements Serializable {

    private TypTache typTache;
    private int numTache;
    private int anneDeb;
    private int moisDeb;
    private int jourDeb;
    private int hDeb;
    private int mnDeb;
    private long longDeb;
    private int anneFin;
    private int moisFin;
    private int jourFin;
    private int hFin;
    private int mnFin;
    private long longFin;
//
    private int numColDeb;
    private int numColFin;
    private int niveau;// l'indice du niveau de dépôt de la tâche
    private double decalX;//pour les contrôles de marges
    private transient Colonne colonne;
//    les taches et intersection
    private Integer numIntersection;
    private Tache[] tachesIntersect = new Tache[0];

    public Tache() {
    }

    public Tache(int numTache, int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, long longDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, long longFin, int numColDeb, int numColFin) {
        this.numTache = numTache;
        this.anneDeb = anneDeb;
        this.moisDeb = moisDeb;
        this.jourDeb = jourDeb;
        this.hDeb = hDeb;
        this.mnDeb = mnDeb;
        this.longDeb = longDeb;
        this.anneFin = anneFin;
        this.moisFin = moisFin;
        this.jourFin = jourFin;
        this.hFin = hFin;
        this.mnFin = mnFin;
        this.longFin = longFin;
        this.numColDeb = numColDeb;
        this.numColFin = numColFin;
    }

    public int getNumTache() {
        return numTache;
    }

    public int getNumColDeb() {
        return numColDeb;
    }

    public int getNumColFin() {
        return numColFin;
    }

    public Colonne getColonne() {
        return colonne;
    }

    public long getLongDeb() {
        return longDeb;
    }

    public long getLongFin() {
        return longFin;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public Integer getNumIntersection(){
        return numIntersection;
    }

    public void setNumIntersection(Integer numIntersection) {
        this.numIntersection = numIntersection;
    }

    public boolean isIntersection(){
        return numIntersection != null;
    }
    public void ajoutTacheIntersect(Tache tache){
        for(Tache t: tachesIntersect){
            if(t == tache)return;
        }
        Tache[] t = new Tache[tachesIntersect.length +1];
        for(int i = 0; i < tachesIntersect.length; i++){
            t[i] = tachesIntersect[i];
        }
        t[tachesIntersect.length]= tache;
        tachesIntersect = t;
    }

    public void retraitTacheIntersect(Tache tache){

    }
    public boolean isDessinable(int numCol, double heureDebJour, double heureFinJour){
        if((numColDeb == numCol || numColDeb == numColFin) && getHDeb() < heureFinJour && getHFin() > heureDebJour)return true;
        if(numColFin == numCol && getHFin() > heureDebJour)return true;
        return false;
    }

    public int getPositionXDeb(int numCol, int positionXCol, double largCol, double heureDebJour, double heureFinJour){
        if(numColDeb ==  numCol){
            decalX =  (getHDeb() - heureDebJour) * largCol / (heureFinJour - heureDebJour);
            int x = decalX < 3 ? positionXCol + 3 : positionXCol +(int) decalX;// dépendra de la taille des traits
            return x;
        }else{//on est donc sur numColFin
            return positionXCol +3;
        }
    }

    public int getLargeur(int numCol, double largCol, double heureDebJour, double heureFinJour){
        double nbHeuresJour = heureFinJour - heureDebJour;
        if(numColDeb == numCol) {
            double nbHeuresTache = numColDeb == numColFin ? (getHFin() - getHDeb()) : ( heureFinJour - getHDeb());
            return (int) (largCol * nbHeuresTache / nbHeuresJour);
        }else{
            double nbHeuresTache = getHFin() - heureDebJour;
            return (int) (largCol * nbHeuresTache / nbHeuresJour);
        }
    }

    private double getHDeb(){
        return hDeb + mnDeb / 60.0;
    }
    private double getHFin(){
        return hFin +  mnFin / 60.0;
     }

}
