package com.example.gwt.sandbox.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

public class ButtonChoixAffichage extends VectorObject implements Positionable {

    private Test test;
    private Rectangle rectangle;
    private Text text;
    private ChoixAffichage choixAffichage;
    private boolean enfonce = false;
    private ButtonChoixAffichage b1;
    private ButtonChoixAffichage b2;

    public ButtonChoixAffichage(Test test, DrawingArea canvas, int x, int y, int width, int height, ChoixAffichage choix, String label) {
        this.test = test;
        rectangle = new Rectangle(x, y , width, height);
        rectangle.setRoundedCorners(5);
        rectangle.setFillColor("orange");
        rectangle.setFillOpacity(0.2D);
        rectangle.addClickHandler(event -> {
            onClick();
        });
        canvas.add(rectangle);
        this.choixAffichage = choix;
        text = new Text(x+8, y+20, label);
        text.setPropertyDouble("fontsize",14.0D);
        text.addClickHandler(event -> {
            onClick();
        });
        canvas.add(text);
        this.b1 = b1;
        this.b2 = b2;
    }

    private void onClick(){
        test.setChoixAffichage(choixAffichage);
        enfonce = true;
            b1.setDeSelected();
            b2.setDeSelected();
        rectangle.setFillOpacity(1.0D);// TODO: pour 'enfoncer' le bouton
    }
    public void setBoutons(ButtonChoixAffichage b1, ButtonChoixAffichage b2){
        this.b1 = b1;
        this.b2 = b2;
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

    public void setDeSelected(){
        enfonce = false;
        rectangle.setFillOpacity(0.2D);
    }

    public ChoixAffichage getChoixAffichage() {
        return choixAffichage;
    }

    public void setFillColor(String color) {
        rectangle.setFillColor(color);
    }

    @Override
    protected Class<? extends VectorObject> getType() {
        return null;
    }
}