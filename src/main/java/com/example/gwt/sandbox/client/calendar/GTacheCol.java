package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.Tache;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.util.logging.Logger;

import static com.example.gwt.sandbox.client.calendar.GCalendar.MOVE_CONTEXT;

/*
    Une PartieTache est la partie de tâche qui est déposée dans une colonne
    Gère toute la partie graphique de l'affichage, quelque soit le mode de présentation retenu (HeureMin-HeureMax / 0-24)
 */
public class GTacheCol implements Positionable {

    private DrawingArea canvas;
    private GSalarie salarie;
    private Tache tache;
    private Rectangle rectangle;
    private Rectangle rdeb;
    private Rectangle rfin;
    private Text text;
//    les informations d'affichage

    private int mnDebJour;
    private int mnFinJour;
    private int mnJour;
    private int largCol;
    private int xCol;
    private int xColPlus1;
    private int numCol=0;

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(GTacheCol.class.getName());

    public GTacheCol(DrawingArea canvas, GSalarie salarie, Tache tache, double heureDebJour, double heureFinJour, int numCol, int xCol, int xColPlus1, double largCol, int indicePremiereCol, int nbJoursAffiches) {
        this.canvas = canvas;
        this.salarie = salarie;
        this.tache = tache;
        this.numCol = numCol;
        this.largCol = (int)largCol;
        this.xCol=xCol;
        this.xColPlus1 = xColPlus1;
        mnDebJour = (int)(heureDebJour * 60);
        mnFinJour = (int)(heureFinJour * 60);
        mnJour = mnFinJour-mnDebJour;
        LOGGER.info("GTacheCol.new  numCol = "+numCol+" "+tache);
        construit(indicePremiereCol, nbJoursAffiches);
    }


    private void construit(int indicePremiereCol, int nbJoursAffiches){
        if (isDessinable(numCol)) {
            Intersection is = tache.isIntersection() ? salarie.getIntersection(tache.getNumIntersection()) : null;
            int maxNiv = is != null ? is.getmaxNiv() : 1;
            int y = (int) (salarie.getPositionY() + salarie.getHauteurSal() * tache.getNiveau() / maxNiv + 3);
            int h = (int) (salarie.getHauteurSal() / maxNiv - 6);
            if(isTacheVisible()) {
                int x = getPositionXDeb();
                int w = getLargeur();
                rectangle = new Rectangle(x, y, w, h);
                rectangle.setFillOpacity(0.5);
                rectangle.setStrokeWidth(0);
                rectangle.addMouseDownHandler(event -> {
                    MOVE_CONTEXT.start(salarie, tache, numCol, largCol, indicePremiereCol, nbJoursAffiches, event.getClientX(), event.getClientY());
                });
                canvas.add(rectangle);
                text = new Text(x + w / 2, y + h / 2, tache.getText());//le label contiendra les informations tâche à afficher
                text.addMouseDownHandler(event -> {
                    MOVE_CONTEXT.start(salarie, tache, numCol, largCol, indicePremiereCol, nbJoursAffiches, event.getClientX(), event.getClientY());
                });
                canvas.add(text);
            }{

            }
            if(isDebutVisible()){
                rdeb = new Rectangle(xCol, y,3, h);
                rdeb.setFillOpacity(1.0);
                rdeb.setFillColor("red");
                rdeb.setStrokeWidth(0);
                canvas.add(rdeb);
            }
            if(isFinVisible()){
                rfin = new Rectangle(xColPlus1 -3, y, 3, h);
                rfin.setFillOpacity(1.0);
                rfin.setFillColor("red");
                rfin.setStrokeWidth(0);
                canvas.add(rfin);
            }
        }
//        LOGGER.info("GTacheCol.construit-Fin numCol= "+numCol+" numTache= "+tache.getNumTache());
    }

    public int getNumTache(){
        return tache.getNumTache();
    }


    public void remove() {
        if(rectangle != null)canvas.remove(rectangle);
        if(text != null)canvas.remove(text);
        if(rdeb != null)canvas.remove(rdeb);
        if(rfin != null)canvas.remove(rfin);
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
        if (rectangle != null) rectangle.setFillColor(color);
    }

    private boolean isDessinable(int numCol){
        if (numCol >= tache.getJoursSelDeb() && numCol <= tache.getJoursSelFin()) return true;
        return false;
    }

    private boolean isTacheVisible(){
        if(tache.getMnFin() < mnDebJour || tache.getMnDeb() > mnFinJour)return false;
        return true;
    }

    private boolean isDebutVisible(){
        if(tache.getMnDeb() < mnDebJour || numCol > tache.getJoursSelDeb())return true;
        return false;
    }

    private boolean isFinVisible(){
        if(tache.getMnFin() > mnFinJour || numCol < tache.getJoursSelFin())return true;
        return false;
    }

    private int getPositionXDeb(){
        int decalX =  (tache.getJoursSelDeb() < numCol ? 0 : Math.max(tache.getMnDeb() - mnDebJour, 0)) * largCol / mnJour;
        int x = decalX < 3 ? xCol + 3 : xCol + decalX;// dépendra de la taille des traits
        return x;
    }

    private int getLargeur(){
        int larg = ( (tache.getJoursSelFin()> numCol ? mnFinJour : Math.min(tache.getMnFin(), mnFinJour)) - (tache.getJoursSelDeb() < numCol ? mnDebJour : Math.max(tache.getMnDeb() , mnDebJour)))* largCol / mnJour;
//        LOGGER.info("getLargeur( tache.getMnFin()="+tache.getMnFin()+" mnFinJour= "+mnFinJour+" tache.getMnDeb()= "+tache.getMnDeb()+" mnDebJour= "+mnDebJour+" largCol= "+largCol+" mnJour= "+mnJour+" larg= "+larg);
        return larg;
    }
    public String toString(){
        return "GTachCol numCol = "+numCol+" "+tache;
    }
}
