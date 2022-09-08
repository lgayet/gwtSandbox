package com.example.gwt.sandbox.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import static com.example.gwt.sandbox.client.Test.MOVE_CONTEXT;

public class Tache implements Positionable {

    Rectangle rectangle;
    Text text;

    public Tache(DrawingArea canvas, int x, int y, int width, int height, String label) {
        rectangle = new Rectangle(x, y , width, height);
        rectangle.addMouseDownHandler(event -> {
            MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
        canvas.add(rectangle);
        text = new Text(x + width/2, y + height/2, label);
        text.addMouseDownHandler(event -> {
            MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
        canvas.add(text);

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
