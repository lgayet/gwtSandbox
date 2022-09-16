package com.example.gwt.sandbox.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

public class ButtonPlusMoins extends VectorObject implements Positionable {

    private Test test;
    private Rectangle rectangle;
    private Text text;
    private boolean plus = false;
    private boolean valid;
    private ButtonPlusMoins b2;


    public ButtonPlusMoins(Test test, DrawingArea canvas, int x, int y, int width, int height, String label, boolean plus) {
        this.test = test;
        rectangle = new Rectangle(x, y , width, height);
        rectangle.setRoundedCorners(5);
        rectangle.setFillColor("orange");
        rectangle.setFillOpacity(0.2D);
        rectangle.addClickHandler(event -> {
            onClick();
        });
        canvas.add(rectangle);
        text = new Text(x+10, y+20, label);
        text.setPropertyDouble("fontsize",14.0D);
        text.addClickHandler(event -> {
            onClick();
        });
        canvas.add(text);
        this.plus = plus;
        setValid(plus ? true : false);;
    }

    private void onClick(){
        if(valid){
            setValid(test.setPlusMoins(plus));
            b2.setValid(true);
        }
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        rectangle.setFillColor(valid ? "orange" : "black");
    }

    public void setButtonPlusMoins(ButtonPlusMoins b){
        b2 = b;
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

    @Override
    protected Class<? extends VectorObject> getType() {
        return null;
    }
}
