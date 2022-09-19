package com.example.gwt.sandbox.shared;

import com.example.gwt.sandbox.client.Colonne;
import com.example.gwt.sandbox.client.Test;
import com.example.gwt.sandbox.client.TypTache;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.io.Serializable;

public class Tache implements Serializable, Positionable {

    private transient Test test;
    private TypTache typTache;
    private int numTache;
    private int numSal;
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
    private transient Colonne colonne;
//    les taches et intersection
    private Tache[] tachesIntersect;
    private transient Rectangle rectangle;
    private transient Text text;

    public Tache() {
    }

    public Tache(int numTache, int numSal, int anneDeb, int moisDeb, int jourDeb, int hDeb, int mnDeb, long longDeb, int anneFin, int moisFin, int jourFin, int hFin, int mnFin, long longFin, int numCol) {
        this.numTache = numTache;
        this.numSal = numSal;
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


    public int getAnneDeb() {
        return anneDeb;
    }

    public int getMoisDeb() {
        return moisDeb;
    }

    public int getJourDeb() {
        return jourDeb;
    }

    public int gethDeb() {
        return hDeb;
    }

    public int getMnDeb() {
        return mnDeb;
    }

    public long getLongDeb() {
        return longDeb;
    }

    public int getAnneFin() {
        return anneFin;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public int getJourFin() {
        return jourFin;
    }

    public int gethFin() {
        return hFin;
    }

    public int getMnFin() {
        return mnFin;
    }

    public long getLongFin() {
        return longFin;
    }

    public void ajoutTacheIntersect(Tache tache){

    }
    public void retraitTacheIntersect(Tache tache){

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
