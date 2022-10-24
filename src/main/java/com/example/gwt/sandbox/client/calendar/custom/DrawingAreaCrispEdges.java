package com.example.gwt.sandbox.client.calendar.custom;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.VectorObject;

/**
 * Extension de la classe de base {@Link DrawingArea} permettant d'avoir des lignes plus fines en appliquant la propriété `shape-rendering="crispEdges"`
 */
public class DrawingAreaCrispEdges extends DrawingArea {

    public DrawingAreaCrispEdges(int width, int height) {
        super(width, height);
    }

    @Override
    public VectorObject add(VectorObject vo) {
        vo.getElement().setAttribute("shape-rendering", "crispEdges");
        return super.add(vo);
    }
}
