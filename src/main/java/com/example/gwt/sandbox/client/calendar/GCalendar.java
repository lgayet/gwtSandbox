package com.example.gwt.sandbox.client.calendar;

import com.example.gwt.sandbox.client.calendar.GButtonChoixAffichage.ChoixAffichage;
import com.example.gwt.sandbox.shared.calendar.*;
import com.google.gwt.user.client.ui.Widget;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de dessiner un calendrier avec en colonnes les jours et les salariès en tant que lignes
 */
public class GCalendar {

    public static MoveContext MOVE_CONTEXT = new MoveContext();

    private final int NB_JOURS_PAR_SEMAINE = 7;

    private final int LARGEUR_PANEL = 1900;
    private final int HAUTEUR_PANEL = 600;
    private final int HAUTEUR_ENTETES_COLONNES = 50;
    private final int LARGEUR_ENTETE_SALARIES = LARGEUR_PANEL / 8;
    private final DrawingArea canvas = new DrawingArea(LARGEUR_PANEL, HAUTEUR_PANEL);
    private final double OPACITY = 0.3D;
    private ChoixAffichage choixAffichage;
    //  Informations pour affichage des dates
    public final String[]tJours = {"XXX","Lun","Mar","Mer","Jeu","Ven","Sam","Dim"};
    public final String[]tJoursLong = {"XXX","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
    public final String[]tMois ={"XXX","Jan","Fev","Mar","Avr","Mai","Juin","Juil","Aou","Sep","Oct","Nov","Dec"};
    public final String[]tMoisLong = {"XXX","Janvier","Février","Mars","Avril","Mai","Juin","Juilet","Août","Septembre","Octobre","Novembre","Décembre"};
    //  pour construire les objets
  /*
    TODO Marc: 13/9/2022: première implémentation: selection de 3 mois pleins
   */
//  Les choix d'affichage ENTREPRISE/UTILISATEUR
    private final double heureDebJour = 6.0;//TODO  petit modif a prévoir si début n'est pas une heure exacte
    private final double heureFinJour = 22.5;
    private final double PLAGE_HORARAIRE = heureFinJour - heureDebJour;

    // les contenus modifiables du tableau
    private Text labelCentre;
    private Line[]tLHSals = new Line[0];
    private Text[]tNomsSal = new Text[0];

    // Objets grahiques structurelles (ex: lignes, etc.) à supprimer lors d'un redessin (les taches graphiques sont gérés dans la liste taches)
    private List<VectorObject> objectsGraphiqueStructure = new ArrayList();
    private List<GTacheCol> tacheCols = new ArrayList();

    private int nbJoursAffiches;
    private int indicePremiereCol = 0;
    private int indiceJourCourant = 0;//TODO pour les retours Semaine ou mois vers Jour (premier jour de la semaine ou du mois)
    private double largCol;
    private Colonne[] tCols;//TODO: dimension= nbJours de la selection
    private Tache[] taches;
    private GSalarie[] tSals;

    private GButtonPlusMoins boutMoins;
    private GButtonPlusMoins boutPlus;

    public GCalendar() {
        //    Debut de la construction graphique
        //  LH_Entete
        ajoutLigne(0, HAUTEUR_ENTETES_COLONNES, LARGEUR_PANEL, HAUTEUR_ENTETES_COLONNES, OPACITY);
        //  LV_EnteteSalaries
        ajoutLigne( LARGEUR_ENTETE_SALARIES,50, LARGEUR_ENTETE_SALARIES, HAUTEUR_PANEL , OPACITY);
        //    Ajout des boutons choixAffichage
        GButtonChoixAffichage boutJour = new GButtonChoixAffichage( this, canvas, 30, 10,50,30, ChoixAffichage.JOUR, "Jour");
        GButtonChoixAffichage boutSem = new GButtonChoixAffichage( this, canvas, 90, 10,70,30, ChoixAffichage.SEMAINE, "Semaine");
        GButtonChoixAffichage boutMois = new GButtonChoixAffichage( this, canvas, 170, 10,50,30, ChoixAffichage.MOIS, "Mois");
        boutJour.addBoutonVoisins(boutSem, boutMois);
        boutSem.addBoutonVoisins(boutJour, boutMois);
        boutMois.addBoutonVoisins(boutJour, boutSem);

        boutMoins = new GButtonPlusMoins(this, canvas, 300,10,30, 30, "<", false);
        boutPlus = new GButtonPlusMoins(this, canvas, 350,10,30, 30, ">", true);
        boutMoins.setButtonVoisin(boutPlus);
        boutPlus.setButtonVoisin(boutMoins);
        //    Le label affichant la période affichée
        labelCentre = ajoutLabel((((LARGEUR_PANEL-LARGEUR_ENTETE_SALARIES)/2)+LARGEUR_ENTETE_SALARIES),30,"du texte", 20, 1.0);

        canvas.addMouseMoveHandler(event -> {
            MOVE_CONTEXT.move(event.getClientX(), event.getClientY());
//            affiche(choixAffichage);
            if(MOVE_CONTEXT.isBusy())afficheSalCol(MOVE_CONTEXT.getSalarie());
        });

        canvas.addMouseUpHandler(event -> {
            MOVE_CONTEXT.stop(event.getClientX(), event.getClientY());
//            affiche(choixAffichage);
            if(MOVE_CONTEXT.isBusy())afficheSalCol(MOVE_CONTEXT.getSalarie());
        });
    }

    public Widget getWidget() {
        return canvas;
    }

    public void setSelection(Selection selection){
        construit(selection);
        int nbSal = tSals.length;
        double hauteurSal = (HAUTEUR_PANEL - HAUTEUR_ENTETES_COLONNES * 2) / nbSal;
        tLHSals = new Line[nbSal];
        tNomsSal = new Text[nbSal];
        for(int i = 0; i < nbSal; i++){
            tSals[i].setPositionY((int)(hauteurSal * i) + HAUTEUR_ENTETES_COLONNES * 2, hauteurSal);
            tLHSals[i] = ajoutLigne(0,tSals[i].getPositionY(), LARGEUR_PANEL, tSals[i].getPositionY(), OPACITY);
            tNomsSal[i] = ajoutLabel( LARGEUR_ENTETE_SALARIES / 4, tSals[i].getPositionY() + (int) hauteurSal / 2 + 5 , tSals[i].getNomSal(), 14, 0.8);
        }
    }

    public void actionChoixAffichage(ChoixAffichage choixAffichage){
        System.out.println("coucou setChoixAffichage avec "+choixAffichage);
        if( choixAffichage != this.choixAffichage) {
            boutPlus.setValid(true);
            boutMoins.setValid(true);
            affiche(choixAffichage);
        }
    }

    public void actionPlusMoins(boolean plus){
        if( ! plus && indicePremiereCol == 0){
            boutMoins.setValid(false);
            return;
        }
        switch(choixAffichage){
            case JOUR: {
                indicePremiereCol = plus ? indicePremiereCol + 1 : indicePremiereCol - 1;
                indiceJourCourant = indicePremiereCol;
                if(indicePremiereCol == 0)boutMoins.setValid(false);
                if(indicePremiereCol == tCols.length -1)boutPlus.setValid(false);
            } break;
            case SEMAINE: {
                indicePremiereCol = plus ? indicePremiereCol + 7 : indicePremiereCol - 7;
                nbJoursAffiches = 7;
                indiceJourCourant = indicePremiereCol;
                if(indicePremiereCol <= 0){
                    boutMoins.setValid(false);
                    if(indicePremiereCol < 0)return;
                }
                if(indicePremiereCol + 7 >= tCols.length){
                    boutPlus.setValid(false);
                    if(indicePremiereCol + 7 > tCols.length)return;
                }
                //TODO: affichage des semaines incomplètes?
            } break;
            case MOIS: {
//        TODO dans le cas moins, on se décale du nombre de jours du mois précédent
                nbJoursAffiches = getNbJoursMois(tCols[indicePremiereCol].getAnnee(), tCols[indicePremiereCol].getNumMois());//TODO du mois en cours
                int decal = plus ? nbJoursAffiches: - getNbJoursMois(tCols[indicePremiereCol - 1].getAnnee(), tCols[indicePremiereCol - 1].getNumMois());
                indicePremiereCol = indicePremiereCol + decal;
                nbJoursAffiches = getNbJoursMois(tCols[indicePremiereCol].getAnnee(), tCols[indicePremiereCol].getNumMois());//TODO du nouveau mois
                indiceJourCourant = indicePremiereCol;//TODO: pour éviter certains plantages, on revient systématiquement sur lundi
                if(indicePremiereCol <= 0){
                    boutMoins.setValid(false);
                    if(indicePremiereCol < 0)return;
                }
                if(indicePremiereCol + nbJoursAffiches >= tCols.length){
                    boutPlus.setValid(false);
                    if(indicePremiereCol + nbJoursAffiches > tCols.length)return;
                }
            } break;
        }
        affiche(choixAffichage);
    }

    private void affiche(ChoixAffichage choixAffichage){
        this.choixAffichage = choixAffichage;

        effacerObjetsGraphiques();

        if(choixAffichage == ChoixAffichage.JOUR){
            nbJoursAffiches = 1;
            Colonne c = tCols[indicePremiereCol];
            c.setPositionX(LARGEUR_ENTETE_SALARIES);
            labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - 100,30,tJoursLong[c.getNumJourSem()]+" "+c.getNumJourMois()+" "+tMoisLong[c.getNumMois()]+" "+c.getAnnee(), 20, 1.0);
            largCol = LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES;
            double largeurHoraire = largCol / PLAGE_HORARAIRE;
            for(double h = 1.0; h < PLAGE_HORARAIRE; h +=1.0){
                int decalX = h+ (int) heureDebJour < 10 ? 3 : 6;
                objectsGraphiqueStructure.add( ajoutLigne(c.getPositionX() + (int) (h* largeurHoraire), HAUTEUR_ENTETES_COLONNES  + 30, c.getPositionX() + (int) (h * largeurHoraire), HAUTEUR_PANEL, OPACITY));
                objectsGraphiqueStructure.add(ajoutLabel(c.getPositionX() + (int) (h* largeurHoraire) - decalX, HAUTEUR_ENTETES_COLONNES + 20, (int) h+ heureDebJour + "" , 12, 0.5));
            }

        }else if(choixAffichage == ChoixAffichage.SEMAINE) {
            nbJoursAffiches = 7;
            indicePremiereCol = indicePremiereCol - tCols[indicePremiereCol].getNumJourSem() + 1 ;//TODO: se positionne sur lundi
            indicePremiereCol = indicePremiereCol < 0 ? 0 : indicePremiereCol;
            Colonne c = tCols[indicePremiereCol];
            c.setPositionX(LARGEUR_ENTETE_SALARIES);
            Colonne cFin = tCols[indicePremiereCol+6];
            boolean anneeFin = cFin.getAnnee() != c.getAnnee();
            boolean moisFin = cFin.getNumMois() != c.getNumMois();
            int decalX = anneeFin ? 150 : moisFin ? 100 : 50;
            String text = anneeFin ? (tMoisLong[c.getNumMois()]+"  " + c.getAnnee() +"  -  "+ tMoisLong[cFin.getNumMois()] + "  "+ cFin.getAnnee() ) : ( moisFin ?  tMoisLong[c.getNumMois()] +"  -  " + tMoisLong[cFin.getNumMois()]+ "  "+ c.getAnnee() : tMoisLong[c.getNumMois()]+"  "+c.getAnnee());
            labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - decalX,30, text , 20, 1.0);
            largCol = ((double)LARGEUR_PANEL - (double)LARGEUR_ENTETE_SALARIES) / NB_JOURS_PAR_SEMAINE;
            double decal2X = 20.0;
            Colonne c2;
            for (int i = 0; i < NB_JOURS_PAR_SEMAINE; i++) {
                double x = LARGEUR_ENTETE_SALARIES + (largCol * i );// TODO: pour dessiner la ligne à gauche de la colonne
                c2 = tCols[c.getNumCol()+ i];
                c2.setPositionX((int)x);
                if(i > 0)objectsGraphiqueStructure.add(ajoutLigne(c2.getPositionX(), HAUTEUR_ENTETES_COLONNES , c2.getPositionX(), HAUTEUR_PANEL, OPACITY));
                objectsGraphiqueStructure.add(ajoutLabel((int)( c2.getPositionX() + largCol / 2.0 - decal2X ), HAUTEUR_ENTETES_COLONNES + 30 , tJours[tCols[indicePremiereCol+i].getNumJourSem()]+ " "+tCols[indicePremiereCol+i].getNumJourMois() , 14, 0.5D));
            }
        }
        else if(choixAffichage == ChoixAffichage.MOIS){//TODO: il faudra etablir le nombre de jours enj fonction du mois
            Colonne c = getPremierJourMois(tCols[indicePremiereCol].getAnnee(), tCols[indicePremiereCol].getNumMois());
            indicePremiereCol = c.getNumCol();
            Colonne c2;
            int nbJoursMois = getNbJoursMois(c.getAnnee(), c.getNumMois());
            nbJoursAffiches = nbJoursMois;
            labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - 50,30, tMoisLong[c.getNumMois()]+"  "+c.getAnnee() , 20, 1.0);
            largCol = ((double)LARGEUR_PANEL - (double)LARGEUR_ENTETE_SALARIES) / nbJoursMois;
            for(int i = 0; i< nbJoursMois; i++ ){
                double x = LARGEUR_ENTETE_SALARIES + (largCol * (i));
                c2 = tCols[c.getNumCol() + i];
                c2.setPositionX((int)x);
                double opacite = c2.getNumJourMois() > 1 && c2.getNumJourSem() == 1.0 ? 0.5 : OPACITY;
                int decalX = c2.getNumJourMois() >= 10 ? 7 : 5;
                if(i > 0)objectsGraphiqueStructure.add(ajoutLigne(c2.getPositionX(), HAUTEUR_ENTETES_COLONNES, (int)x, HAUTEUR_PANEL, opacite));
                objectsGraphiqueStructure.add(ajoutLabel((int)(c2.getPositionX() + largCol * 0.5 - 10), HAUTEUR_ENTETES_COLONNES + 20, tJours[c2.getNumJourSem()], 12, OPACITY));
                objectsGraphiqueStructure.add(ajoutLabel((int)(c2.getPositionX() + largCol * 0.5 - decalX) , HAUTEUR_ENTETES_COLONNES + 40, c2.getNumJourMois()+"", 12,OPACITY));
            }
        }
//      affichage des Tâches
        Colonne c;
        for(int i = indicePremiereCol; i < indicePremiereCol + nbJoursAffiches; i++) {
            c = tCols[i];
            for (GSalarie gSalarie : tSals) {
                GSalCol gSalCol = gSalarie.getGSalCols()[i];
                SalCol salCol = gSalCol.getSalCol();
                int it =0;
                for(Tache tache: salCol.getTaches()){
                    int posXPlus1 = i < nbJoursAffiches ? tCols[i+1].getPositionX() : LARGEUR_PANEL;
                    gSalCol.getTacheCols()[it] = ajoutTache(gSalarie,tache,i,c.getPositionX(), posXPlus1, largCol);
                it ++;
                }
            }
        }
    }

    private void afficheSalCol(GSalarie salarie){
        Colonne c;
        for(int i = indicePremiereCol; i < indicePremiereCol + nbJoursAffiches; i++) {
            c = tCols[i];
            GSalCol gSalCol = salarie.getGSalCols()[i];
            for (GTacheCol t : gSalCol.getTacheCols()) {
                t.remove(canvas);
                tacheCols.remove(t);
            }
            SalCol salCol = gSalCol.getSalCol();
            int it = 0;
            for (Tache tache : salCol.getTaches()) {
                int posXPlus1 = i < nbJoursAffiches ? tCols[i+1].getPositionX() : LARGEUR_PANEL;
                gSalCol.getTacheCols()[it] = ajoutTache(salarie, tache, i, c.getPositionX(), posXPlus1, largCol);
                it++;
            }
        }
    }
    private void construit(Selection selection){
        tCols = selection.getTCols();
        taches = selection.getTTache();
        Salarie[] salaries = selection.getTSals();
        tSals = new GSalarie[salaries.length];
        for (int i = 0; i< salaries.length; i++){
            tSals[i] = new GSalarie(this, salaries[i]);
        }
    }

    public Tache[] getTaches() {
        return taches;
    }

    private void effacerObjetsGraphiques() {
        canvas.remove(labelCentre);
        objectsGraphiqueStructure.forEach(o -> canvas.remove(o));
        objectsGraphiqueStructure.clear();
        tacheCols.forEach(t -> t.remove(canvas));
        tacheCols.clear();
    }

    private int getNbJoursMois(int annee, int numMois){
        int nbJoursMois = 0;
        for(Colonne c: tCols){
            if(c.getAnnee() == annee && c.getNumMois() == numMois)nbJoursMois ++;
        }
        return nbJoursMois;
    }

    private Colonne getPremierJourMois(int annee, int numMois){
        for(Colonne c: tCols){
            if(c.getAnnee() == annee && c.getNumMois() == numMois && c.getNumJourMois() == 1)return c;
        }
        return null;
    }

    private GTacheCol ajoutTache(GSalarie salarie, Tache tache, int indiceJour, int positionX, int posXPlus1, double largCol){
        GTacheCol t = new GTacheCol(canvas, salarie, tache, heureDebJour, heureFinJour, indiceJour, positionX, posXPlus1, largCol);
        t.setFillColor("blue");
        tacheCols.add(t);
        return t;
    }

    private Text ajoutLabel(int x, int y, String text, int size, double opacity){
        Text t = new Text(x,y,text);
        t.setFontSize(size);
        t.setStrokeOpacity(opacity);
        canvas.add(t);
        return t;
    }

    private Line ajoutLigne(int x1, int y1, int x2, int y2, double opacity){
        Line l = new Line(x1,y1,x2,y2);
        l.setStrokeOpacity(opacity);
        canvas.add(l);
        return l;
    }
}
