package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.shared.calendar.Intersection;
import com.example.gwt.sandbox.shared.calendar.Tache;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Positionable;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import static com.example.gwt.sandbox.client.calendar.GCalendar.MOVE_CONTEXT;

/*
    Une PartieTache est la partie de tâche qui est déposée dans une colonne
    Gère toute la partie graphique de l'affichage, quelque soit le mode de présentation retenu (HeureMin-HeureMax / 0-24)
 */
public class GPartieTache implements Positionable {

    private GSalarie salarie;
    private Tache tache;
    private Rectangle rectangle;
    private Text text;
//    les informations d'affichage
    private double heureDebJour;//TODO  petit modif a prévoir si début n'est pas une heure exacte
    private double heureFinJour;
    private double plageHoraire;

    public GPartieTache(DrawingArea canvas, GSalarie salarie, Tache tache, double heureDebJour, double heureFinJour, int numCol, int positionX, double largColD) {
        this.salarie = salarie;
        this.tache = tache;
        this.heureDebJour = heureDebJour;
        this.heureFinJour = heureFinJour;
        plageHoraire = heureFinJour - heureDebJour;
        construit(canvas, numCol, positionX, largColD);
    }


    private void construit(DrawingArea canvas, int numCol, int positionX, double largColD){
        Intersection is = tache.isIntersection() ? salarie.getIntersection(tache.getNumIntersection()) : null;
        int maxNiv = is != null ? is.getmaxNiv() : 1;
        int x = getPositionXDeb(numCol, positionX, largColD, heureDebJour, heureFinJour);
        int w = (int) (salarie.getPositionY() + salarie.getHauteurSal() * tache.getNiveau() / maxNiv + 3);
        int y = getLargeur(numCol, largColD, heureDebJour, heureFinJour);
        int h = (int) (salarie.getHauteurSal() / maxNiv - 6);
        if(isDessinable(numCol)){
            rectangle = new Rectangle(x,w,y,h);
            rectangle.setFillOpacity(0.5);
            rectangle.setStrokeWidth(0);
            rectangle.addMouseDownHandler(event -> {
                MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
            });
            canvas.add(rectangle);
            text = new Text(x + w/2, y + h/2, "");//le label contiendra les informations tâche à afficher
            text.addMouseDownHandler(event -> {
                MOVE_CONTEXT.start(this, event.getClientX(), event.getClientY());
            });
            canvas.add(text);
        }
    }

    public int getNumTache(){
        return tache.getNumTache();
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

    private boolean isDessinable(int numCol){
        if(tache.numColDeb == tache.numColFin) {//c'est une intersection classique
            if(tache.getHDeb() < heureFinJour && tache.getHFin() > heureDebJour)return true;
            return false;
        }
        if(tache.numColDeb == numCol){// on ne tiend compte que de l'heure de début (l'heure de fin est sur le lendemain
            if(tache.getHDeb() < heureFinJour)return true;
            return false;}
        if(tache.numColFin == numCol){
            if(tache.getHFin() > heureDebJour)return true;
        }
        return false;
    }


    public int getPositionXDeb(int numCol, int positionXCol, double largCol, double heureDebJour, double heureFinJour){
        if(tache.getNumColDeb() ==  numCol){
            int decalX =  (int)((tache.getHDeb() - heureDebJour) * largCol / (heureFinJour - heureDebJour));
            int x = decalX < 3 ? positionXCol + 3 : positionXCol +(int) decalX;// dépendra de la taille des traits
            return x;
        }else{//on est donc sur numColFin
            return positionXCol +3;
        }
    }

    public int getLargeur(int numCol, double largCol, double heureDebJour, double heureFinJour){
        double nbHeuresJour = heureFinJour - heureDebJour;
        if(tache.getNumColDeb() == numCol) {
            double nbHeuresTache = tache.getNumColDeb() == tache.getNumColFin() ? (tache.getHFin() - tache.getHDeb()) : ( heureFinJour - tache.getHDeb());
            return (int) (largCol * nbHeuresTache / nbHeuresJour);
        }else{
            double nbHeuresTache = tache.getHFin() - heureDebJour;
            return (int) (largCol * nbHeuresTache / nbHeuresJour);
        }
    }
}
