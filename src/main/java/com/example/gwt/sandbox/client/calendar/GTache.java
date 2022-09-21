package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Tache;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import static com.example.gwt.sandbox.client.calendar.GCalendar.MOVE_CONTEXT;

public class GTache implements Positionable {

    private Tache tache;
    private Rectangle rectangle;
    private Text text;


    public GTache(DrawingArea canvas, Tache tache, int x, int y, int w, int h, String label) {
        this.tache = tache;
        rectangle = new Rectangle(x, y, w, h);
        rectangle.setFillOpacity(0.5);
        rectangle.setStrokeWidth(0);
        rectangle.addMouseDownHandler(event -> {
           MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
        canvas.add(rectangle);
        text = new Text(x + w/2, y + h/2, label);
        text.addMouseDownHandler(event -> {
            MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
        });
        canvas.add(text);
    }

    public void remove(DrawingArea canvas) {
        canvas.remove(rectangle);
        canvas.remove(text);
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
