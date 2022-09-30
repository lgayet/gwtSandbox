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
    private Text text;
//    les informations d'affichage
    private double heureDebJour;//TODO  petit modif a prévoir si début n'est pas une heure exacte
    private double heureFinJour;
    private double plageHoraire;
    private double largCol;
    private int xCol;
    private int numCol=0;

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(GTacheCol.class.getName());

    public GTacheCol(DrawingArea canvas, GSalarie salarie, Tache tache, double heureDebJour, double heureFinJour, int numCol, int xCol, int positionXPlus1, double largCol, int indicePremiereCol, int nbJoursAffiches) {
        this.canvas = canvas;
        this.salarie = salarie;
        this.tache = tache;
        this.numCol = numCol;
        this.largCol = largCol;
        this.xCol=xCol;
        this.heureDebJour = heureDebJour;
        this.heureFinJour = heureFinJour;
        plageHoraire = heureFinJour - heureDebJour;
        LOGGER.info("GTacheCol.new  numCol = "+numCol+" "+tache);
        construit(positionXPlus1, indicePremiereCol, nbJoursAffiches);
    }


    private void construit(int posXPlus1, int indicePremiereCol, int nbJoursAffiches){
        if(tache == null)LOGGER.info("!!!!!!!!!!!! GTacheCol.construit "+this);
        else {
            if (isDessinable(numCol)) {
                Intersection is = tache.isIntersection() ? salarie.getIntersection(tache.getNumIntersection()) : null;
                int maxNiv = is != null ? is.getmaxNiv() : 1;
                int x = getPositionXDeb();
                int y = (int) (salarie.getPositionY() + salarie.getHauteurSal() * tache.getNiveau() / maxNiv + 3);
                int w = getLargeur();
                int h = (int) (salarie.getHauteurSal() / maxNiv - 6);
                rectangle = new Rectangle(x, y, w, h);
                rectangle.setFillOpacity(0.5);
                rectangle.setStrokeWidth(0);
                rectangle.addMouseDownHandler(event -> {
                    MOVE_CONTEXT.start(salarie, tache, numCol, largCol, indicePremiereCol, nbJoursAffiches, event.getClientX(), event.getClientY());
                });
                canvas.add(rectangle);
                text = new Text(x + w / 2, y + h / 2, "");//le label contiendra les informations tâche à afficher
                text.addMouseDownHandler(event -> {
                    MOVE_CONTEXT.start(salarie, tache, numCol, largCol, indicePremiereCol, nbJoursAffiches, event.getClientX(), event.getClientY());
                });
                canvas.add(text);
            }
            LOGGER.info("GTacheCol.construit-Fin numCol= "+numCol+" numTache= "+tache.getNumTache());
        }
    }

    public int getNumTache(){
        return tache.getNumTache();
    }


    public void remove() {
        if(rectangle != null)canvas.remove(rectangle);
        if(text != null)canvas.remove(text);
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


    private int getPositionXDeb(){
        if(tache  == null)LOGGER.info("GTacheCol.getPositionXDeb "+this+" numCol= "+numCol);
        if(tache.getJoursSelDeb() ==  numCol){
            int decalX =  (int)((tache.getHDebDecim() - heureDebJour) * largCol / (heureFinJour - heureDebJour));
            int x = decalX < 3 ? xCol + 3 : xCol +(int) decalX;// dépendra de la taille des traits
            return x;
        }else{//on est donc sur numColFin
            return xCol +3;
        }
    }

    private int getLargeur(){
        double nbHeuresJour = heureFinJour - heureDebJour;
        if(tache == null)LOGGER.info("GTacheCol.getLargeur tache null "+this+" numCol= "+numCol);
        else {
            if (tache.getJoursSelDeb() == numCol) {
                double nbHeuresTache = tache.getJoursSelDeb() == tache.getJoursSelFin() ? (tache.getHFinDecim() - tache.getHDebDecim()) : (heureFinJour - tache.getHDebDecim());
                return (int) (largCol * nbHeuresTache / nbHeuresJour);
            } else {
                double nbHeuresTache = tache.getHFinDecim() - heureDebJour;
                return (int) (largCol * nbHeuresTache / nbHeuresJour);
            }
        }
        return 0;
    }
    public String toString(){
        return "GTachCol numCol = "+numCol;
    }
}
