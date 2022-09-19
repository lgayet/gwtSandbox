package com.example.gwt.sandbox.shared;

import com.example.gwt.sandbox.client.Test;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.io.Serializable;

public class Tache implements Serializable, Positionable {

    private transient Test test;
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
    private int numCol;
    private int niveau;// l'indice du niveau de dépôt de la tâche
    private double decalX;//pour les contrôles de marges
    private transient Colonne colonne;
//    les taches et intersection
    private Tache[] tachesIntersect = new Tache[0];
    private transient Rectangle rectangle;
    private transient Text text;

    public Tache() {
    }

    public Tache(int numTache, int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, long longDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, long longFin, int numCol) {
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
        this.numCol = numCol;
    }

    public Tache(DrawingArea canvas, int x, int y, int width, int height, String label) {
        rectangle = new Rectangle(x, y , width, height);
        rectangle.addMouseDownHandler(event -> {
//            MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
//        canvas.add(rectangle);
        text = new Text(x + width/2, y + height/2, label);
        text.addMouseDownHandler(event -> {
//            MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
//        canvas.add(text);

    }

    public int getNumTache() {
        return numTache;
    }

    public int getNumCol() {
        return numCol;
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

    public int getIndiceMax(){
        int iMax = niveau;
        for(Tache t: tachesIntersect){
            if(t.getNiveau() > iMax)iMax = t.getNiveau();
        }
        return iMax;
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
    public int getPositionXDeb(int positionXCol, double largCol, double heureDebJour, double heureFinJour){
        double hDebut = hDeb + mnDeb  / 60.0;
        decalX =  (hDebut - heureDebJour) * largCol / (heureFinJour - heureDebJour);
         int x = decalX < 3 ? positionXCol + 3 : positionXCol +(int) decalX;// dépendra de la taille des traits
         return x;
    }

    public int getLargeur(double largCol, double nbHeuresJour){
        double nbHeuresTache = hFin +  mnFin / 60.0 - (hDeb + mnDeb / 60.0 );
        double w = decalX + largCol * nbHeuresTache / nbHeuresJour;
        return (int)(largCol * nbHeuresTache / nbHeuresJour);
    }

    @Override
    public int getX() {
        return rectangle.getX();
    }

    @Override
    public void setX(int i) {
        rectangle.setX(i);
        text.setX(i + rectangle.getWidth()/2);
    }

    @Override
    public int getY() {
        return rectangle.getY();
    }

    @Override
    public void setY(int i) {
        rectangle.setY(i);
        text.setY(i + rectangle.getHeight()/2);
    }

    public void setFillColor(String color) {
        rectangle.setFillColor(color);
    }
}
