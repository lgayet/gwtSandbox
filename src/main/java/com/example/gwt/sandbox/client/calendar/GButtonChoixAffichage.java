package com.example.gwt.sandbox.client.calendar;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GButtonChoixAffichage {

    public enum ChoixAffichage {
        JOUR, SEMAINE, MOIS
    }

    private GCalendar calendar;
    private Rectangle rectangle;
    private Text text;
    private ChoixAffichage choixAffichage;
    private Set<GButtonChoixAffichage> boutonVoisins = new HashSet<>();

    public GButtonChoixAffichage(GCalendar calendar, DrawingArea canvas, int x, int y, int width, int height, ChoixAffichage choix, String label) {
        this.calendar = calendar;
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
    }

    private void onClick(){
        calendar.actionChoixAffichage(choixAffichage);
        boutonVoisins.stream().forEach( b -> b.setDeSelected());
        rectangle.setFillOpacity(1.0D);// TODO: pour 'enfoncer' le bouton
    }

    public void addBoutonVoisins(GButtonChoixAffichage... boutons){
        boutonVoisins.clear();
        boutonVoisins.addAll(Arrays.asList(boutons));
    }

    public void setDeSelected(){
        rectangle.setFillOpacity(0.2D);
    }

}
