package com.example.gwt.sandbox.client.calendar.custom;

import org.vaadin.gwtgraphics.client.shape.Text;

/**
 * Extension de la classe de base {@Link Text} permettant d'avoir un texte sans Serif et plein
 */
public class TextArialPlein extends Text {
    public TextArialPlein(int x, int y, String text) {
        super(x, y, text);
        this.setFontFamily("Sans,Arial");
        this.setFillColor("black");
    }
}
