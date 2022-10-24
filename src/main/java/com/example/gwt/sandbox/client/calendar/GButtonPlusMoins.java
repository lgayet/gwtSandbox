package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.client.calendar.custom.TextArialPlein;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

public class GButtonPlusMoins {

    private GCalendar calendar;
    private Rectangle rectangle;
    private Text text;
    private boolean plus;
    private boolean valid;
    private GButtonPlusMoins boutonVoisin;


    public GButtonPlusMoins(GCalendar calendar, DrawingArea canvas, int x, int y, int width, int height, String label, boolean plus) {
        this.calendar = calendar;
        rectangle = new Rectangle(x, y , width, height);
        rectangle.setRoundedCorners(5);
        rectangle.setFillColor("orange");
        rectangle.setFillOpacity(0.2D);
        rectangle.addClickHandler(event -> {
            onClick();
        });
        canvas.add(rectangle);
        text = new TextArialPlein(x+10, y+20, label);
        text.setFontSize(14);
        text.addClickHandler(event -> {
            onClick();
        });
        canvas.add(text);
        this.plus = plus;
        setValid(plus ? true : false);;
    }

    private void onClick(){
        if(valid){
            calendar.actionPlusMoins(plus);
            boutonVoisin.setValid(true);
        }
    }

    public void setValid(boolean valid) {
        if( this.valid != valid){
            rectangle.setFillColor(valid ? "orange" : "black");
            this.valid = valid;
        }
    }

    public void setButtonVoisin(GButtonPlusMoins boutonVoisin){
        this.boutonVoisin = boutonVoisin;
    }

}
